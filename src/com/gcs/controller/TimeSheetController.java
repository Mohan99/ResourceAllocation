package com.gcs.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Timesheet;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.TimeSheetDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.requestDao.TimesheetForm;
import com.gcs.requestDao.TimesheetRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Timesheet")
public class TimeSheetController extends BaseController {
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("timeSheetDao")
	private TimeSheetDao timeSheetDao;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private SessionData sessionobj;

	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;

	@RequestMapping(value = "createActualEfforts", method = RequestMethod.GET)
	public ModelAndView createActualEfforts(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			TimesheetRequest sheetReq = new TimesheetRequest();
			List<Projects> projList = timeSheetDao.getProjectsByEmpId(empUserId);
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("sheetReq", sheetReq);
			model.put("projList", projList);

			modelObj = new ModelAndView("effortsAdding", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	public Employee compareEmplyee(int empId, List<Employee> empList) {
		Employee emp = null;
		for (Employee employee : empList) {
			if (employee.getEmpId() == empId)
				emp = employee;
		}
		return emp;
	}

	public Projects compareProjects(int projId, List<Projects> projList) {
		Projects proj = null;
		for (Projects projects : projList) {
			if (projects.getProjectId() == projId)
				proj = projects;
		}
		return proj;
	}

	@RequestMapping(value = "addEfforts", method = RequestMethod.POST)
	public ModelAndView addActualEfforts(ModelMap model, HttpServletRequest req) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			String fromDate = req.getParameter("startdate");
			String endDate = req.getParameter("enddate");
			int totalEfforts = Integer.parseInt(req.getParameter("noOfEfforts"));
			System.out.println("fromDate=" + fromDate + ",endDate" + endDate);
			List<Resourceallocations> resList = timeSheetDao
					.getResourcesListByManager(sessionobj.getEmpObj().getEmpId());

			List<Timesheet> sheetList = new ArrayList<>();
			List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
			List<Projects> projList = projectDao.getActiveProjects();
			Employee emp = null;
			Projects proj = null;
			Timesheet sheet = null;
			for (Resourceallocations res : resList) {
				sheet = new Timesheet();
				emp = compareEmplyee(res.getEmployeeId(), empList);
				proj = compareProjects(res.getProjectId(), projList);
				sheet.setEmpId(emp);
				sheet.setProjectId(proj);
				sheetList.add(sheet);
			}
			System.out.println("sheetList.size()==" + sheetList.size());
			TimesheetRequest sheetReq = new TimesheetRequest();

			model.put("sheetReq", sheetReq);
			model.put("sheetList", sheetList);
			model.put("totalEfforts", totalEfforts);
			model.put("fromDate", fromDate);
			model.put("endDate", endDate);

			sessionobj.setTsTotalEfforts(totalEfforts);
			sessionobj.setTsFromDate(fromDate);
			sessionobj.setTsToDate(endDate);

			Map<String, Object> mapModel = new HashMap<String, Object>();

			modelObj = new ModelAndView("efforsEmployeeList", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "updateSingleEfforts/{employeeId}", method = RequestMethod.POST)
	public String updateSingleEfforts(@PathVariable int employeeId, ModelMap model, HttpServletRequest req)
			throws ParseException {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			int projId = Integer.parseInt(req.getParameter("projId"));
			String fromDate = req.getParameter("fromDate");
			String endDate = req.getParameter("endDate");
			int totalEfforts = Integer.parseInt(req.getParameter("totalEfforts"));
			int actalEfforts = Integer.parseInt(req.getParameter("actalEfforts"));
			int billableEfforts = Integer.parseInt(req.getParameter("billableEfforts"));
			System.out.println("employeeId=" + employeeId + ",projId=" + projId + ",fromDate=" + fromDate + ",endDate="
					+ endDate + ",actalEfforts=" + actalEfforts + ",totalEfforts=" + totalEfforts);

			Date dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			Date dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			Employee emp = new Employee();
			emp.setEmpId(employeeId);
			Projects proj = new Projects();
			proj.setProjectId(projId);
			Timesheet sheet = new Timesheet();
			sheet.setEmpId(emp);
			sheet.setProjectId(proj);
			sheet.setNumberEfforts(totalEfforts);
			sheet.setTimesheetEfforts(actalEfforts);
			sheet.setBillableEfforts(billableEfforts);
			sheet.setFromDate(dateFrom);
			sheet.setToDate(dateTo);
			sheet.setCreatedEmpId(empUserId);
			timeSheetDao.saveOrUpdate(sheet);
			Map<String, Object> mapModel = new HashMap<String, Object>();

			return "redirect:/Timesheet/addEfforts/" + projId;
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "updateMultipleEfforts/{objArry}", method = RequestMethod.GET)
	public String updateMultipleEfforts(@PathVariable int[] objArry, @ModelAttribute TimesheetForm sheetForm,
			BindingResult result, ModelMap model, HttpServletRequest req) throws ParseException {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Integer> actualEfforts = new ArrayList<>();
			List<Integer> billableEfforts = new ArrayList<>();
			List<Integer> empId = new ArrayList<>();
			List<Integer> projId = new ArrayList<>();

			int t1 = 0, t2 = 1, t3 = 2,t4=3;
			for (int i = 0; i < objArry.length - 1; i++) {

				if (objArry.length > t1)
					actualEfforts.add(objArry[t1]);
				if (objArry.length > t2)
					empId.add(objArry[t2]);
				if (objArry.length > t3)
					projId.add(objArry[t3]);
				if (objArry.length > t4)
					billableEfforts.add(objArry[t4]);
				t1 = t1 + 4;
				t2 = t2 + 4;
				t3 = t3 + 4;
				t4 = t4 + 4;
			}
			
			
			
			System.out.println("emp==" + empId);
			System.out.println("projId==" + projId);
			System.out.println("eff==" + actualEfforts);
			System.out.println("billableEfforts==" + billableEfforts);

			int empUserId = sessionobj.getEmpObj().getEmpId();
			String fromDate = sessionobj.getTsFromDate();
			String endDate = sessionobj.getTsToDate();
			int totalEfforts = sessionobj.getTsTotalEfforts();

			Date dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			Date dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			Employee emp = null;
			Projects proj = null;
			Timesheet sheet = null;

			for (int i = 0; i < empId.size(); i++) {
				
				sheet = new Timesheet();
				emp = new Employee();
				emp.setEmpId(empId.get(i));
				proj = new Projects();
				proj.setProjectId(projId.get(i));

				sheet.setEmpId(emp);
				sheet.setProjectId(proj);
				sheet.setFromDate(dateFrom);
				sheet.setToDate(dateTo);
				sheet.setNumberEfforts(totalEfforts);
				sheet.setTimesheetEfforts(actualEfforts.get(i));
				sheet.setBillableEfforts(billableEfforts.get(i));
				System.out.println("billable efforts"  + billableEfforts.get(i));
				
				sheet.setCreatedEmpId(empUserId);
				timeSheetDao.saveOrUpdate(sheet);
			}
			//}
			
			return "redirect:/Timesheet/getEffortsEmpList/"+empId.get(0);
			//return "effortsEmployeeList2";
		} else {
			return "redirect:/login";
		}
	}

	@ResponseBody
	@RequestMapping(value = "getResourcesList")
	public String getResorceList(@RequestParam Integer projectId, ModelMap model) throws IOException {
		String employees = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empId = sessionobj.getEmpObj().getEmpId();
			List<Employee> empList = timeSheetDao.getResourcesByProjId(projectId, empId);
			if (empList != null && empList.size() > 0) {
				Iterator<Employee> itr = empList.iterator();
				StringBuffer sbrObj = new StringBuffer();
				model.put("empList", empList);
				while (itr.hasNext()) {
					Employee emp = itr.next();
					sbrObj.append("<option value='").append(emp.getEmpId()).append("'>").append(emp.getEmployeeName())
							.append("</option>");
				}
				if (sbrObj != null)
					employees = sbrObj.toString();
			}
		}
		return employees;
	}

