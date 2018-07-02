package com.gcs.controller;

import java.awt.Font;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.bean.ResourceReportBean;
import com.gcs.bean.SalesReportBean;
import com.gcs.bean.TimeSheetReportBean;
import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.SkillSet;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Timesheet;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.db.businessDao.Workplace;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.CategoryRequest;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.requestDao.SendMailRequest;
import com.gcs.requestDao.SkillSetRequest;
import com.gcs.requestDao.UserEmployeeRequest;
import com.gcs.responseDao.EmployeeResponseReport;
import com.gcs.responseDao.Response;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("Employee")
public class EmployeeController extends BaseController {
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;

	@Autowired
	private UsersDao usersDao;
	@Autowired
	private SessionData sessionobj;

	String filePath = null;
	Part part = null;

	private List<Category> categoryCmpList;
	private List<Countries> countryCmpList;
	private List<State> stateCmpList;
	private List<Workplace> workplaceCmpList;
	private List<Cities> cityCmpList;

	public EmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	

	@RequestMapping(value = "deleteAllEmployees", method = RequestMethod.GET)
	public ModelAndView deleteAllEmployees(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			empDao.deleteAllEmployees();
			List<Employee> empList = empDao.getActiveEmployeeList();
			modelObj = new ModelAndView("activeEmployee", "empList", empList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "email", method = RequestMethod.GET)
	public ModelAndView email(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			SendMailRequest sendMailRequest = new SendMailRequest();

			sendMailRequest.setToEmail(sessionobj.getToEmail());
			sendMailRequest.setSubject(sessionobj.getMailSubject());
			sendMailRequest.setMessage(sessionobj.getMailMessage());

			modelObj = new ModelAndView("sendMail", "SendMailRequest", sendMailRequest);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "sendEmail", method = RequestMethod.POST)
	public String sendEmail(@ModelAttribute SendMailRequest sendMailRequest, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			HSSFWorkbook workbook = null;
			List<EmployeeResponseReport> reportObj = sessionobj.getResponseReport();
			String fileName = "EmpDataReport.xls";
			try {
				switch (sessionobj.getReportFrom()) {
				case "swipe":
					workbook = getHssfSwipeWorkBook(reportObj, fileName);
					break;
				case "monthly":
					workbook = getMonthlyHoursWorkBook(reportObj, fileName);
					break;
				default:
					workbook = getWeeklyHoursWorkBook(reportObj, fileName);
					break;
				}

				com.gcs.resourceMethods.EmailUtility.sendEmailCall(sendMailRequest.getToEmail(), fileName, workbook,
						sendMailRequest.getCcEmails(), sendMailRequest.getSubject(), sendMailRequest.getMessage());

				sessionobj.setStatusMessage("Email sent Successfully");

			} catch (Exception ex) {
			}
			return "redirect:/Reports/" + sessionobj.getEmialCallPath();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createEmployee", method = RequestMethod.GET)
	public ModelAndView createEmployee(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			List<SkillSet> skills = categoryDao.getSkillSet();
			List<Countries> countryList = empDao.getCountryList();
			List<Employee> empList = empDao.getActiveEmployeeList();
			List<Employee> usersEmpList = usersDao.getListEmpUsers();
			List<Workplace> workplaceList = empDao.getWorkplaceList();
			EmployeeRequest empReq = new EmployeeRequest();
			//UserEmployeeRequest usersEmpReq = new UserEmployeeRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("EmployeeRequest", empReq);
			model.put("categoryList", categoryList);
			model.put("skills", skills);
			model.put("countryList", countryList);
			model.put("workplaceList", workplaceList);

			model.put("empList", empList);
			model.put("usersEmpList", usersEmpList);
			//model.put("usersEmpReq", usersEmpReq);
			modelObj = new ModelAndView("createEmployee", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/editEmployee/{empId}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {

				if (empObj.getJoinDate() != null) {
					empObj.setJoinDateStr(getDateFormat(empObj.getJoinDate()));
				}
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				List<Employee> usersList = usersDao.getListEmpUsers();
				List<Workplace> workplaceList = empDao.getWorkplaceList();

				List<EmployeeSkillSet> skillsList = categoryDao.getSkillSetByEmployee(Integer.parseInt(empId));
				List<SkillSet> skills = categoryDao.getSkillSet();

				mapModel.put("skillsList", skillsList);
				mapModel.put("skills", skills);
				mapModel.put("workplaceList", workplaceList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("usersList", usersList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("editEmployee", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@ResponseBody
	@RequestMapping(value = "getStatesList")
	public String getStatesList(@RequestParam Integer country_Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		String States = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<State> stateList = empDao.getSatesList(country_Id != null ? Integer.valueOf(country_Id) : 1);
			if (stateList != null && stateList.size() > 0) {
				Iterator<State> itr = stateList.iterator();
				StringBuffer sbrObj = new StringBuffer();
				model.put("stateList", stateList);
				while (itr.hasNext()) {
					State state = itr.next();
					sbrObj.append("<option value='").append(state.getId()).append("'>").append(state.getName())
							.append("</option>");
				}
				if (sbrObj != null)
					States = sbrObj.toString();
			}
		}
		return States;
	}

	@ResponseBody
	@RequestMapping(value = "getCitiesList")
	public String getCitiesList(@RequestParam Integer state_Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		String Cities = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Cities> cityList = empDao.getCitiesList(state_Id != null ? Integer.valueOf(state_Id) : 1);
			if (cityList != null && cityList.size() > 0) {
				model.put("cityList", cityList);
				Iterator<Cities> itr = cityList.iterator();
				StringBuffer sbrObj = new StringBuffer();
				while (itr.hasNext()) {
					Cities city = itr.next();
					sbrObj.append("<option value='").append(city.getId()).append("'>").append(city.getName())
							.append("</option>");
				}
				if (sbrObj != null)
					Cities = sbrObj.toString();
			}
		
		}
		return Cities;
	}

	public String getDateFormat(Date unformattedDate) {
		Date dt = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(unformattedDate);
		return strDate;
	}


	@RequestMapping(value = "/manageEmployee/{empId}", method = RequestMethod.GET)
	public ModelAndView manageEmployee(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {

				if (empObj.getJoinDate() != null) {
					empObj.setJoinDateStr(getDateFormat(empObj.getJoinDate()));
				}
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				
				List<Employee> usersList = usersDao.getListEmpUsers();
				List<Workplace> workplaceList = empDao.getWorkplaceList();
				
				List<EmployeeSkillSet> skillsList = categoryDao.getSkillSetByEmployee(Integer.parseInt(empId));
				List<SkillSet> skills = categoryDao.getSkillSet();
				// mapModel.put("countryList", countryList);
				mapModel.put("workplaceList", workplaceList);

				// mapModel.put("stateList", stateList);
				// mapModel.put("cityList", cityList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("usersList", usersList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);
				mapModel.put("skillsList", skillsList);
				mapModel.put("skills", skills);

				modelObj = new ModelAndView("manageEmployee", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/setExitDate/{empId}", method = RequestMethod.GET)
	public ModelAndView setExitDate(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {

				if (empObj.getJoinDate() != null) {
					empObj.setJoinDateStr(getDateFormat(empObj.getJoinDate()));
				}
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("setExitDate", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/setExitDate", method = RequestMethod.POST)
	public String updateEmployeeExitDate(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = formatter.format(empRequest.getExitDate());

			empDao.updateEmployeeExitDate(empRequest.getEmpId(), strDate);
			// empDao.updateEmployeeAsBench(empRequest.getEmpId(), strDate);

			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/setEmployeeBench/{empId}", method = RequestMethod.GET)
	public ModelAndView setEmployeeBench(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {

				if (empObj.getJoinDate() != null) {
					empObj.setJoinDateStr(getDateFormat(empObj.getJoinDate()));
				}
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("setEmpBench", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/setEmpInBench", method = RequestMethod.POST)
	public String updateEmployeeAsBench(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = formatter.format(empRequest.getBenchDate());
			empDao.updateEmployeeAsBench(empRequest.getEmpId(), strDate);

			return "redirect:/Reports/getAllResorcesUnderManager";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/Assign/{empId}", method = RequestMethod.GET)
	public String updateEmployeeAsAssigned(@PathVariable int empId, @ModelAttribute EmployeeRequest empRequest,
			BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {

			empDao.updateEmployeeAsAssigned(empId);

			return "redirect:/Reports/getAllResorcesUnderManager";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/deleteEmployee/{empId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable Integer empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				Boolean isTrue = empDao.setEndDateByEmpId(empId, getCurrentDate());
				empDao.delete(empId, true);

				// Boolean isTrue=emp
			}
			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	
	@RequestMapping(value = "createOrUpdateEmployee", method = RequestMethod.POST)
	public String createOrUpdateEmployee(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {

			Employee empObj = new Employee();
			if (empRequest.getEmpId() != null) {
				empObj.setEmpId(empRequest.getEmpId());
				
			}

			empObj.setEmployeeId(empRequest.getEmployeeId());
			empObj.setEmployeeName(empRequest.getEmployeeName());
			 empObj.setEmployeeCategory(empRequest.getCategory());
			empObj.setEmployeeCountry(empRequest.getCountries());
			empObj.setWorkplace(empRequest.getWorkplace());
			empObj.setEmployeeState(empRequest.getStates());
			empObj.setEmployeeCity(empRequest.getCities());
			empObj.setEmployeeDesg(empRequest.getEmployeeDesg());
			empObj.setEmployeeSpecialization(empRequest.getEmployeeSpecialization());
			empObj.setEmployeeMobilenumber(empRequest.getEmployeeMobilenumber());
			empObj.setEmployeeExperience(empRequest.getEmployeeExperience());

			empObj.setStatus(true);
			empObj.setJoinDate(empRequest.getJoinDate());
			if(empRequest.getReportingTo()!=null)
			empObj.setReportingTo(empRequest.getReportingTo());
			empObj.setEmailId(empRequest.getEmailId());
			empObj.setBenchStatus(true);
			empObj.setBenchDate(null);
						try
			{
			empDao.saveOrUpdate(empObj);
			}
			catch(Exception e)
			{
			}

			Employee emp = empDao.getEmployeeDataByEmployeeId(empRequest.getEmployeeId());
			categoryDao.deleteEmpSkill(emp.getEmpId());
			
			String skillSet[] = req.getParameterValues("skills");
			EmployeeSkillSet empSkill = null;
			SkillSet setSkill = null;
			if(skillSet!= null){
				
			for (String skill : skillSet) {
				
				
				empSkill = new EmployeeSkillSet();
				setSkill = new SkillSet();
				
				
				setSkill.setSkillId(Integer.parseInt(skill));
				empSkill.setSkillSet(setSkill);
				empSkill.setEmpId(emp);
				categoryDao.saveOrUpdate(empSkill);
			}
			}
			
			

			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createOrUpdateManagerEmployee", method = RequestMethod.POST)
	public String createOrUpdateManagerEmployee(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model , HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Employee empObj = new Employee();
			if (empRequest.getEmpId() != null) {
				empObj.setEmpId(empRequest.getEmpId());
				
			}

			empObj.setEmployeeId(empRequest.getEmployeeId());
			empObj.setEmployeeName(empRequest.getEmployeeName());
			empObj.setEmployeeCategory(empRequest.getCategory());
			empObj.setEmployeeCountry(empRequest.getCountries());
			empObj.setWorkplace(empRequest.getWorkplace());
			empObj.setEmployeeState(empRequest.getStates());
			empObj.setEmployeeCity(empRequest.getCities());
			empObj.setEmployeeDesg(empRequest.getEmployeeDesg());
			empObj.setEmployeeSpecialization(empRequest.getEmployeeSpecialization());
			empObj.setEmployeeMobilenumber(empRequest.getEmployeeMobilenumber());
			empObj.setEmployeeExperience(empRequest.getEmployeeExperience());

			empObj.setStatus(true);
			empObj.setJoinDate(empRequest.getJoinDate());
			empObj.setReportingTo(empRequest.getReportingTo());
			empObj.setEmailId(empRequest.getEmailId());
			empObj.setBenchStatus(true);
			empObj.setBenchDate(null);

			empDao.saveOrUpdate(empObj);
			Employee emp = empDao.getEmployeeDataByEmployeeId(empRequest.getEmployeeId());
			categoryDao.deleteEmpSkill(emp.getEmpId());
			
			String skillSet[] = req.getParameterValues("skills");
			EmployeeSkillSet empSkill = null;
			SkillSet setSkill = null;
			if(skillSet!= null){
				
			for (String skill : skillSet) {
				
				
				empSkill = new EmployeeSkillSet();
				setSkill = new SkillSet();
				
				
				setSkill.setSkillId(Integer.parseInt(skill));
				empSkill.setSkillSet(setSkill);
				empSkill.setEmpId(emp);
				categoryDao.saveOrUpdate(empSkill);
			}
			}
			return "redirect:/Reports/getAllResorcesUnderManager";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/editEmpUnderManger/{empId}", method = RequestMethod.GET)
	public ModelAndView editEmpUnderManger(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {

				if (empObj.getJoinDate() != null) {
					empObj.setJoinDateStr(getDateFormat(empObj.getJoinDate()));
				}
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				List<Employee> usersList = usersDao.getListEmpUsers();
				List<Workplace> workplaceList = empDao.getWorkplaceList();
				mapModel.put("workplaceList", workplaceList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("usersList", usersList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("editEmpUnderManger", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "activeEmpUnderManager", method = RequestMethod.GET)
	public ModelAndView activeEmpUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		int id = sessionobj.getEmpObj().getEmpId();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getEmployeesUnderManagers(id);
			List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("employeeList", employeeList);

			modelObj = new ModelAndView("activeEmpUnderManager", model);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "inActiveEmpUnderManager", method = RequestMethod.GET)
	public ModelAndView inActiveEmpUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		int id = sessionobj.getEmpObj().getEmpId();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getInActiveEmpUnderManager(id);
			List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("empList", employeeList);

			modelObj = new ModelAndView("inActiveEmpUnderManager", model);

		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/deleteEmpUnderManager/{empId}", method = RequestMethod.GET)
	public String deleteEmpUnderManager(@PathVariable Integer empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				Boolean isTrue = empDao.setEndDateByEmpId(empId, getCurrentDate());
				empDao.delete(empId, true);

				// Boolean isTrue=emp
			}
			return "redirect:/Employee/activeEmpUnderManager";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/updateEmpUnderManager/{empId}", method = RequestMethod.GET)
	public String updateEmpUnderManager(@PathVariable String empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				empDao.updateStatus(empId, true);
			}
			return "redirect:/Employee/inActiveEmpUnderManager";
		} else
			return "redirect:/login";
	}

	
	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	@RequestMapping(value = "employeeBulkUpload", method = RequestMethod.GET)
	public String employeeBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "employeeBulkUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping
	public Category compareCategory(List<Category> listCategory, String categoryName) {
		Category category = null;
		for (Category cat : listCategory) {
			if (cat.getCategoryName().equalsIgnoreCase(categoryName)) {
				category = new Category();
				category.setCategoryId(cat.getCategoryId());
				category.setCategoryName(cat.getCategoryName());
			}
		}

		return category;
	}

	@RequestMapping
	public Countries compareCountry(List<Countries> listCountry, String countryName) {
		Countries country = null;

		for (Countries cun : listCountry) {
			if (cun.getName().equalsIgnoreCase(countryName)) {
				country = new Countries();
				country.setId(cun.getId());
				country.setName(cun.getName());
				country.setPhonecode(cun.getPhonecode());
			}
		}
		return country;
	}

	@RequestMapping
	public State compareState(List<State> listStates, String stateName) {
		State state = null;

		for (State st : listStates) {
			if (st.getName().equalsIgnoreCase(stateName)) {
				state = new State();
				state.setId(st.getId());
				state.setName(st.getName());
				state.setCountryId(st.getCountryId());
			}
		}
		return state;
	}

	@RequestMapping
	public Workplace compareWorkPlace(List<Workplace> WorkplaceList, String Name) {
		Workplace workplace = null;
		for (Workplace wrk : WorkplaceList) {
			if (wrk.getName().equalsIgnoreCase(Name)) {
				workplace = new Workplace();
				workplace.setId(wrk.getId());
				workplace.setName(wrk.getName());
			}
		}

		return workplace;
	}

	@RequestMapping
	public Cities compareCity(List<Cities> listCities, String cityName) {
		Cities city = null;

		for (Cities ct : listCities) {
			if (ct.getName().equalsIgnoreCase(cityName)) {
				city = new Cities();
				city.setId(ct.getId());
				city.setName(ct.getName());
				city.setStateId(ct.getStateId());
				city.setCountryId(ct.getCountryId());
			}
		}
		return city;

	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				EmployeeController empController = new EmployeeController();

				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();
				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);
				List<Employee> empList = new ArrayList<Employee>();
				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int employeeId = -1, employeeCategory = -1, employeeName = -1, employeeDesg = -1,
						employeeSpecialization = -1, employeeWorkplace = -1, employeeCountry = -1, employeeState = -1,
						employeeCity = -1, employeeMobilenumber = -1, employeeExperience = -1, joinDate = -1,
						reportingTo = -1, emailId = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("Employee_Id".equalsIgnoreCase(cellValue) || "Employee Id".equalsIgnoreCase(cellValue)) {
						employeeId = j;
					} else if ("Employee_Name".equalsIgnoreCase(cellValue)
							|| "Employee Name".equalsIgnoreCase(cellValue)) {
						employeeName = j;
					} else if ("Employee_Designation".equalsIgnoreCase(cellValue)
							|| "Employee Designation".equalsIgnoreCase(cellValue)) {
						employeeDesg = j;
					} else if ("Employee_Category".equalsIgnoreCase(cellValue)
							|| "Employee Category".equalsIgnoreCase(cellValue)) {
						employeeCategory = j;
					} else if ("Employee_Workplace".equalsIgnoreCase(cellValue)
							|| "Employee Workplace".equalsIgnoreCase(cellValue)) {
						employeeWorkplace = j;
					} else if ("Country".equalsIgnoreCase(cellValue)) {
						employeeCountry = j;
					} else if ("State".equalsIgnoreCase(cellValue)) {
						employeeState = j;
					} else if ("City".equalsIgnoreCase(cellValue)) {
						employeeCity = j;
					} else if ("Specialization".equalsIgnoreCase(cellValue)) {
						employeeSpecialization = j;
					} else if ("Mobile_Number".equalsIgnoreCase(cellValue)
							|| "Mobile Number".equalsIgnoreCase(cellValue)) {
						employeeMobilenumber = j;
					} else if ("Employee_Experience".equalsIgnoreCase(cellValue)
							|| "Employee Experience".equalsIgnoreCase(cellValue)) {
						employeeExperience = j;
					} else if ("Reporting_To".equalsIgnoreCase(cellValue)
							|| "Reporting To".equalsIgnoreCase(cellValue)) {
						reportingTo = j;
					} else if ("Join_Date".equalsIgnoreCase(cellValue) || "Join Date".equalsIgnoreCase(cellValue)) {
						joinDate = j;
					} else if ("Email_Id".equalsIgnoreCase(cellValue) || "EmailId".equalsIgnoreCase(cellValue)) {
						emailId = j; 

					}

					if (employeeId == -1 || employeeName == -1 || employeeDesg == -1 || employeeSpecialization == -1
							|| employeeMobilenumber == -1 || employeeWorkplace == -1 || employeeCategory == -1
							|| employeeCountry == -1 || employeeState == -1 || employeeExperience == -1
							|| employeeCity == -1 || joinDate == -1 || reportingTo == -1 || emailId == -1) {
						try {
							throw new Exception("Could not find header indexes\nemployeeId : " + employeeId
									+ " | EmployeeName : " + employeeName);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					for (int i = 1; i < rowNum; i++) {
						XSSFRow row = ws.getRow(i);
						Employee employee = new Employee();

						DataFormatter df = new DataFormatter();
						String emId = df.formatCellValue(row.getCell(employeeId));
						employee.setEmployeeId(emId);
						employee.setEmployeeName(cellToString(row.getCell(employeeName)));
						employee.setEmployeeDesg(cellToString(row.getCell(employeeDesg)));
						employee.setEmployeeSpecialization(cellToString(row.getCell(employeeSpecialization)));
						employee.setEmployeeMobilenumber(cellToString(row.getCell(employeeMobilenumber)));
						employee.setEmailId(cellToString(row.getCell(emailId)));
						Date date1 = null;
						DataFormatter dataFormatter = new DataFormatter();
						String jnDate = dataFormatter.formatCellValue(row.getCell(joinDate));
						try {
							date1 = new SimpleDateFormat("dd-MM-yyyy").parse(jnDate);
							employee.setJoinDate(date1);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						employee.setJoinDateStr(jnDate);

						boolean categoryFlag = false;

						String ctgName = (cellToString(row.getCell(employeeCategory)));
						if (categoryCmpList == null) {
							categoryCmpList = empDao.getCategoryList();
						}
						Category category = empController.compareCategory(categoryCmpList, ctgName);

						for (Category catgry : categoryCmpList) {
							categoryFlag = false;
							if (catgry.getCategoryName().equalsIgnoreCase(ctgName)) {
								categoryFlag = true;
								category.setCategoryName(catgry.getCategoryName());
								break;
							}
						}

						if (categoryFlag == false) {
							category = new Category();
							category.setCategoryName(ctgName);
							
							category.setStatus(true);
							categoryDao.saveOrUpdate(category);
							categoryCmpList = categoryDao.getCategory();
						}

						employee.setEmployeeCategory(category);


						String cntName = cellToString(row.getCell(employeeCountry));
						if (countryCmpList == null) {
							countryCmpList = empDao.getCountryList();
						}
						String wrkName = cellToString(row.getCell(employeeWorkplace));
						if (workplaceCmpList == null) {
							workplaceCmpList = empDao.getWorkplaceList();
						}
						Workplace workplace = empController.compareWorkPlace(workplaceCmpList, wrkName);
						employee.setWorkplace(workplace);
						Countries country = empController.compareCountry(countryCmpList, cntName);
						employee.setEmployeeCountry(country);

						String stName = cellToString(row.getCell(employeeState));
						if (stateCmpList == null) {
							stateCmpList = empDao.getStates();
						}
						State state = empController.compareState(stateCmpList, stName);
						employee.setEmployeeState(state);

						String ctName = cellToString(row.getCell(employeeCity));
						if (cityCmpList == null) {
							cityCmpList = empDao.getCities();
						}
						Cities city = empController.compareCity(cityCmpList, ctName);
						employee.setEmployeeCity(city);
						employee.setStatus(true);
						empList.add(employee);
					}

					if (empList.size() > 0) {
						empDao.insert(empList);

					}
					fileStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	
	
	
	@RequestMapping(value = "categoryUpload", method = RequestMethod.GET)
	public String categoryUpload(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "categoryUpload";
		} else
			return "redirect:/login";
	}

	
	@RequestMapping(value = "categoryUpload", method = RequestMethod.POST)
	public String categoryFileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				EmployeeController empController = new EmployeeController();

				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();
				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);
				List<Employee> empList = new ArrayList<Employee>();
				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int employeeId = -1, employeeCategory = -1, employeeName = -1 ;
				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("Employee_Id".equalsIgnoreCase(cellValue) || "Employee Id".equalsIgnoreCase(cellValue)) {
						employeeId = j;
					} else if ("Employee_Name".equalsIgnoreCase(cellValue)
							|| "Employee Name".equalsIgnoreCase(cellValue)) {
						employeeName = j;
					/*} else if ("Employee_Designation".equalsIgnoreCase(cellValue)
							|| "Employee Designation".equalsIgnoreCase(cellValue)) {
						employeeDesg = j;*/
					} else if ("Employee_Category".equalsIgnoreCase(cellValue)
							|| "Employee Category".equalsIgnoreCase(cellValue)) {
						employeeCategory = j;
					} /*else if ("Employee_Workplace".equalsIgnoreCase(cellValue)
							|| "Employee Workplace".equalsIgnoreCase(cellValue)) {
						employeeWorkplace = j;
					} else if ("Country".equalsIgnoreCase(cellValue)) {
						employeeCountry = j;
					} else if ("State".equalsIgnoreCase(cellValue)) {
						employeeState = j;
					} else if ("City".equalsIgnoreCase(cellValue)) {
						employeeCity = j;
					} else if ("Specialization".equalsIgnoreCase(cellValue)) {
						employeeSpecialization = j;
					} else if ("Mobile_Number".equalsIgnoreCase(cellValue)
							|| "Mobile Number".equalsIgnoreCase(cellValue)) {
						employeeMobilenumber = j;
					} else if ("Employee_Experience".equalsIgnoreCase(cellValue)
							|| "Employee Experience".equalsIgnoreCase(cellValue)) {
						employeeExperience = j;
					} else if ("Reporting_To".equalsIgnoreCase(cellValue)
							|| "Reporting To".equalsIgnoreCase(cellValue)) {
						reportingTo = j;
					} else if ("Join_Date".equalsIgnoreCase(cellValue) || "Join Date".equalsIgnoreCase(cellValue)) {
						joinDate = j;
					} else if ("Email_Id".equalsIgnoreCase(cellValue) || "EmailId".equalsIgnoreCase(cellValue)) {
						emailId = j; 
										 * else if
										 * ("Status".equalsIgnoreCase(cellValue)
										 * ) { status = j; }
										 

					}*/

					if (employeeId == -1 || employeeName == -1 || employeeCategory == -1) {
						try {
							throw new Exception("Could not find header indexes\nemployeeId : " + employeeId
									+ " | EmployeeName : " + employeeName);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					for (int i = 1; i < rowNum; i++) {
						XSSFRow row = ws.getRow(i);
						Employee employee = new Employee();

						DataFormatter df = new DataFormatter();
						String emId = df.formatCellValue(row.getCell(employeeId));
						employee.setEmployeeId(emId);
						employee.setEmployeeName(cellToString(row.getCell(employeeName)));
						

						boolean categoryFlag = false;

						String ctgName = (cellToString(row.getCell(employeeCategory)));
						if (categoryCmpList == null) {
							categoryCmpList = empDao.getCategoryList();
						}
						Category category = empController.compareCategory(categoryCmpList, ctgName);

						for (Category catgry : categoryCmpList) {
							categoryFlag = false;
							if (catgry.getCategoryName().equalsIgnoreCase(ctgName)) {
								categoryFlag = true;
								category.setCategoryName(catgry.getCategoryName());
								break;
							}
						}

						if (categoryFlag == false) {
							category = new Category();
							category.setCategoryName(ctgName);
							
							category.setStatus(true);
							categoryDao.saveOrUpdate(category);
							categoryCmpList = categoryDao.getCategory();
						}

						employee.setEmployeeCategory(category);

					}

					if (empList.size() > 0) {
						empDao.insert(empList);

					}
					fileStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	
	
	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}

		return result.toString();
	}

	public HSSFWorkbook getWeeklyHoursWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo");
			rowhead.createCell(1).setCellValue("Date From");
			rowhead.createCell(2).setCellValue("Date To");
			rowhead.createCell(3).setCellValue("Emp Name");
			rowhead.createCell(4).setCellValue("Emp Id");
			rowhead.createCell(5).setCellValue("Mon");
			rowhead.createCell(6).setCellValue("Tue");
			rowhead.createCell(7).setCellValue("wed");
			rowhead.createCell(8).setCellValue("Thu");
			rowhead.createCell(9).setCellValue("Fri");
			rowhead.createCell(10).setCellValue("Sat");
			rowhead.createCell(11).setCellValue("Sun");
			rowhead.createCell(12).setCellValue("Total In Hours");
			rowhead.createCell(13).setCellValue("Total Hours");

			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getFromDate());
				row.createCell(2).setCellValue(wkEmpBean.getToDate());
				row.createCell(3).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(4).setCellValue(wkEmpBean.getEmpID());
				row.createCell(5).setCellValue(wkEmpBean.getMonHours());
				row.createCell(6).setCellValue(wkEmpBean.getTueHours());
				row.createCell(7).setCellValue(wkEmpBean.getWedHours());
				row.createCell(8).setCellValue(wkEmpBean.getThuHours());
				row.createCell(9).setCellValue(wkEmpBean.getFriHours());
				row.createCell(10).setCellValue(wkEmpBean.getSatHours());
				row.createCell(11).setCellValue(wkEmpBean.getSunHours());
				row.createCell(12).setCellValue(wkEmpBean.getTotalInHours());
				row.createCell(13).setCellValue(wkEmpBean.getTotalHours());
				index++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		}

		catch (Exception e) {

		}
		return workbook;

	}

	public HSSFWorkbook getMonthlyHoursWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo.");
			rowhead.createCell(1).setCellValue("Date From");
			rowhead.createCell(2).setCellValue("Date To");
			rowhead.createCell(3).setCellValue("Emp Name");
			rowhead.createCell(4).setCellValue("Emp Id");
			rowhead.createCell(5).setCellValue("Total In Hours");
			rowhead.createCell(6).setCellValue("Total Hours");

			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getFromDate());
				row.createCell(2).setCellValue(wkEmpBean.getToDate());
				row.createCell(3).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(4).setCellValue(wkEmpBean.getEmpID());
				row.createCell(5).setCellValue(wkEmpBean.getTotalInHours());
				row.createCell(6).setCellValue(wkEmpBean.getTotalHours());
				index++;
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
		}
		return workbook;
	}

	public HSSFWorkbook getHssfSwipeWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo.");
			rowhead.createCell(1).setCellValue("Emp Id");
			rowhead.createCell(2).setCellValue("Emp Name");
			rowhead.createCell(3).setCellValue("Shift start Time");
			rowhead.createCell(4).setCellValue("Shift End Time");
			rowhead.createCell(5).setCellValue("SwipeTime");
			rowhead.createCell(6).setCellValue("IN_OUT");
			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getEmpID());
				row.createCell(2).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(3).setCellValue(wkEmpBean.getShiftStartTime());
				row.createCell(4).setCellValue(wkEmpBean.getShiftEndTime());
				row.createCell(5).setCellValue(wkEmpBean.getSwipeTime());
				row.createCell(6).setCellValue(wkEmpBean.getSwipeInOut());
				index++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		}

		catch (Exception e) {

		}
		return workbook;
	}

	@RequestMapping(value = "searchCategory", method = RequestMethod.GET)
	public String searchCategory(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> list = empDao.getCategoryList();
			model.put("ctgList", list);
			return "categoryList";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "activeEmployee", method = RequestMethod.GET)
	public ModelAndView employeeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getActiveEmployeeList();
					List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("employeeList", employeeList);

			modelObj = new ModelAndView("activeEmployee", model);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	@RequestMapping(value = "employeeList", method = RequestMethod.GET)
	public ModelAndView empListForSales(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getActiveEmployeeList();
					List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("employeeList", employeeList);

			modelObj = new ModelAndView("employeeList", model);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	
	@RequestMapping(value = "inActiveEmp", method = RequestMethod.GET)
	public ModelAndView empList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> empList = empDao.getInActiveEmpList();
			List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("empList", empList);
			modelObj = new ModelAndView("inActiveEmp", model);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/updateEmployee/{empId}", method = RequestMethod.GET)
	public String updateEmployee(@PathVariable String empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				empDao.updateStatus(empId, true);
			}
			return "redirect:/Employee/inActiveEmp";
		} else
			return "redirect:/login";
	}

	
	@RequestMapping(value = "/salesSearch", method = RequestMethod.GET)
	public ModelAndView salesSearch(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			EmployeeRequest empReq = new EmployeeRequest();
			model.put("EmployeeRequest", empReq);
			modelObj = new ModelAndView("salesSearch", "skillSetList", skillSetList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	
	private List<Employee> empActiveList;
	private List<Resourceallocations> resCmpList;
	private List<EmployeeSkillSet> empSkillSet;
	
	
	@RequestMapping(value = "/empUtilizationReport", method = RequestMethod.GET)
	public String empUtilizationReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			EmployeeRequest empRequest = new EmployeeRequest();
			model.put("EmployeeRequest", empRequest);
			model.put("skillSetList", skillSetList);
			return "empUtilizationReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}
	
	
	@RequestMapping(value = "/getMonthlyUtilizationDetails", method = RequestMethod.POST)
	 public String getManagerTimeSheetMonthlyDetails(ModelMap model,EmployeeRequest empRequest,BindingResult result, HttpServletRequest req) throws ParseException {

	  //int managerId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
	  List<TimeSheetReportBean> reportList = null;
	  List<EmployeeSkillSet> empSkillSet = categoryDao.getEmpSkillSet();
	  
	  DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
	  String startDate = req.getParameter("month");
	  String date[] = startDate.split("/");
	  int month = Integer.parseInt(date[0]);
	  int year = Integer.parseInt(date[1]);
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	  
	  List<SkillSet> skillSetList = categoryDao.getSkillSet();
	  String skillSet[] = req.getParameterValues("skills");
	  String skills="0";
	  for (String skill : skillSet) {
	   skills=skills+skill+",";
	  }
	  String fetchSkills=skills.substring(1, skills.length()-1);
	  System.out.println("fetchSkills" +  fetchSkills);
	  List<TimeSheetReportBean> timeSheetDtls =
	    resourceDao.getempMonthlyTimeSheet(fetchSkills,empRequest.getEmployeeExperience(),month, year);
	  
	  
	  int empId = 0;
	  boolean empFlag = true;
	  Date fromDate = null;
	  Date toDate = null;

	  int totalNoEfts = 0, totalActEfts = 0;

	  int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	  int totalWorkingDays = 0, totalEfforts = 0;
	  int day1 = actualDate.getDay();
	  calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
	  int day2 = calendar.getTime().getDay();
	  if (day2 == 6 || day2 == 0) {
	   day2 = 5;
	  }

	  if (day1 == 6 || day1 == 0) {
	   day1 = 1;
	  }

	  int estimatedEfforts = 0, actualEstEfrts = 0;
	  int utilizedEfforts = 0, actualUtiEfts = 0;
	  int estUtil = 0, actulUtil = 0;
	  int x = 0, x1 = 0, x2 = 0;
	  int y = 0, y1 = 0, y2 = 0;

	  int cmp = 0;
	  int i = 0;
	  TimeSheetReportBean sheet = null;
	  List<TimeSheetReportBean> monthlyList = new ArrayList<>();
	  for (TimeSheetReportBean timesheet : timeSheetDtls) {
	   i++;
	   empId = timesheet.getEmp().getEmpId();
	   fromDate = timesheet.getFromDate();
	   toDate = timesheet.getToDate();
	   // actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
	   actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();

	   if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmp().getEmpId()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    empFlag = true;
	   }
	   if (i == timeSheetDtls.size()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    empFlag = true;
	   }
	   //if (empFlag) {
	    if (i == 1) {
	     x = timeSheetDtls.get(0).getNumberEfforts();
	     // x1 = timeSheetDtls.get(0).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(0).getTimesheetEfforts();

	    } else {
	     x = timeSheetDtls.get(i - 1).getNumberEfforts();
	     // x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();

	    }
	    sheet = new TimeSheetReportBean();
	    int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
	    if (tDay1 == 0) {
	     tDay1 = 1;
	    }
	    if (tDay2 == 6) {
	     tDay2 = 5;
	    }

	    totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
	    totalEfforts = totalWorkingDays * 8;
	    // estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
	    // 8)) * (5 - day2);
	    utilizedEfforts = actualUtiEfts;// - (x2 / (x / 8))  (day1 - 1) - (y2 / (y / 8))  (5 - day2);
	    // estUtil = (estimatedEfforts * 100) / totalEfforts;
	    actulUtil = (utilizedEfforts * 100) / totalEfforts;
	    sheet.setEmp(timesheet.getEmp());
	    sheet.setFromDate(actualDate);
	    sheet.setSkillSet(timesheet.getSkillSet());
	    sheet.setReporting(timesheet.getReporting());
	    sheet.setMobileNumber(timesheet.getMobileNumber());
	    sheet.setEmailId(timesheet.getEmailId());
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
	    sheet.setFromDateStr(formatter.format(actualDate));
	    sheet.setNumberEfforts(totalEfforts);
	    // sheet.setEstimatedEfforts(estimatedEfforts);
	    sheet.setTimesheetEfforts(utilizedEfforts);
	    sheet.setActualUtilization(actulUtil);
	    monthlyList.add(sheet);
	    
	    actualEstEfrts = 0;
	    actualUtiEfts = 0;
	    empFlag = false;
	   }
	  //}
	  
	  for (TimeSheetReportBean time : monthlyList) {
	   System.out.println("EmpId== "+time.getEmp().getEmployeeId());
	   System.out.println("ActUt== "+time.getActualUtilization());
	  }
	  EmployeeRequest empReq = new EmployeeRequest();
	  model.put("monthlySheet", monthlyList);
	  model.put("startDate", actualDate);
	  model.put("EmployeeRequest", empReq);
	  model.put("skillSetList", skillSetList);
	  model.put("fetchSkills", fetchSkills);
	  model.put("month", month);
	  model.put("year", year);
	  if (empRequest.getEmployeeExperience() ==null || empRequest.getEmployeeExperience() == 0.0)
	   model.put("empExpr", 0);
	  else
	   model.put("empExpr", empRequest.getEmployeeExperience());
	  return "getMonthlyUtilizationDetails";
	 }
	
	@RequestMapping(value = "/exportGetMonthlyUtilizationDetails/{fetchSkills}/{empExpr}/{month}/{year}", method = RequestMethod.GET)
	 public String exportGetMonthlyUtilizationDetails(ModelMap model,EmployeeRequest empRequest,BindingResult result,@PathVariable int month,@PathVariable int year,
	   HttpServletRequest req,HttpServletResponse response,@PathVariable String fetchSkills,@PathVariable Double empExpr) throws ParseException {
	  List<TimeSheetReportBean> reportList = null;

	  
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	  reportList = resourceDao.getempMonthlyTimeSheet(fetchSkills, empExpr, month, year);
	  
	  
	  List<SkillSet> skillSetList = categoryDao.getSkillSet();
	 
	  List<TimeSheetReportBean> timeSheetDtls =
	    resourceDao.getempMonthlyTimeSheet(fetchSkills,empRequest.getEmployeeExperience(),month, year);
	  
	  
	  int empId = 0;
	  boolean empFlag = true;
	  Date fromDate = null;
	  Date toDate = null;

	  int totalNoEfts = 0, totalActEfts = 0;

	  int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	  int totalWorkingDays = 0, totalEfforts = 0;
	  int day1 = actualDate.getDay();
	  calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
	  int day2 = calendar.getTime().getDay();
	  if (day2 == 6 || day2 == 0) {
	   day2 = 5;
	  }

	  if (day1 == 6 || day1 == 0) {
	   day1 = 1;
	  }

	  int estimatedEfforts = 0, actualEstEfrts = 0;
	  int utilizedEfforts = 0, actualUtiEfts = 0;
	  int estUtil = 0, actulUtil = 0;
	  int x = 0, x1 = 0, x2 = 0;
	  int y = 0, y1 = 0, y2 = 0;

	  int cmp = 0;
	  int i = 0;
	  TimeSheetReportBean sheet = null;
	  List<TimeSheetReportBean> monthlyList = new ArrayList<>();
	  for (TimeSheetReportBean timesheet : timeSheetDtls) {
	   i++;
	   empId = timesheet.getEmp().getEmpId();
	   fromDate = timesheet.getFromDate();
	   toDate = timesheet.getToDate();
	   // actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
	   actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();

	   if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmp().getEmpId()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    empFlag = true;
	   }
	   if (i == timeSheetDtls.size()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    empFlag = true;
	   }
	   //if (empFlag) {
	    if (i == 1) {
	     x = timeSheetDtls.get(0).getNumberEfforts();
	     // x1 = timeSheetDtls.get(0).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(0).getTimesheetEfforts();

	    } else {
	     x = timeSheetDtls.get(i - 1).getNumberEfforts();
	     // x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();

	    }
	    sheet = new TimeSheetReportBean();
	    int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
	    if (tDay1 == 0) {
	     tDay1 = 1;
	    }
	    if (tDay2 == 6) {
	     tDay2 = 5;
	    }

	    totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
	    totalEfforts = totalWorkingDays * 8;
	    // estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
	    // 8)) * (5 - day2);
	    utilizedEfforts = actualUtiEfts;// - (x2 / (x / 8))  (day1 - 1) - (y2 / (y / 8))  (5 - day2);
	    // estUtil = (estimatedEfforts * 100) / totalEfforts;
	    actulUtil = (utilizedEfforts * 100) / totalEfforts;
	    if(totalEfforts>0){
	    sheet.setEmp(timesheet.getEmp());
	    sheet.setFromDate(actualDate);
	    sheet.setSkillSet(timesheet.getSkillSet());
	    sheet.setReporting(timesheet.getReporting());
	    sheet.setMobileNumber(timesheet.getMobileNumber());
	    sheet.setEmailId(timesheet.getEmailId());
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
	    sheet.setFromDateStr(formatter.format(actualDate));
	    
	    sheet.setNumberEfforts(totalEfforts);
	    // sheet.setEstimatedEfforts(estimatedEfforts);
	    sheet.setTimesheetEfforts(utilizedEfforts);
	    // sheet.setEstimatedUtilization(estUtil);
	    //System.out.println("actulUtil=="+actulUtil);
	    sheet.setActualUtilization(actulUtil);
	    monthlyList.add(sheet);
	    }
	    
	    
	    actualEstEfrts = 0;
	    actualUtiEfts = 0;
	    empFlag = false;
	   }
	  //}
	  
	  for (TimeSheetReportBean time : monthlyList) {
	   System.out.println("EmpId== "+time.getEmp().getEmployeeId());
	   System.out.println("ActUt== "+time.getActualUtilization());
	  }
	  try {
	  doExportGetMonthlyUtilizationDetails(monthlyList, response);
	 } catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	  EmployeeRequest empReq = new EmployeeRequest();
	  model.put("monthlySheet", monthlyList);
	  model.put("startDate", actualDate);
	  model.put("EmployeeRequest", empReq);
	  model.put("skillSetList", skillSetList);
	  model.put("reportList", reportList);
	  return "getMonthlyUtilizationDetails";
	 }
	public void doExportGetMonthlyUtilizationDetails(List<TimeSheetReportBean> timesheet, HttpServletResponse response)
	   throws Exception {

	 

	  if (timesheet != null && !timesheet.isEmpty()) {
	   HSSFWorkbook workBook = new HSSFWorkbook();
	   // HSSFSheet sheet = workBook.createSheet();
	   HSSFSheet sheet = workBook.createSheet("Technical Team");
	   TimeSheetReportBean tsheet = new TimeSheetReportBean();

	   for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
	    sheet.autoSizeColumn((short) (columnPosition));
	   }

	   sheet.setColumnWidth(0, 10000);
	   sheet.setColumnWidth(1, 10000);
	   sheet.setColumnWidth(2, 10000);
	   sheet.setHorizontallyCenter(true);
	   sheet.setVerticallyCenter(true);
	   sheet.autoSizeColumn(0);
	   sheet.autoSizeColumn(1);
	   sheet.autoSizeColumn(2);
	   PrintSetup ps = sheet.getPrintSetup();

	   sheet.setAutobreaks(true);

	   ps.setFitHeight((short) 1);
	   ps.setFitWidth((short) 1);

	   // Timesheet timeSheetDtlsSubmitted1 = null;
	   sheet.setHorizontallyCenter(true);
	   sheet.setFitToPage(true);
	   HSSFFont font = workBook.createFont();
	   font.setFontHeightInPoints((short) 15);
	   font.setFontName("Calibri");
	   font.setColor(IndexedColors.BLACK.getIndex());
	   font.setBold(true);

	   HSSFCellStyle style = workBook.createCellStyle();
	   style.setAlignment(CellStyle.ALIGN_CENTER);
	   style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

	   style.setWrapText(true);
	   style.setFont(font);

	   HSSFRow row0 = sheet.createRow(0);
	   HSSFCell ch0 = row0.createCell(0);
	   style.setFillForegroundColor(IndexedColors.RED.getIndex());
	   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	   sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
	   ch0.setCellStyle(style);

	   HSSFRow row1 = sheet.createRow(1);
	   HSSFCell ch1 = row1.createCell(0);
	   HSSFCellStyle style1 = workBook.createCellStyle();
	   style1.setAlignment(CellStyle.ALIGN_CENTER);
	   style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	   font.setFontHeightInPoints((short) 15);
	   style1.setFont(font);
	   ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
	   ch1.setCellStyle(style1);
	   sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));

	   HSSFRow row2 = sheet.createRow(2);
	   HSSFCell ch2 = row2.createCell(0);
	   ch2.setCellStyle(style1);
	   ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
	   sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 11));
	   style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	   HSSFPalette palette = workBook.getCustomPalette();
	   palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
	   palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

	   style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	   HSSFCellStyle style2 = workBook.createCellStyle();
	   style2.setAlignment(CellStyle.ALIGN_CENTER);
	   style2.setWrapText(true);
	   style2.setFont(font);
	   HSSFRow headingRow = sheet.createRow(3);
	   font.setFontHeightInPoints((short) 13);
	   style2.setFont(font);
	   style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	   style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	
	   HSSFCell ch3 = headingRow.createCell((short) 0);
	   ch3.setCellValue("EmployeeId");
	   ch3.setCellStyle(style2);

	   ch3 = headingRow.createCell((short) 1);
	   ch3.setCellValue("EmployeeName");

	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 2);
	   ch3.setCellValue("Experience");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 3);
	   ch3.setCellValue("Skill Set");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 4);
	   ch3.setCellValue("Actual Efforts ");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 5);
	   ch3.setCellValue("Actual Utilization");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 6);
	   ch3.setCellValue("Reporting To");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 7);
	   ch3.setCellValue("Reporting Manager Mobile");
	   ch3.setCellStyle(style2);
	   ch3 = headingRow.createCell((short) 8);
	   ch3.setCellValue("Reporting Manager Email");
	   
	   ch3.setCellStyle(style2);
	   sheet.setFitToPage(true);

	   short rowNo = 4;

	   for (TimeSheetReportBean details : timesheet) {
	    if (details.getEmp() != null) {
	     System.out.println("Setting Values==" + details.getEmp().getEmployeeId());
	     HSSFRow row = sheet.createRow(rowNo);

	     
	     if (details.getEmp() != null && details.getTimesheetEfforts()>0) {
	      row.createCell((short) 0).setCellStyle(style);
	     row.createCell((short) 0).setCellValue(details.getEmp().getEmployeeId());
	     row.createCell((short) 1).setCellStyle(style);
	     row.createCell((short) 1).setCellValue(details.getEmp().getEmployeeName());
	     row.createCell((short) 2).setCellStyle(style);
	     row.createCell((short) 2).setCellValue(details.getEmp().getEmployeeExperience());
	     row.createCell((short) 3).setCellStyle(style);
	     if (details.getSkillSet() != null)
	      row.createCell((short) 3).setCellValue(details.getSkillSet());
	    
	     row.createCell((short) 4).setCellStyle(style);
	     row.createCell((short) 4).setCellValue(details.getTimesheetEfforts());
	     row.createCell((short) 5).setCellStyle(style);
	     row.createCell((short) 5).setCellValue(details.getActualUtilization());
	     row.createCell((short) 6).setCellStyle(style);
	     row.createCell((short) 6).setCellValue(details.getReporting());
	     row.createCell((short) 7).setCellStyle(style);
	     row.createCell((short) 7).setCellValue(details.getMobileNumber());
	     row.createCell((short) 8).setCellStyle(style);
	     row.createCell((short) 8).setCellValue(details.getEmailId());
	     }
	     rowNo++;
	    }
	   }

	   String file = "Employee Monthly Utilization Report.xls";
	   try {
	    FileOutputStream fos = new FileOutputStream(file);
	    workBook.write(fos);
	    fos.flush();
	    fos.close();
	    downloadFile(file, response);

	   } catch (FileNotFoundException e) {
	    e.printStackTrace();
	   } catch (IOException e) {
	    e.printStackTrace();
	   } catch (Exception e) {
	    e.printStackTrace();
	   } finally {
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	   }
	  }
	 }
	
	
	@RequestMapping(value = "/getSalesSearch", method = RequestMethod.POST)
	public String benchEmployeesReport(@ModelAttribute EmployeeRequest empRequest, BindingResult result, ModelMap model,
			HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			//List<EmployeeSkillSet> empSkillSet = categoryDao.getEmpSkillSet();
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			String skillSet[] = req.getParameterValues("skills");
			String skills = "0";
			for (String skill : skillSet) {
				skills = skills + skill + ",";
			}
			String fetchSkills = skills.substring(1, skills.length() - 1);
			List<SalesReportBean> resourceList = resourceDao.getSalesSearch(fetchSkills,
					empRequest.getEmployeeExperience(), empRequest.getEmployeeName());

			try {
				empActiveList = empDao.getActiveEmployeeList();
				resCmpList = resourceDao.getResources();
				Employee employee = null;
				Resourceallocations resource = null;
				SalesReportBean bean = null;

				for (Resourceallocations res : resCmpList) {
					for (Employee emp : empActiveList) {
						// if ( emp.getEmpId() == res.getEmployeeId() ) {
						employee = new Employee();
						bean = new SalesReportBean();
						resource = new Resourceallocations();
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());

						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
						employee.setReportingTo(emp.getReportingTo());
						resource.setProjectFromStr(null);

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
						if (employee.getJoinDateStr() != null) {
							employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
						}

					}
					resourceList.add(bean);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmployeeRequest empReq = new EmployeeRequest();
			model.put("resourceList", resourceList);
			model.put("EmployeeRequest", empReq);
			model.put("fetchSkills", fetchSkills);
			
			if (empRequest.getEmployeeExperience() ==null || empRequest.getEmployeeExperience() == 0.0)
				model.put("empExp", 0);
			else
				model.put("empExp", empRequest.getEmployeeExperience());
			
			if (empRequest.getEmployeeName()=="" || empRequest.getEmployeeName() == null)
				model.put("empName", "A");
			else
				model.put("empName", empRequest.getEmployeeName());
			
			model.put("skillSetList", skillSetList);

			return "salesSearch";
		} else
			return "redirect:/login";

	}
	private void downloadFile(final String fileName, HttpServletResponse response) {
		final File f = new File(fileName);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "max-age=0");
		FileInputStream fin = null;
		ServletOutputStream os = null;
		try {
			fin = new FileInputStream(f);
			final int size = 1024;
			response.setContentLength(fin.available());
			final byte[] buffer = new byte[size];
			os = response.getOutputStream();
			int length = 0;
			while ((length = fin.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fin != null)
					fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/exportGetSalesSearch/{fetchSkills}/{empExp}/{empName}", method = RequestMethod.GET)
	public String exportGetSalesSearch(@PathVariable String fetchSkills, @PathVariable Double empExp,
			@PathVariable String empName, @ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model, HttpServletResponse response) {
		List<SalesReportBean> resourceList = null;

		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			resourceList = resourceDao.getSalesSearch(fetchSkills, empExp, empName);
			try {
				empActiveList = empDao.getActiveEmployeeList();
				resCmpList = resourceDao.getResources();
				Employee employee = null;
				Resourceallocations resource = null;
				SalesReportBean bean = null;

				for (Resourceallocations res : resCmpList) {
					for (Employee emp : empActiveList) {
						// if ( emp.getEmpId() == res.getEmployeeId() ) {
						employee = new Employee();
						bean = new SalesReportBean();
						resource = new Resourceallocations();
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());

						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
						employee.setReportingTo(emp.getReportingTo());
						resource.setProjectFromStr(null);

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
						if (employee.getJoinDateStr() != null) {
							employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
						}

					}
					// resourceList.add(bean);
				}
				
				doExportGetSalesSearch(resourceList, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmployeeRequest empReq = new EmployeeRequest();
			model.put("resourceList", resourceList);
			model.put("EmployeeRequest", empReq);
			model.put("skillSetList", skillSetList);
			return "salesSearch";
		} else
			return "redirect:/login";
	}

	public void doExportGetSalesSearch(List<SalesReportBean> resourceList, HttpServletResponse response)
			throws Exception {

		

		if (resourceList != null && !resourceList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");
			SalesReportBean sales = new SalesReportBean();

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			// Timesheet timeSheetDtlsSubmitted1 = null;
			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			HSSFFont font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 11));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EmployeeId");
			ch3.setCellStyle(style2);

			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EmployeeName");

			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Designation");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Assignments");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Mobile No");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Location");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Experience");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 7);
			ch3.setCellValue("SkillSet");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 8);
			ch3.setCellValue("Available From");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 9);
			ch3.setCellValue("Reporting To	");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 10);
			ch3.setCellValue("Reporting Manager Mobile	");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 11);
			ch3.setCellValue("Reporting Manager Email");
			ch3.setCellStyle(style2);
			sheet.setFitToPage(true);

			short rowNo = 4;

			for (SalesReportBean details : resourceList) {
				if (details.getEmp() != null) {
					HSSFRow row = sheet.createRow(rowNo);

					row.createCell((short) 0).setCellStyle(style);
					row.createCell((short) 0).setCellValue(details.getEmp().getEmployeeId());

					row.createCell((short) 1).setCellStyle(style);

					row.createCell((short) 1).setCellValue(details.getEmp().getEmployeeName());
					row.createCell((short) 2).setCellStyle(style);
					row.createCell((short) 2).setCellValue(details.getEmp().getEmployeeDesg());
					row.createCell((short) 3).setCellStyle(style);
					if (details.getPrimaryProjects() != null) {
						if (details.getPrimaryProjects() == "_"
								|| details.getPrimaryProjects().equalsIgnoreCase("Bench")) {
							row.createCell((short) 3).setCellValue("Bench");
						} else {
							row.createCell((short) 3).setCellValue(details.getPrimaryProjects());
						}
					}
					row.createCell((short) 4).setCellStyle(style);
					if (details.getEmp() != null) {
						row.createCell((short) 4).setCellValue(details.getEmp().getEmployeeMobilenumber());
						row.createCell((short) 5).setCellStyle(style);
						row.createCell((short) 5).setCellValue(details.getEmp().getEmployeeCity().getName());
						row.createCell((short) 6).setCellStyle(style);
						row.createCell((short) 6).setCellValue(details.getEmp().getEmployeeExperience());
					}
					row.createCell((short) 7).setCellStyle(style);
					if (details.getSkillSet() != null)
						row.createCell((short) 7).setCellValue(details.getSkillSet());
					row.createCell((short) 8).setCellStyle(style);
					if (details.getBenchDate() != null)
						row.createCell((short) 8).setCellValue(details.getBenchDate());
					row.createCell((short) 9).setCellStyle(style);
					row.createCell((short) 9).setCellValue(details.getReporting());
					row.createCell((short) 10).setCellStyle(style);
					row.createCell((short) 10).setCellValue(details.getMobileNumber());
					row.createCell((short) 11).setCellStyle(style);
					row.createCell((short) 11).setCellValue(details.getEmailId());
					rowNo++;
				}
			}

			String file = "EmployeeSkillSetReportList.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}


	


	
	@RequestMapping(value = "pmoEmployeeListView", method = RequestMethod.GET)
	public ModelAndView pmoEmployeeListView(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getActiveEmployeeList();
					List<EmployeeSkillSet> skillsList = categoryDao.getEmpSkillSet();
			model.put("skillsList", skillsList);
			model.put("employeeList", employeeList);

			modelObj = new ModelAndView("activeEmployee", model);
			//modelObj = new ModelAndView("activeEmployee", "employeeList", employeeList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
}