	@ResponseBody
	@RequestMapping(value = "getActualEfforts")
	public String getActualEfforts(@RequestParam Date startDate, @RequestParam Date endDate, ModelMap model)
			throws IOException {
		String actalEfforts = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empId = sessionobj.getEmpObj().getEmpId();
			int holidays = timeSheetDao.getActualEfforts(startDate, endDate);

			int efforts = (5 - holidays) * 8;
			StringBuffer sbrObj = new StringBuffer();
			sbrObj.append("'").append(efforts).append("'");
			if (sbrObj != null)
				actalEfforts = sbrObj.toString();
		}
		return actalEfforts;
	}

	@RequestMapping(value = "createOrUpdateEfforts", method = RequestMethod.POST)
	public String createOrUpdateEfforts(@ModelAttribute TimesheetRequest sheetRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Timesheet sheet = new Timesheet();
			System.out.println("sheetRequest.getId()==" + sheetRequest.getId());
			if (sheetRequest.getId() != 0) {
				sheet.setId(sheetRequest.getId());
				int utilEfts = sheetRequest.getTimesheetEfforts();
				int billableEft = sheetRequest.getBillableEfforts();
				System.out.println("utilEfts==" + utilEfts);
				System.out.println("billableEft "  +billableEft);
				// int diffEfts = sheetRequest.getNumberEfforts() -
				// sheetRequest.getEstimatedEfforts();
				timeSheetDao.updateEfforts(sheetRequest.getId(), utilEfts, billableEft);
				//timeSheetDao.updateEfforts(sheetRequest.getId(), utilEfts);
				return "redirect:/Timesheet/getActualEffortsList";
			} else {
				sheet.setEmpId(sheetRequest.getEmpId());
				sheet.setProjectId(sheetRequest.getProjectId());
				sheet.setFromDate(sheetRequest.getFromDate());
				sheet.setToDate(sheetRequest.getToDate());
				sheet.setNumberEfforts(sheetRequest.getNumberEfforts());
				sheet.setEstimatedEfforts(sheetRequest.getEstimatedEfforts());
				if (sheetRequest.getEstimatedEfforts() != 0)
					sheet.setDiffEfforts(sheetRequest.getNumberEfforts() - sheetRequest.getEstimatedEfforts());
				sheet.setCreatedDate(new Date());
				sheet.setCreatedEmpId(sessionobj.getEmpObj().getEmpId());

				timeSheetDao.saveOrUpdate(sheet);
			}
			return "redirect:/Timesheet/createActualEfforts";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "getActualEffortsList", method = RequestMethod.GET)
	public ModelAndView actualEffortsList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Timesheet> actualEfforts = timeSheetDao
					.getActualEffortsList(sessionobj.getEmpObj().getEmpId());
			modelObj = new ModelAndView("actualEffortsList", "effortsList", actualEfforts);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	public String getDateFormat(Date unformattedDate) {
		Date dt = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String strDate = formatter.format(unformattedDate);
		return strDate;
	}

	@RequestMapping(value = "/editEfforts/{id}", method = RequestMethod.GET)
	public ModelAndView editProject(@PathVariable String id, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Timesheet> actualEfforts = timeSheetDao
					.getActualEffortsList(sessionobj.getEmpObj().getEmpId());
			Timesheet sheet = timeSheetDao.getActualEffortsById(Integer.parseInt(id));
			if (sheet != null) {
				if (sheet.getFromDate() != null)
					sheet.setFromDateStr(getDateFormat(sheet.getFromDate()));
				if (sheet.getToDate() != null) {
					sheet.setToDateStr(getDateFormat(sheet.getToDate()));
				}
				TimesheetRequest shetReq = new TimesheetRequest();

				Map<String, Object> mapModel = new HashMap<String, Object>();
				model.put("shetReq", shetReq);
				model.put("sheet", sheet);
				model.put("actualEfforts", actualEfforts);
				// model.put("projects", projects);
				modelObj = new ModelAndView("editActualEfforts", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/managerTimesheetReport", method = RequestMethod.GET)
	public String managerTimesheetReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "managerTimesheetReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}
	}

	// Adding Efforts Employee Wise
	@RequestMapping(value = "addEffortsEmployee", method = RequestMethod.POST)
	public ModelAndView addEffortsEmployee(ModelMap model, HttpServletRequest req) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			String fromDate = req.getParameter("startdate");
			String endDate = req.getParameter("enddate");
			int totalEfforts = Integer.parseInt(req.getParameter("noOfEfforts"));
			System.out.println("fromDate=" + fromDate + ",endDate" + endDate);
			List<Resourceallocations> resList = timeSheetDao
					.getResourcesListByManager(sessionobj.getEmpObj().getEmpId());

			List<Timesheet> sheetList = new ArrayList<>();
			List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
			List<Projects> projList = projectDao.getActiveProjects();
			Employee emp = null;
			Projects proj = null;
			Timesheet sheet = null;
			for (Resourceallocations res : resList) {
				sheet = new Timesheet();
				emp = compareEmplyee(res.getEmployeeId(), empList);
				proj = compareProjects(res.getProjectId(), projList);
				sheet.setEmpId(emp);
				sheet.setProjectId(proj);
				sheetList.add(sheet);
			}
			TimesheetRequest sheetReq = new TimesheetRequest();

			model.put("sheetReq", sheetReq);
			// model.put("sheetList", sheetList);
			model.put("totalEfforts", totalEfforts);
			model.put("fromDate", fromDate);
			model.put("endDate", endDate);
			model.put("empList", empList);

			sessionobj.setTsTotalEfforts(totalEfforts);
			sessionobj.setTsFromDate(fromDate);
			sessionobj.setTsToDate(endDate);

			Map<String, Object> mapModel = new HashMap<String, Object>();
			modelObj = new ModelAndView("efforsEmployeeList2", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "getEffortsEmpList/{emp_Id}")
	public String getEffortsEmpList(@PathVariable Integer emp_Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
			
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate=null,toDate=null;
			try {
				fromDate = format1.parse(sessionobj.getTsFromDate());
				 toDate=format1.parse(sessionobj.getTsToDate());
				 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Resourceallocations> resList = timeSheetDao
					.getResourcesListByManagerByEmployee(sessionobj.getEmpObj().getEmpId(), emp_Id,format2.format(fromDate),format2.format(toDate));
			List<Timesheet> sheetList = new ArrayList<>();
			List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
			List<Projects> projList = projectDao.getActiveProjects();
			Employee employee=empDao.getEmployeeData(String.valueOf(emp_Id));
			Employee emp = null;
			Projects proj = null;
			Timesheet sheet = null;
			for (Resourceallocations res : resList) {
				sheet = new Timesheet();
				emp = compareEmplyee(res.getEmployeeId(), empList);
				proj = compareProjects(res.getProjectId(), projList);
				if(proj!=null){
				sheet.setEmpId(emp);
				sheet.setProjectId(proj);
				
				sheetList.add(sheet);
				}
			}
			
			TimesheetRequest sheetReq = new TimesheetRequest();

			model.put("sheetReq", sheetReq);
			model.put("sheetList", sheetList);
			model.put("empList", empList);
			if (sheetList != null && !sheetList.isEmpty())
				model.put("empObj", sheetList.get(0).getEmpId());
			else
				model.put("empObj", employee);

			return "efforsEmployeeList2";
		}
		return "login";
	}
	
	@RequestMapping(value = "timesheetsSubmitted", method = RequestMethod.GET)
	public ModelAndView timesheetsSubmitted(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Map<String, Object> mapModel = new HashMap<String, Object>();

			modelObj = new ModelAndView("timesheetSubmitted", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "updateMultipleEffortsByProj/{objArry}", method = RequestMethod.GET)
	public String updateMultipleEffortsByProj(@PathVariable int[] objArry, @ModelAttribute TimesheetForm sheetForm,
			BindingResult result, ModelMap model, HttpServletRequest req) throws ParseException {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Integer> actualEfforts = new ArrayList<>();
			List<Integer> billableEfforts = new ArrayList<>();
			List<Integer> empId = new ArrayList<>();
			List<Integer> projId = new ArrayList<>();

			int t1 = 0, t2 = 1, t3 = 2,t4=3;
			for (int i = 0; i < objArry.length - 1; i++) {

				if (objArry.length > t1)
					actualEfforts.add(objArry[t1]);
				if (objArry.length > t2)
					empId.add(objArry[t2]);
				if (objArry.length > t3)
					projId.add(objArry[t3]);
				if (objArry.length > t4)
					billableEfforts.add(objArry[t4]);
				t1 = t1 + 4;
				t2 = t2 + 4;
				t3 = t3 + 4;
				t4 = t4 + 4;
			}
			
			
			
			System.out.println("emp==" + empId);
			System.out.println("projId==" + projId);
			System.out.println("eff==" + actualEfforts);
			System.out.println("billableEfforts==" + billableEfforts);

			int empUserId = sessionobj.getEmpObj().getEmpId();
			String fromDate = sessionobj.getTsFromDate();
			String endDate = sessionobj.getTsToDate();
			int totalEfforts = sessionobj.getTsTotalEfforts();

			Date dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			Date dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			Employee emp = null;
			Projects proj = null;
			Timesheet sheet = null;

			for (int i = 0; i < projId.size(); i++) {
				
				sheet = new Timesheet();
				emp = new Employee();
				emp.setEmpId(empId.get(i));
				proj = new Projects();
				proj.setProjectId(projId.get(i));

				sheet.setEmpId(emp);
				sheet.setProjectId(proj);
				sheet.setFromDate(dateFrom);
				sheet.setToDate(dateTo);
				sheet.setNumberEfforts(totalEfforts);
				sheet.setTimesheetEfforts(actualEfforts.get(i));
				sheet.setBillableEfforts(billableEfforts.get(i));
				System.out.println("billable efforts"  + billableEfforts.get(i));
				
				sheet.setCreatedEmpId(empUserId);
				timeSheetDao.saveOrUpdate(sheet);
			}
			//}
			
			return "redirect:/Timesheet/getEffortsProjList/"+projId.get(0);
			//return "effortsEmployeeList2";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "createProjectEfforts", method = RequestMethod.POST)
	public ModelAndView createProjectEfforts(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empUserId = sessionobj.getEmpObj().getEmpId();
			TimesheetRequest sheetReq = new TimesheetRequest();
			List<Projects> projList = timeSheetDao.getProjectsByEmpId(empUserId);
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("sheetReq", sheetReq);
			model.put("projList", projList);

			modelObj = new ModelAndView("projectEffortsAdding", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	// Adding Efforts Project Wise
		@RequestMapping(value = "addEffortsProject", method = RequestMethod.POST)
		public ModelAndView addEffortsProject(ModelMap model, HttpServletRequest req) {
			ModelAndView modelObj = null;
			if (sessionobj != null && sessionobj.getIsValidLogin()) {
				int empUserId = sessionobj.getEmpObj().getEmpId();
				//int empUserId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
				String fromDate = req.getParameter("startdate");
				String endDate = req.getParameter("enddate");
				int totalEfforts = Integer.parseInt(req.getParameter("noOfEfforts"));
				System.out.println("fromDate=" + fromDate + ",endDate" + endDate);
				List<Resourceallocations> resList = timeSheetDao
						.getResourcesListByManager(sessionobj.getEmpObj().getEmpId());

				List<Timesheet> sheetList = new ArrayList<>();
				List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
				List<Projects> projList = projectDao.getActiveProjects();
				Employee emp = null;
				Projects proj = null;
				Timesheet sheet = null;
				for (Resourceallocations res : resList) {
					sheet = new Timesheet();
					emp = compareEmplyee(res.getEmployeeId(), empList);
					proj = compareProjects(res.getProjectId(), projList);
					sheet.setEmpId(emp);
					sheet.setProjectId(proj);
					sheetList.add(sheet);
				}
				TimesheetRequest sheetReq = new TimesheetRequest();

				model.put("sheetReq", sheetReq);
				// model.put("sheetList", sheetList);
				model.put("totalEfforts", totalEfforts);
				model.put("fromDate", fromDate);
				model.put("endDate", endDate);
				model.put("empList", empList);
				model.put("projList", projList);

				sessionobj.setTsTotalEfforts(totalEfforts);
				sessionobj.setTsFromDate(fromDate);
				sessionobj.setTsToDate(endDate);

				Map<String, Object> mapModel = new HashMap<String, Object>();
				modelObj = new ModelAndView("efforsProjectList", mapModel);
			} else {
				Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
				modelObj = getLogoutView(resp);
			}
			return modelObj;
		}
		
		@RequestMapping(value = "getEffortsProjList/{projectId}")
		public String getEffortsProjList(@PathVariable Integer projectId, HttpServletRequest request,
				HttpServletResponse response, ModelMap model) throws IOException {
			ModelAndView modelObj = null;
			if (sessionobj != null && sessionobj.getIsValidLogin()) {
				int empUserId = sessionobj.getEmpObj().getEmpId();
				SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
				
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				Date fromDate=null,toDate=null;
				try {
					fromDate = format1.parse(sessionobj.getTsFromDate());
					 toDate=format1.parse(sessionobj.getTsToDate());
					 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*List<Resourceallocations> resList = timeSheetDao
						.getResourcesListByManagerByEmployee(sessionobj.getEmpUsersObj().getEmpId().getEmpId(), emp_Id,format2.format(fromDate),format2.format(toDate));*/
				List<Resourceallocations> resList = timeSheetDao
						.getProjectListByManagerByEmployee(sessionobj.getEmpObj().getEmpId(), projectId, format2.format(fromDate), format2.format(toDate));
				/*for(Resourceallocations res :resList)
				{
					System.out.println("resList=="+res);
				
				}*/
				System.out.println("reslist size in controller ====" + resList.size());
				
				List<Timesheet> sheetList = new ArrayList<>();
				List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
				List<Projects> projList = projectDao.getActiveProjects();
				//Employee employee=empDao.getEmployeeData(String.valueOf(emp_Id));
				Projects projects = projectDao.getProjectData(String.valueOf(projectId));
				Employee emp = null;
				Projects proj = null;
				Timesheet sheet = null;
				for (Resourceallocations res : resList) {
					sheet = new Timesheet();
					emp = compareEmplyee(res.getEmployeeId(), empList);
					proj = compareProjects(res.getProjectId(), projList);
					if(emp!=null){
					sheet.setEmpId(emp);
					sheet.setProjectId(proj);
					
					sheetList.add(sheet);
					}
				}
				
				TimesheetRequest sheetReq = new TimesheetRequest();

				model.put("sheetReq", sheetReq);
				model.put("sheetList", sheetList);
				model.put("empList", empList);
				model.put("projList", projList);
				if (sheetList != null && !sheetList.isEmpty())
					model.put("projObj", sheetList.get(0).getProjectId());
				else
					//model.put("empObj", employee);
				    model.put("projObj", projects);
				
				
				return "efforsProjectList";
			}
			return "login";
		}
		
		
		 @RequestMapping(value = "/pmoMonthlyReport", method = RequestMethod.GET)
		 public String pmoMonthlyReport(ModelMap model) {
		  ModelAndView modelObj = null;
		  if (sessionobj != null && sessionobj.getIsValidLogin()) {
		   return "pmoTimesheetMonthlyReport";
		  } else {
		   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
		   modelObj = getLogoutView(resp);
		   return "redirect:/login";
		  }

		 }
		 
		 
		 @RequestMapping(value = "account", method = RequestMethod.GET)
			public ModelAndView account(ModelMap model) {
				ModelAndView modelObj = null;
				if (sessionobj != null && sessionobj.getIsValidLogin()) {
					//int empUserId = sessionobj.getEmpObj().getEmpId();
					System.out.println("account");
					TimesheetRequest sheetReq = new TimesheetRequest();
					//List<Projects> projList = timeSheetDao.getProjectsByEmpId(empUserId);
					Map<String, Object> mapModel = new HashMap<String, Object>();
					model.put("sheetReq", sheetReq);
					//model.put("projList", projList);

					modelObj = new ModelAndView("account", mapModel);
				} else {
					Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
					modelObj = getLogoutView(resp);
				}
				return modelObj;
			}
		 
		 
		 @RequestMapping(value = "accountsTeam", method = RequestMethod.POST)
			public ModelAndView accountsTeam(ModelMap model, HttpServletRequest req) {
				ModelAndView modelObj = null;
				if (sessionobj != null && sessionobj.getIsValidLogin()) {
					//int empUserId = sessionobj.getEmpObj().getEmpId();
					String fromDate = req.getParameter("startdate");
					String endDate = req.getParameter("enddate");
				//	int totalEfforts = Integer.parseInt(req.getParameter("noOfEfforts"));
					System.out.println("fromDate=" + fromDate + ",endDate" + endDate);
					List<Employee> usersEmpList = usersDao.getListEmpUsers();
					/*List<Resourceallocations> resList = timeSheetDao
							.getResourcesListByManager(sessionobj.getEmpObj().getEmpId());
*/
					List<Timesheet> sheetList = new ArrayList<>();
					//List<Employee> empList = empDao.getEmployeesUnderManagers(empUserId);
					List<Projects> projList = projectDao.getActiveProjects();
					Employee emp = null;
					Projects proj = null;
					Timesheet sheet = null;
					/*for (Resourceallocations res : resList) {
						sheet = new Timesheet();
						//emp = compareEmplyee(res.getEmployeeId(), empList);
						proj = compareProjects(res.getProjectId(), projList);
						sheet.setEmpId(emp);
						sheet.setProjectId(proj);
						sheetList.add(sheet);
					}*/
					TimesheetRequest sheetReq = new TimesheetRequest();

					model.put("sheetReq", sheetReq);
					// model.put("sheetList", sheetList);
					//model.put("totalEfforts", totalEfforts);
					model.put("fromDate", fromDate);
					model.put("endDate", endDate);
					//model.put("empList", empList);
					model.put("usersEmpList", usersEmpList);

					//sessionobj.setTsTotalEfforts(totalEfforts);
					sessionobj.setTsFromDate(fromDate);
					sessionobj.setTsToDate(endDate);

					Map<String, Object> mapModel = new HashMap<String, Object>();
					modelObj = new ModelAndView("accountsTeam", mapModel);
				} else {
					Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
					modelObj = getLogoutView(resp);
				}
				return modelObj;
			}

		 
}
