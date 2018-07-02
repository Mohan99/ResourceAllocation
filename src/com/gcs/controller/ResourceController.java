package com.gcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("/Resource")
public class ResourceController extends BaseController {
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;

	@Autowired
	// @Qualifier("usersDao")
	private UsersDao usersDao;

	@Autowired
	private SessionData sessionobj;

	List<Employee> empCmpList;
	List<Projects> projectsCmpList;
	List<Category> categoryCmpList;
	List<Cities> cityCmpList;
	List<State> stateCmpList;
	List<Countries> countryCmpList;
	List<Resourceallocations> resourceCmpList;
	List<Employee> managerCmpList;

	@RequestMapping(value = "/searchResource", method = RequestMethod.GET)
	public ModelAndView employeeList(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResourcesList();
			// System.out.println("searchResource......." + resourceList);
			modelObj = new ModelAndView("searchResource", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/engagedResources", method = RequestMethod.GET)
	public ModelAndView engagedResources(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.engagedResources();
			// System.out.println("searchResource......." + resourceList);
			modelObj = new ModelAndView("engagedResources", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/searchResourceUnderManager", method = RequestMethod.GET)
	public ModelAndView searchResourceUnderManager(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResourcesListUnderManager(sessionobj.getEmpObj().getEmpId());
			List<Employee> empList = empDao
					.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());

			Resourceallocations res = null;
			for (Employee employee : empList) {
				boolean found = true;
				for (Resourceallocations resource : resourceList) {
					if (employee.getEmpId() == resource.getEmployeeId())
						found = false;
				}
				if (found) {
					res = new Resourceallocations();
					res.setEmployeeId(employee.getEmpId());
					res.setEmpId(employee.getEmployeeId());
					res.setEmployeeName(employee.getEmployeeName());
					resourceList.add(res);
				}
			}
			
			modelObj = new ModelAndView("searchResource", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/allocateResource/{empId}", method = RequestMethod.GET)
	public ModelAndView allocateResource(@PathVariable String empId, String id, Date projectTo, ModelMap model) {
		ModelAndView modelObj = null;
		Resourceallocations resourceObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			sessionobj.setEmpId(empId);
			ResourceRequest resourceReq = new ResourceRequest();
			List<Employee> empList = empDao.getActiveEmployeeList();
			List<Projects> projectList = projectDao.getActiveProjects();
			List<Resourceallocations> resourceList = resourceDao.getResourcesListByEmpId(empId);
			if (resourceList != null && !resourceList.isEmpty()) {
				resourceObj = resourceList.get(0);
				ListIterator<Resourceallocations> itr = resourceList.listIterator();
				Resourceallocations resourceallocations = null;
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				while (itr.hasNext()) {
					resourceallocations = new Resourceallocations();
					resourceallocations = itr.next();
					if (resourceallocations.getProjectFrom() != null)
						resourceallocations.setProjectFromStr(formatter.format(resourceallocations.getProjectFrom()));

				}

				ListIterator<Resourceallocations> itr1 = resourceList.listIterator();
				Resourceallocations res = null;
				while (itr1.hasNext()) {
					res = new Resourceallocations();
					res = itr1.next();
					if (res.getProjectTo() != null)
						res.setProjectToStr(formatter.format(res.getProjectTo()));

				}
				model.put("empName", resourceObj.getEmployeeName());
				model.put("empId", resourceObj.getEmpId());
				model.put("projectId", resourceObj.getProjectId());
				model.put("projectTo", resourceObj.getProjectTo());
			} else {
				Employee emp = empDao.getEmployeeData(empId);
				model.put("empName", emp.getEmployeeName());
				model.put("empId", emp.getEmployeeId());
			}

			Map<String, Object> mapModel = new HashMap<String, Object>();
			modelObj = new ModelAndView("allocateResource", mapModel);
			model.put("resourceRequest", resourceReq);
			model.put("empList", empList);
			model.put("projectList", projectList);
			model.put("resourceList", resourceList);

		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	public String getDateFormat(Date unformattedDate) {
		Date dt = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(unformattedDate);

		System.out.println(strDate);

		return strDate;
	}

	@RequestMapping(value = "editResource/{projectId}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable String projectId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Resourceallocations> resourceList = resourceDao.getResourcesListByEmpId(sessionobj.getEmpId());
			List<Projects> projectList = projectDao.getProjects();
			Resourceallocations resourceObj = resourceDao.getResourceByProjectId(sessionobj.getEmpId(), projectId);

			System.out.println("edit Resource.........");

			if (resourceObj != null) {
				if (resourceObj.getProjectFrom() != null) {
					resourceObj.setProjectFromStr(getDateFormat(resourceObj.getProjectFrom()));
					System.out.println("resourceObj.getProjectFrom()==" + resourceObj.getProjectFrom());

				}
				if (resourceObj.getProjectTo() != null) {
					resourceObj.setProjectToStr(getDateFormat(resourceObj.getProjectTo()));
					// System.out.println("resourceObj.getProjectTo()==" +
					// resourceObj.getProjectTo());
				}

				ResourceRequest resourceReq = new ResourceRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();

				mapModel.put("projectList", projectList);
				mapModel.put("resourceRequest", resourceReq);
				mapModel.put("resourceList", resourceList);
				mapModel.put("resourceObj", resourceObj);
				// mapModel.put("resource", resource);
				modelObj = new ModelAndView("allocateResource", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/createOrUpdateResource", method = RequestMethod.POST)
	public String createOrUpdateResource(@ModelAttribute ResourceRequest resourceRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Resourceallocations resourceObj = new Resourceallocations();
			// List<Projects> projList=projectDao.getActiveProjects();
			resourceObj.setResId(resourceRequest.getResId());
			if (sessionobj.getEmpId() != null)
				resourceObj.setEmployeeId(Integer.parseInt(sessionobj.getEmpId()));
			else
				resourceObj.setEmployeeId(resourceRequest.getEmployeeId());
			resourceObj.setProjectId(resourceRequest.getProjectId());
			resourceObj.setAllocation(resourceRequest.getAllocation());
			resourceObj.setBillable(resourceRequest.getBillable());
			System.out.println("resourceRequest.getAllocation()" + resourceRequest.getAllocation());
			System.out.println("resObj ......allocation " + resourceObj.getAllocation());
			Projects project = new Projects();
			// project=projList.get(0);

			if (resourceRequest.getProjectFrom() != null) {
				resourceObj.setProjectFrom(resourceRequest.getProjectFrom());
			}
			if (resourceRequest.getProjectTo() != null) {
				resourceObj.setProjectTo(resourceRequest.getProjectTo());
				resourceObj.setProjectCompleted("Y");
			}

			else {
				// resourceObj.setProjectTo(project.getEndDate());
				resourceObj.setProjectCompleted("N");
			}

			if (resourceObj.getProjectTo() != null) {

				resourceDao.saveOrUpdate(resourceObj);
			}
			if (resourceObj.getAllocation() != null) {
				resourceDao.saveOrUpdate(resourceObj);
			}

			if (resourceObj.getBillable() != null) {
				resourceDao.saveOrUpdate(resourceObj);
			}

			else {
				int count = resourceDao.setResUpdate(resourceObj.getEmployeeId(), resourceObj.getProjectId(),
						resourceObj.getProjectFrom());
				if (count == 1)
					resourceDao.saveOrUpdate(resourceObj);
			}
			List<Resourceallocations> checkList = resourceDao.checkResWithProjId(resourceObj.getEmployeeId(),
					resourceObj.getProjectId());
			if (checkList.size() == 0) {
				resourceDao.saveOrUpdate(resourceObj);
			}
			return "redirect:/Resource/allocateResource/" + sessionobj.getEmpId();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createNewResource", method = RequestMethod.POST)
	public String createNewResource(@ModelAttribute ResourceRequest resourceRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Resourceallocations resource = new Resourceallocations();
			resource.setResId(resourceRequest.getResId());

			resource.setEmployeeId(resourceRequest.getEmployeeId());
			resource.setProjectId(resourceRequest.getProjectId());
			resource.setAllocation(resourceRequest.getAllocation());
			resource.setBillable(resourceRequest.getBillable());
			if (resourceRequest.getProjectFrom() != null)
				resource.setProjectFrom(resourceRequest.getProjectFrom());
			if (resourceRequest.getProjectTo() != null) {
				resource.setProjectTo(resourceRequest.getProjectTo());
				resource.setProjectCompleted("Y");

			} else
				resource.setProjectCompleted("N");
			if (resource.getProjectTo() != null) {

				resourceDao.saveOrUpdate(resource);
			}

			else {
				int count = resourceDao.setResUpdate(resourceRequest.getEmployeeId(), resourceRequest.getProjectId(),
						resourceRequest.getProjectFrom());
				if (count == 1)
					resourceDao.saveOrUpdate(resource);

			}
			List<Resourceallocations> checkList = resourceDao.checkResWithProjId(resource.getEmployeeId(),
					resource.getProjectId());
			if (checkList.size() == 0) {
				resourceDao.saveOrUpdate(resource);
			}
			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createNewResourceUnderManager", method = RequestMethod.POST)
	public String createNewResourceUnderManager(@ModelAttribute ResourceRequest resourceRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Resourceallocations resource = new Resourceallocations();
			resource.setResId(resourceRequest.getResId());

			resource.setEmployeeId(resourceRequest.getEmployeeId());
			resource.setProjectId(resourceRequest.getProjectId());
			resource.setAllocation(resourceRequest.getAllocation());
			resource.setBillable(resourceRequest.getBillable());
			if (resourceRequest.getProjectFrom() != null)
				resource.setProjectFrom(resourceRequest.getProjectFrom());
			if (resourceRequest.getProjectTo() != null) {
				resource.setProjectTo(resourceRequest.getProjectTo());
				resource.setProjectCompleted("Y");
			} else
				resource.setProjectCompleted("N");
			if (resource.getProjectTo() != null) {

				resourceDao.saveOrUpdate(resource);
			}
			if (resource.getBillable() != null) {
				resourceDao.saveOrUpdate(resource);
			}

			else {
				int count = resourceDao.setResUpdate(resourceRequest.getEmployeeId(), resourceRequest.getProjectId(),
						resourceRequest.getProjectFrom());
				if (count == 1)
					resourceDao.saveOrUpdate(resource);

			}
			List<Resourceallocations> checkList = resourceDao.checkResWithProjId(resource.getEmployeeId(),
					resource.getProjectId());
			if (checkList.size() == 0) {
				resourceDao.saveOrUpdate(resource);
			}
			return "redirect:/Resource/searchResourceUnderManager";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/deleteResource/{resId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String resId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (resId != null) {
				resourceDao.delete(resId, true);
			}
			return "redirect:/Resource/allocateResource/" + sessionobj.getEmpId();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/allocateNewResource", method = RequestMethod.GET)
	public ModelAndView allocateNewResource(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ResourceRequest resourceReq = new ResourceRequest();
			List<Employee> empList = empDao.getActiveEmployeeList();
			List<Projects> projectList = projectDao.getActiveProjects();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("resourceRequest", resourceReq);
			model.put("empList", empList);
			model.put("projectList", projectList);

			modelObj = new ModelAndView("allocateNewResource", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/allocateNewResourceManager", method = RequestMethod.GET)
	public ModelAndView allocateNewResourceUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> empListUnderManager = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());

			ResourceRequest resourceReq = new ResourceRequest();

			List<Projects> projectList = projectDao.getProjects();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("resourceRequest", resourceReq);

			model.put("projectList", projectList);
			model.put("empListUnderManager", empListUnderManager);

			modelObj = new ModelAndView("allocateNewResourceUnderManager", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "resourceBulkUpload", method = RequestMethod.GET)
	public String resourceBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "resourceBulkUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "resourceAction", method = RequestMethod.GET)
	public String resourceActionBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "resourceActionUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "massAllocations", method = RequestMethod.GET)
	public String resourceMassAllocations(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projList = projectDao.getActiveProjects();
			model.put("projList", projList);
			return "massAllocation";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "allocateEmployees/{projId}", method = RequestMethod.GET)
	public String allocateMassEmployees(@PathVariable int projId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int empId = sessionobj.getEmpObj().getEmpId();
			int id = sessionobj.getEmpObj().getEmpId();

			List<Employee> employeeList = empDao.getEmployeesUnderManagers(id);
			List<Resourceallocations> resList = resourceDao.getResources();
			List<Projects> projectList = projectDao.getProjects();
			List<Employee> empList = new ArrayList<>();
			List<EmployeeSkillSet> empSkillList = categoryDao.getEmpSkillSet();
			for (Employee emp : employeeList) {
				for (Resourceallocations res : resList) {
					if (res.getEmployeeId() == emp.getEmpId() && res.getProjectId() == projId
							&& res.getProjectCompleted().equalsIgnoreCase("N")) {
						emp.setAssigned(true);
						emp.setBillable(res.getBillable());
						break;
					} else {
						emp.setAssigned(false);
						emp.setBillable("N");
					}

				}
				empList.add(emp);
			}
			model.put("empList", empList);
			// model.put("resList", resList);
			model.put("empSkillList", empSkillList);
			model.put("projectList", projectList);
			model.put("projId", projId);

			return "allocateMassEmployees";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/resourceListUnderManager", method = RequestMethod.GET)
	public ModelAndView resourceListUnderManager(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResListUnderManager(sessionobj.getEmpObj().getEmpId());
			// System.out.println("searchResource......." + resourceList);
			modelObj = new ModelAndView("resourceListUnderManager", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/benchListUnderManager", method = RequestMethod.GET)
	public ModelAndView benchListUnderManager(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getBenchListUnderManager(sessionobj.getEmpObj().getEmpId());
			// System.out.println("searchResource......." + resourceList);
			modelObj = new ModelAndView("resourceListUnderManager", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}


	public boolean compareResources(int empId, int projId, List<Resourceallocations> resList) {
		boolean flag = true;
		for (Resourceallocations res : resList) {
			if (res.getEmployeeId() == empId && res.getProjectId() == projId) {
				System.out.println("comparingg===empId" + empId);
				System.out.println("comparingg===projId" + projId);
				flag = false;
				break;
			}
		}
		return flag;
	}

	public boolean compareResourcesBillable(int empId, int projId, String billable, List<Resourceallocations> resList) {
		boolean flag = true;
		for (Resourceallocations res : resList) {
			if (res.getEmployeeId() == empId && res.getProjectId() == projId && res.getBillable() == "N") {
				System.out.println("comparingg===empId" + empId);
				System.out.println("comparingg===projId" + projId);
				System.out.println("comparingg===billable" + billable);
				flag = false;
				break;
			}
		}
		return flag;
	}

	@RequestMapping(value = "/updateMultipleResources", method = RequestMethod.POST)
	public String updateBulkResources(HttpServletRequest request, ModelMap model) throws ParseException {

		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Resourceallocations> resList = resourceDao.getResources();
			Date date1 = null, date2 = null;
			int projId = Integer.parseInt(request.getParameter("projId"));
			// String billable = request.getParameter("billable");
			// System.out.println("billableEmpId ====" +billableEmpId);
			String fromDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
			if (fromDate != null && fromDate != "")
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			else
				date1 = new Date();
			if (endDate != null && endDate != "")
				date2 = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			Resourceallocations res = null;
			boolean flag = true;
			boolean flagbillable = true;
			/*
			 * String billable1[] = request.getParameterValues("billable"); for
			 * (String b : billable1) { System.out.println("billable----" + b);
			 * }
			 */

			if (request.getParameterValues("empId") != null) {
				for (String empId : request.getParameterValues("empId")) {

					System.out.println("empId non billable=" + empId);
					// System.out.println("billable----" + billable);

					int id = Integer.parseInt(empId);
					if (resList != null && !resList.isEmpty())
						flag = compareResources(id, projId, resList);
					// flagbillable = compareResourcesBillable(id, projId,
					// billable, resList);

					if (flag) {
						res = new Resourceallocations();
						res.setEmployeeId(id);
						res.setProjectId(projId);
						res.setProjectFrom(date1);
						res.setBillable("N");
						if (request.getParameterValues("billable") != null) {
							for (String billable : request.getParameterValues("billable")) {
								if (billable.equalsIgnoreCase(empId)) {
									res.setBillable("Y");
									System.out.println("empId billable=" + empId);
									break;
								}

							}
						}
						if (date2 != null) {
							res.setProjectTo(date2);
							res.setProjectCompleted("Y");
						} else
							res.setProjectCompleted("N");
						res.setAllocation("P");
						resourceDao.saveOrUpdate(res);
					}
				}
			}

			return "redirect:/Resource/massAllocations";
		} else
			return "redirect:/login";

	}

	/*
	 * @RequestMapping(value = "/updateMultipleResourcesBillable", method =
	 * RequestMethod.POST) public String
	 * updateBulkResourcesBillable(HttpServletRequest request, ModelMap model)
	 * throws ParseException {
	 * 
	 * if (sessionobj != null && sessionobj.getIsValidLogin()) {
	 * List<Resourceallocations> resList = resourceDao.getResources(); Date
	 * date1 = null, date2 = null; int projId =
	 * Integer.parseInt(request.getParameter("projId")); String fromDate =
	 * request.getParameter("startdate"); String endDate =
	 * request.getParameter("enddate"); if (fromDate != null && fromDate != "")
	 * date1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate); else date1 =
	 * new Date(); if (endDate != null && endDate != "") date2 = new
	 * SimpleDateFormat("MM/dd/yyyy").parse(endDate);
	 * 
	 * Resourceallocations res = null; boolean flag = true; if
	 * (request.getParameterValues("empId") != null) { for (String empId :
	 * request.getParameterValues("empId")) {
	 * 
	 * int id = Integer.parseInt(empId); if (resList != null &&
	 * !resList.isEmpty()) flag = compareResources(id, projId, resList);
	 * 
	 * if (flag) { res = new Resourceallocations(); res.setEmployeeId(id);
	 * res.setProjectId(projId); res.setBillable("Y");
	 * 
	 * 
	 * resourceDao.saveOrUpdate(res); } } }
	 * 
	 * return "redirect:/Resource/massAllocations"; } else return
	 * "redirect:/login";
	 * 
	 * }
	 */
	@RequestMapping(value = "/removeMultipleResources/{projId}", method = RequestMethod.POST)
	public String removeMultipleResources(@PathVariable int projId, HttpServletRequest request, ModelMap model)
			throws ParseException {

		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Date date1 = null, date2 = null;
			// int projId = Integer.parseInt(request.getParameter("projId"));
			String fromDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");

			if (fromDate != null && fromDate != "")
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			else
				date1 = new Date();

			if (endDate != null && endDate != "")
				date2 = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			else
				date2 = new Date();

			if (request.getParameterValues("empId") != null) {
				for (String empId : request.getParameterValues("empId")) {
					int id = Integer.parseInt(empId);
					resourceDao.removeMultipleResource(id, projId, date2);
				}
			}

			return "redirect:/Resource/massAllocations";
		} else
			return "redirect:/login";

	}

	/*
	 * @RequestMapping(value = "/removeMultipleResourcesBillable/{projId}",
	 * method = RequestMethod.POST) public String
	 * removeMultipleResourcesBillable(@PathVariable int projId,
	 * HttpServletRequest request, ModelMap model) throws ParseException {
	 * 
	 * if (sessionobj != null && sessionobj.getIsValidLogin()) { Date date1 =
	 * null, date2 = null; // int projId =
	 * Integer.parseInt(request.getParameter("projId")); String fromDate =
	 * request.getParameter("startdate"); String endDate =
	 * request.getParameter("enddate"); String billable =
	 * request.getParameter("billable");
	 * 
	 * if (fromDate != null && fromDate != "") date1 = new
	 * SimpleDateFormat("MM/dd/yyyy").parse(fromDate); else date1 = new Date();
	 * 
	 * if (endDate != null && endDate != "") date2 = new
	 * SimpleDateFormat("MM/dd/yyyy").parse(endDate); else date2 = new Date();
	 * 
	 * if (request.getParameterValues("empId") != null) { for (String empId :
	 * request.getParameterValues("empId")) { int id = Integer.parseInt(empId);
	 * resourceDao.removeMultipleResourceBiller(id, projId, date2, billable); }
	 * }
	 * 
	 * return "redirect:/Resource/massAllocations"; } else return
	 * "redirect:/login";
	 * 
	 * }
	 */

	public int getEmployeeId(List<Employee> emp, String empid) {
		int employeeId = 0;
		for (Employee employee : emp) {
			if (empid.contains(employee.getEmployeeId())) {
				employeeId = employee.getEmpId();
			}
		}
		return employeeId;

	}

	public int getProjectId(List<Projects> projects, String projectName) {
		int projectId = 0;
		for (Projects proj : projects) {
			if (proj.getProjectName().equalsIgnoreCase(projectName))
				projectId = proj.getProjectId();
		}
		return projectId;
	}

	public Resourceallocations getResource(List<Resourceallocations> resources, int empId, int projId) {
		Resourceallocations allocation = null;
		for (Resourceallocations res : resources) {
			if (res.getEmployeeId() == empId && res.getProjectId() == projId) {
				int resId = res.getResId();
				allocation = new Resourceallocations();
				allocation.setResId(res.getResId());
				allocation.setEmployeeId(res.getEmployeeId());
				allocation.setProjectId(res.getProjectId());
				allocation.setProjectFrom(res.getProjectFrom());
				allocation.setProjectTo(res.getProjectTo());
				allocation.setAllocation(res.getAllocation());
				allocation.setProjectCompleted(res.getProjectCompleted());
			}
		}
		return allocation;
	}

	public Category getCategory(List<Category> catgList, String categoryName) {
		Category ctg = null;
		for (Category cat : catgList) {
			if (cat.getCategoryName().equalsIgnoreCase(categoryName)) {
				ctg = new Category();
				ctg.setCategoryId(cat.getCategoryId());
				ctg.setCategoryName(cat.getCategoryName());
			}
		}
		return ctg;
	}

	public Cities getcity(List<Cities> cityList, String cityName) {
		Cities city = null;
		for (Cities ct : cityList) {
			if (ct.getName().equalsIgnoreCase(cityName)) {
				city = new Cities();
				city.setId(ct.getId());
				city.setName(ct.getName());
				city.setStateId(ct.getStateId());
				city.setCountryId(ct.getCountryId());
				break;
			}
		}
		return city;
	}

	public State getState(List<State> stateList, int cityStId) {
		State state = null;
		for (State st : stateList) {
			if (st.getId() == cityStId) {
				state = new State();
				state.setId(st.getId());
				state.setName(st.getName());
				state.setCountryId(st.getCountryId());
			}
		}
		return state;
	}

	public Countries getCountry(List<Countries> cntList, int stCntId) {
		Countries country = null;
		for (Countries cnt : cntList) {
			if (cnt.getId() == stCntId) {
				country = new Countries();
				country.setId(cnt.getId());
				country.setName(cnt.getName());
				country.setPhonecode(cnt.getPhonecode());
				country.setSortname(cnt.getSortname());
			}
		}
		return country;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				ResourceController controller = new ResourceController();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				System.out.println("Resource bulk upload....");
				String fileName = file.getOriginalFilename();

				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int id = -1, name = -1, designation = -1, serviceCategory = -1, primaryAssignment = -1,
						otherAssignments = -1, /*specialization = -1,*/ mobileNo = -1, location = -1, date = -1,
						reportingTo = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(2);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("ID".equalsIgnoreCase(cellValue)) {
						id = j;
					} else if ("Name".equalsIgnoreCase(cellValue)) {
						name = j;
					} else if ("Designation".equalsIgnoreCase(cellValue)) {
						designation = j;
					} else if ("Reporting Manager".equalsIgnoreCase(cellValue)
							|| "Reporting_Manager".equalsIgnoreCase(cellValue)) {
						reportingTo = j;
					} else if ("Service_Category".equalsIgnoreCase(cellValue)
							|| "Service Category".equalsIgnoreCase(cellValue)) {
						serviceCategory = j;
					} else if ("Primary Assignment".equalsIgnoreCase(cellValue)
							|| "Primary_Assignment".equalsIgnoreCase(cellValue)) {
						primaryAssignment = j;
					} else if ("Other Assignments".equalsIgnoreCase(cellValue)
							|| "Other_Assignments".equalsIgnoreCase(cellValue)) {
						otherAssignments = j;
					} /*else if ("Specialization".equalsIgnoreCase(cellValue)) {
						specialization = j;
					} */else if ("Mobile No".equalsIgnoreCase(cellValue) || "Mobile_No".equalsIgnoreCase(cellValue)) {
						mobileNo = j;

					} else if ("Location".equalsIgnoreCase(cellValue)) {
						location = j;
					} /*
						 * else if ("Date".equalsIgnoreCase(cellValue)) { date =
						 * j; } else if ("JoinDate".equalsIgnoreCase(cellValue)
						 * || "Join_Date".equalsIgnoreCase(cellValue)) {
						 * joinDate = j; }
						 */
				}

				if (id == -1 || name == -1 || designation == -1 || reportingTo == -1 || serviceCategory == -1 || primaryAssignment == -1
						|| otherAssignments == -1 || mobileNo == -1 || location == -1) {
					try {
						throw new Exception(
								"Could not find header indexes\nemployeeId : " + id + " | EmployeeName : " + name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				List<Employee> empList = new ArrayList<Employee>();
				List<Resourceallocations> resourceList = new ArrayList<>();
				List<Projects> projectsList = new ArrayList<>();
				List<Category> categoryList = new ArrayList<>();
				Employee employee = null;
				Resourceallocations resource = null;
				Projects project = null;
				empCmpList = empDao.getEmpList();
				categoryCmpList = empDao.getCategoryList();
				stateCmpList = empDao.getStates();
				countryCmpList = empDao.getCountryList();
				projectsCmpList = projectDao.getProjects();
				managerCmpList = usersDao.getListEmpUsers();

				boolean empFlag = false;
				boolean prjectFlag = false;
				boolean secPorjFlag = false;
				boolean categoryFlag = false;

				for (int i = 3; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					resource = new Resourceallocations();
					DataFormatter dataFormatter = new DataFormatter();
					String emplId = dataFormatter.formatCellValue(row.getCell(id));
					String categoryName = cellToString(row.getCell(serviceCategory));
					String cityName = cellToString(row.getCell(location));
					String reportingManager = cellToString(row.getCell(reportingTo));

					Category category = null;
					Cities city = null;
					State state = null;
					Countries country = null;
					Date date2 = null;

					for (Employee emp : empCmpList) {
						empFlag = false;
						if (emp.getEmployeeId().equalsIgnoreCase(emplId)) {
							empFlag = true;
							date2 = emp.getJoinDate();
							break;
						}
					}
					if (empFlag == false) {
						employee = new Employee();
						employee.setEmployeeId(emplId);
						employee.setEmployeeName(cellToString(row.getCell(name)));
						employee.setEmployeeDesg(cellToString(row.getCell(designation)));

						Employee userEmp = null;
						for (Employee manager : managerCmpList) {
							if (manager.getEmployeeName().equalsIgnoreCase(reportingManager)) {
								userEmp = manager;
							}
						}

						employee.setReportingTo(userEmp);
						//employee.setEmployeeSpecialization(cellToString(row.getCell(specialization)));

						/*
						 * category = controller.getCategory(categoryCmpList,
						 * categoryName);
						 * employee.setEmployeeCategory(category);
						 */

						category = controller.getCategory(categoryCmpList, categoryName);
						for (Category catgry : categoryCmpList) {
							categoryFlag = false;
							if (catgry.getCategoryName().equalsIgnoreCase(categoryName)) {
								categoryFlag = true;
								category.setCategoryName(catgry.getCategoryName());
								break;
							}
						}

						if (categoryFlag == false) {
							category = new Category();
							category.setCategoryName(categoryName);
							category.setStatus(true);
							category.setIsCategory("Y");
							categoryDao.saveOrUpdate(category);
							categoryCmpList = categoryDao.getCategory();
						}

						employee.setEmployeeCategory(category);

						/*
						 * String jnDate =
						 * dataFormatter.formatCellValue(row.getCell(joinDate));
						 * try { date2 = new
						 * SimpleDateFormat("dd-MM-yyyy").parse(jnDate);
						 * employee.setJoinDate(date2); } catch (ParseException
						 * e) { e.printStackTrace(); }
						 * employee.setJoinDateStr(jnDate);
						 */
						city = empDao.getCityByName(cityName);

						employee.setEmployeeCity(city);

						if (city != null)
							state = controller.getState(stateCmpList, city.getStateId());
						employee.setEmployeeState(state);

						if (state != null)
							country = controller.getCountry(countryCmpList, state.getCountryId());
						employee.setEmployeeCountry(country);

						employee.setEmployeeMobilenumber(cellToString(row.getCell(mobileNo)));
						employee.setStatus(true);
						empDao.saveOrUpdate(employee);
						empCmpList = empDao.getEmpList();
					}

					int empId = controller.getEmployeeId(empCmpList, emplId);
					// System.out.println("empId...." + empId);
					resource.setEmployeeId(empId);

					/*
					 * String projDate =
					 * dataFormatter.formatCellValue(row.getCell(date)); date1 =
					 * new SimpleDateFormat("dd-MM-yyyy").parse(projDate);
					 */

					Date date1 = new Date();
					String projName = cellToString(row.getCell(primaryAssignment));
					if (projName != null && projName != "-" && projName != "" && !projName.equalsIgnoreCase("Bench")) {
						String priProjName[] = projName.split("/");
						for (int j = 0; j < priProjName.length; j++) {
							resource = new Resourceallocations();
							resource.setEmployeeId(empId);
							prjectFlag = false;
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(priProjName[j].trim())) {
									prjectFlag = true;
									date2 = new Date();
									// if(date2.before(proj.getStartDate())) {
									if (date2.before(proj.getStartDate()) && proj.getStartDate().after(date1))
										resource.setProjectFrom(proj.getStartDate());
									else if (date2.before(date1))
										resource.setProjectFrom(date1);
									else
										resource.setProjectFrom(date2);

								}
							}

							if (prjectFlag == false) {
								project = new Projects();
								project.setProjectName(priProjName[j].trim());
								project.setStartDate(new Date());
								project.setStatus(true);
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
								resource.setProjectFrom(new Date());
							}

							int projectId = controller.getProjectId(projectsCmpList, priProjName[j].trim());
							if (projectId > 0) {
								boolean istrue = false;
								resource.setProjectId(projectId);

								resource.setProjectCompleted("N");
								resource.setAllocation("p");
								int count0 = resourceDao.checkEmpResoruceAllocation(empId, projectId);
								if (count0 == 0) {
									resourceDao.insert(resource);
								} else {
									int count = resourceDao.setResUpdate(empId, projectId, resource.getProjectFrom());
									if (count == 1) {
										resourceDao.insert(resource);
									}
								}
							}
						}
					}

					String otherProjects = cellToString(row.getCell(otherAssignments));
					if (otherProjects != null && otherProjects != "-" && otherProjects != "" && !otherProjects.equalsIgnoreCase("Bench")) {
						String secProjName[] = otherProjects.split("/");
						for (int j = 0; j < secProjName.length; j++) {
							// secPorjFlag = false;//this shld be
							resource = new Resourceallocations();
							resource.setEmployeeId(empId);
							secPorjFlag = false;
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(secProjName[j].trim())) {
									secPorjFlag = true;
								}
							}
							if (secPorjFlag == false) {
								project = new Projects();
								project.setProjectName(secProjName[j]);
								project.setStartDate(new Date());
								project.setStatus(true);
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							int scndProjectId = controller.getProjectId(projectsCmpList, secProjName[j]);

							resource.setProjectId(scndProjectId);
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date currentDate = new Date();
							resource.setProjectFrom(currentDate);
							resource.setProjectCompleted("N");
							resource.setAllocation("s");

							int count3 = resourceDao.checkEmpResoruceAllocation(empId, scndProjectId);

							if (count3 == 0) {

								resourceDao.insert(resource);
							}

							else {

								int count4 = resourceDao.setResUpdate(empId, scndProjectId, resource.getProjectFrom());

								if (count4 == 1) {
									resourceDao.insert(resource);
								}
							}
						}
					}

				}

				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	/*@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				ResourceController controller = new ResourceController();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				System.out.println("Resource bulk upload....");
				String fileName = file.getOriginalFilename();

				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int id = -1, name = -1, designation = -1, serviceCategory = -1, primaryAssignment = -1,
						otherAssignments = -1, mobileNo = -1, location = -1, date = -1,
						reportingTo = -1;

				// Read the headers first. Locate the ones you need  specialization = -1, specialization == -1 ||
				XSSFRow rowHeader = ws.getRow(2);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("ID".equalsIgnoreCase(cellValue)) {
						id = j;
					} else if ("Name".equalsIgnoreCase(cellValue)) {
						name = j;
					} else if ("Designation".equalsIgnoreCase(cellValue)) {
						designation = j;
					} else if ("Reporting Manager".equalsIgnoreCase(cellValue)
							|| "Reporting_Manager".equalsIgnoreCase(cellValue)) {
						reportingTo = j;
					} else if ("Service_Category".equalsIgnoreCase(cellValue)
							|| "Service Category".equalsIgnoreCase(cellValue)) {
						serviceCategory = j;
					} else if ("Primary Assignment".equalsIgnoreCase(cellValue)
							|| "Primary_Assignment".equalsIgnoreCase(cellValue)) {
						primaryAssignment = j;
					} else if ("Other Assignments".equalsIgnoreCase(cellValue)
							|| "Other_Assignments".equalsIgnoreCase(cellValue)) {
						otherAssignments = j;
					} else if ("Specialization".equalsIgnoreCase(cellValue)) {
						specialization = j;
					} else if ("Mobile No".equalsIgnoreCase(cellValue) || "Mobile_No".equalsIgnoreCase(cellValue)) {
						mobileNo = j;

					} else if ("Location".equalsIgnoreCase(cellValue)) {
						location = j;
					} 
						 * else if ("Date".equalsIgnoreCase(cellValue)) { date =
						 * j; } else if ("JoinDate".equalsIgnoreCase(cellValue)
						 * || "Join_Date".equalsIgnoreCase(cellValue)) {
						 * joinDate = j; }
						 
				}

				if (id == -1 || name == -1 || designation == -1 || serviceCategory == -1 || primaryAssignment == -1
						|| otherAssignments == -1 || mobileNo == -1 || location == -1
						|| reportingTo == -1) {
					try {
						throw new Exception(
								"Could not find header indexes\nemployeeId : " + id + " | EmployeeName : " + name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				List<Employee> empList = new ArrayList<Employee>();
				List<Resourceallocations> resourceList = new ArrayList<>();
				List<Projects> projectsList = new ArrayList<>();
				List<Category> categoryList = new ArrayList<>();
				Employee employee = null;
				Resourceallocations resource = null;
				Projects project = null;
				empCmpList = empDao.getEmpList();
				categoryCmpList = empDao.getCategoryList();
				stateCmpList = empDao.getStates();
				countryCmpList = empDao.getCountryList();
				projectsCmpList = projectDao.getProjects();
				managerCmpList = usersDao.getListEmpUsers();

				boolean empFlag = false;
				boolean prjectFlag = false;
				boolean secPorjFlag = false;
				boolean categoryFlag = false;

				for (int i = 3; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					resource = new Resourceallocations();
					DataFormatter dataFormatter = new DataFormatter();
					String emplId = dataFormatter.formatCellValue(row.getCell(id));
					String categoryName = cellToString(row.getCell(serviceCategory));
					String cityName = cellToString(row.getCell(location));
					String reportingManager = cellToString(row.getCell(reportingTo));

					Category category = null;
					Cities city = null;
					State state = null;
					Countries country = null;
					Date date2 = null;

					for (Employee emp : empCmpList) {
						empFlag = false;
						if (emp.getEmployeeId().equalsIgnoreCase(emplId)) {
							empFlag = true;
							date2 = emp.getJoinDate();
							break;
						}
					}
					if (empFlag == false) {
						employee = new Employee();
						employee.setEmployeeId(emplId);
						employee.setEmployeeName(cellToString(row.getCell(name)));
						employee.setEmployeeDesg(cellToString(row.getCell(designation)));

						Employee userEmp = null;
						for (Employee manager : managerCmpList) {
							if (manager.getEmployeeName().equalsIgnoreCase(reportingManager)) {
								userEmp = manager;
							}
						}

						employee.setReportingTo(userEmp);
						///for specialization
						//employee.setEmployeeSpecialization(cellToString(row.getCell(specialization)));

						
						 * category = controller.getCategory(categoryCmpList,
						 * categoryName);
						 * employee.setEmployeeCategory(category);
						 

						category = controller.getCategory(categoryCmpList, categoryName);
						for (Category catgry : categoryCmpList) {
							categoryFlag = false;
							if (catgry.getCategoryName().equalsIgnoreCase(categoryName)) {
								categoryFlag = true;
								category.setCategoryName(catgry.getCategoryName());
								break;
							}
						}

						if (categoryFlag == false) {
							category = new Category();
							category.setCategoryName(categoryName);
							category.setStatus(true);
							category.setIsCategory("Y");
							categoryDao.saveOrUpdate(category);
							categoryCmpList = categoryDao.getCategory();
						}

						employee.setEmployeeCategory(category);

						
						 * String jnDate =
						 * dataFormatter.formatCellValue(row.getCell(joinDate));
						 * try { date2 = new
						 * SimpleDateFormat("dd-MM-yyyy").parse(jnDate);
						 * employee.setJoinDate(date2); } catch (ParseException
						 * e) { e.printStackTrace(); }
						 * employee.setJoinDateStr(jnDate);
						 
						city = empDao.getCityByName(cityName);

						employee.setEmployeeCity(city);

						if (city != null)
							state = controller.getState(stateCmpList, city.getStateId());
						employee.setEmployeeState(state);

						if (state != null)
							country = controller.getCountry(countryCmpList, state.getCountryId());
						employee.setEmployeeCountry(country);

						employee.setEmployeeMobilenumber(cellToString(row.getCell(mobileNo)));
						employee.setStatus(true);
						empDao.saveOrUpdate(employee);
						empCmpList = empDao.getEmpList();
					}

					int empId = controller.getEmployeeId(empCmpList, emplId);
					// System.out.println("empId...." + empId);
					resource.setEmployeeId(empId);

					
					 * String projDate =
					 * dataFormatter.formatCellValue(row.getCell(date)); date1 =
					 * new SimpleDateFormat("dd-MM-yyyy").parse(projDate);
					 

					Date date1 = new Date();
					String projName = cellToString(row.getCell(primaryAssignment));
					if (projName != null && projName != "-" && !projName.equalsIgnoreCase("Bench")) {
						String priProjName[] = projName.split("/");
						for (int j = 0; j < priProjName.length; j++) {
							resource = new Resourceallocations();
							resource.setEmployeeId(empId);
							prjectFlag = false;
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(priProjName[j].trim())) {
									prjectFlag = true;
									date2 = new Date();
									// if(date2.before(proj.getStartDate())) {
									if (date2.before(proj.getStartDate()) && proj.getStartDate().after(date1))
										resource.setProjectFrom(proj.getStartDate());
									else if (date2.before(date1))
										resource.setProjectFrom(date1);
									else
										resource.setProjectFrom(date2);

								}
							}

							if (prjectFlag == false) {
								project = new Projects();
								project.setProjectName(priProjName[j].trim());
								project.setStartDate(new Date());
								project.setStatus(true);
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
								resource.setProjectFrom(new Date());
							}

							int projectId = controller.getProjectId(projectsCmpList, priProjName[j].trim());
							if (projectId > 0) {
								boolean istrue = false;
								resource.setProjectId(projectId);

								resource.setProjectCompleted("N");
								resource.setAllocation("p");
								int count0 = resourceDao.checkEmpResoruceAllocation(empId, projectId);
								if (count0 == 0) {
									resourceDao.insert(resource);
								} else {
									int count = resourceDao.setResUpdate(empId, projectId, resource.getProjectFrom());
									if (count == 1) {
										resourceDao.insert(resource);
									}
								}
							}
						}
					}

					String otherProjects = cellToString(row.getCell(otherAssignments));
					if (otherProjects != null && otherProjects != "-" && !otherProjects.equalsIgnoreCase("Bench")) {
						String secProjName[] = otherProjects.split("/");
						for (int j = 0; j < secProjName.length; j++) {
							// secPorjFlag = false;//this shld be
							resource = new Resourceallocations();
							resource.setEmployeeId(empId);
							secPorjFlag = false;
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(secProjName[j].trim())) {
									secPorjFlag = true;
								}
							}
							if (secPorjFlag == false) {
								project = new Projects();
								project.setProjectName(secProjName[j]);
								project.setStartDate(new Date());
								project.setStatus(true);
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							int scndProjectId = controller.getProjectId(projectsCmpList, secProjName[j]);

							resource.setProjectId(scndProjectId);
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date currentDate = new Date();
							resource.setProjectFrom(currentDate);
							resource.setProjectCompleted("N");
							resource.setAllocation("s");

							int count3 = resourceDao.checkEmpResoruceAllocation(empId, scndProjectId);

							if (count3 == 0) {

								resourceDao.insert(resource);
							}

							else {

								int count4 = resourceDao.setResUpdate(empId, scndProjectId, resource.getProjectFrom());

								if (count4 == 1) {
									resourceDao.insert(resource);
								}
							}
						}
					}

				}

				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}
*/
	@RequestMapping(value = "addOrRemoveResource", method = RequestMethod.POST)
	public String addOrRemoveResource(@RequestParam CommonsMultipartFile file, HttpSession session)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				ResourceController controller = new ResourceController();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();
				empCmpList = empDao.getEmpList();

				FileInputStream fileStream = new FileInputStream(file1);

				// Create Workbook instance holding reference to .xlsx file
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

				// Get first/desired sheet from the workbook
				XSSFSheet ws = workbook.getSheetAt(0);

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int empId = -1, primaryProject = -1, secondaryProject = -1, startDate = -1, endDate = -1, action = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("Employee Id".equalsIgnoreCase(cellValue) || "Employee_Id".equalsIgnoreCase(cellValue)) {
						empId = j;

					} else if ("Primary Project".equalsIgnoreCase(cellValue)
							|| "Primary_Project".equalsIgnoreCase(cellValue)) {
						primaryProject = j;
					} else if ("Secondary project".equalsIgnoreCase(cellValue)
							|| "Secondary_project".equalsIgnoreCase(cellValue)) {
						secondaryProject = j;
					} else if ("Start Date".equalsIgnoreCase(cellValue) || "Start_Date".equalsIgnoreCase(cellValue)) {
						startDate = j;
					} else if ("End Date".equalsIgnoreCase(cellValue) || "End_Date".equalsIgnoreCase(cellValue)) {
						endDate = j;

					} else if ("Action".equalsIgnoreCase(cellValue)) {
						action = j;
					}
				}

				if (empId == -1 || primaryProject == -1 || secondaryProject == -1 || startDate == -1 || endDate == -1
						|| action == -1) {
					try {
						throw new Exception("Could not find header indexes\nemployeeId : " + empId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Resourceallocations resource = null;
				Projects project = null;
				for (int i = 1; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					resource = new Resourceallocations();

					DataFormatter df = new DataFormatter();
					String employeeId = df.formatCellValue(row.getCell(empId));

					int id = controller.getEmployeeId(empCmpList, employeeId);

					if (id != 0) {

						DataFormatter dataFormatter = new DataFormatter();
						String projStartDate = dataFormatter.formatCellValue(row.getCell(startDate));
						String projEndDate = dataFormatter.formatCellValue(row.getCell(endDate));

						Date dateStart = null;
						Date dateEnd = null;
						if (projStartDate != "") {
							dateStart = new SimpleDateFormat("dd/MM/yyyy").parse(projStartDate);
						} else
							dateStart = new Date();

						if (projEndDate != "") {
							dateEnd = new SimpleDateFormat("dd/MM/yyyy").parse(projEndDate);
						} else
							dateEnd = new Date();

						if (projectsCmpList == null) {
							projectsCmpList = projectDao.getProjects();
						}

						int projectId = 0;
						String primaryProjName = cellToString(row.getCell(primaryProject));
						if (primaryProjName != null) {
							boolean prjectFlag = false;
							boolean secPorjFlag = false;
							System.out.println("primaryProjName..." + primaryProjName);
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(primaryProjName)) {
									prjectFlag = true;
									break;
								}
							}

							if (prjectFlag == false) {
								System.out.println("Inserting primary project");
								project = new Projects();
								project.setProjectName(primaryProjName);
								project.setStartDate(dateStart);
								project.setStatus(true);
								/*
								 * if (dateEnd != null) {
								 * project.setEndDate(dateEnd);
								 * project.setStatus(true); }
								 */
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							resource.setAllocation("p");
							projectId = controller.getProjectId(projectsCmpList, primaryProjName);

						}

						String secondaryProjName = cellToString(row.getCell(secondaryProject));
						if (secondaryProjName != null) {
							boolean secPorjFlag = false;
							System.out.println("secondaryProjName..." + secondaryProjName);
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(secondaryProjName)) {
									secPorjFlag = true;
									break;
								}
							}

							if (secPorjFlag == false) {
								System.out.println("Inserting Secondary project");
								project = new Projects();
								project.setProjectName(secondaryProjName);
								project.setStartDate(dateStart);
								project.setStatus(true);
								/*
								 * if (dateEnd != null) {
								 * project.setEndDate(dateEnd);
								 * project.setStatus(true); }
								 */
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							resource.setAllocation("s");
							projectId = controller.getProjectId(projectsCmpList, secondaryProjName);

						}

						String projAction = cellToString(row.getCell(action));
						if (resourceCmpList == null) {
							resourceCmpList = resourceDao.getResources();
						}
						Resourceallocations res = controller.getResource(resourceCmpList, id, projectId);
						System.out.println("res...." + res);
						boolean dateFlag = false;
						if (projAction.equalsIgnoreCase("add")) {
							if (res != null) {
								resource.setResId(res.getResId());
								resource.setEmployeeId(id);
								resource.setProjectId(projectId);
								resource.setProjectFrom(res.getProjectFrom());
								resource.setProjectTo(res.getProjectFrom());
								resource.setAllocation(res.getAllocation());
								resource.setProjectCompleted("Y");
								System.out.println("formDateDay..=" + res.getProjectFrom().getDate() + "..formDateMonth"
										+ res.getProjectFrom().getMonth() + "....dateStartDay=" + dateStart.getDate()
										+ "....dateStartMonth=" + dateStart.getMonth());
								if (res.getProjectFrom().getDate() != dateStart.getDate()
										&& res.getProjectFrom().getMonth() != dateStart.getMonth()
										&& res.getProjectFrom().getYear() != dateStart.getYear()) {
									resourceDao.saveOrUpdate(resource);
									dateFlag = true;
								}
								resourceCmpList = resourceDao.getResources();
							} else {
								// if (dateFlag == true) {
								Resourceallocations resource1 = new Resourceallocations();
								resource1.setEmployeeId(id);
								resource1.setProjectId(projectId);
								resource1.setProjectFrom(dateStart);
								if (primaryProjName != null)
									resource1.setAllocation("p");
								else if (secondaryProjName != null)
									resource1.setAllocation("s");
								resource1.setProjectCompleted("N");
								/*
								 * if (dateEnd != null) {
								 * resource.setProjectTo(dateEnd);
								 * resource.setProjectCompleted("Y"); }
								 */
								System.out.println("Inserting Resource with PrimaryProject In Add..." + projectId);
								resourceDao.insert(resource1);
								resourceCmpList = resourceDao.getResources();
							}
						} else if (projAction.equalsIgnoreCase("remove")) {
							if (res != null && projStartDate == "") {
								resourceDao.updateResource(res.getResId(), dateEnd);
								resourceCmpList = resourceDao.getResources();
							}
						}

					}
				}

				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		if (cell == null) {
			return null;
		} else {
			type = cell.getCellType();
			switch (type) {

			case XSSFCell.CELL_TYPE_NUMERIC:
				result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

				break;
			case XSSFCell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
			}
			if (result == null) {
				return null;
			}
			return result.toString();
		}
	}
	@RequestMapping(value = "allEmpMassAllocations", method = RequestMethod.GET)
	 public String allEmpMassAllocations(ModelMap model) {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   List<Projects> projList = projectDao.getActiveProjects();
	   model.put("projList", projList);
	   return "allEmpMassAllocations";
	  } else
	   return "redirect:/login";
	 }
	@RequestMapping(value = "allocateAllEmployees/{projId}", method = RequestMethod.GET)
	 public String allocateAllMassEmployees(@PathVariable int projId, ModelMap model) {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   //int empId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
	   //int id = sessionobj.getEmpUsersObj().getId();

	   //List<Employee> employeeList = empDao.getEmployeesUnderManagers(id);
	   List<Employee> employeeList = empDao.getActiveEmployeeList();
	   List<Resourceallocations> resList = resourceDao.getResources();
	   List<Projects> projectList = projectDao.getProjects();
	   List<Employee> empList = new ArrayList<>();
	   List<EmployeeSkillSet> empSkillList = categoryDao.getEmpSkillSet();
	   for (Employee emp : employeeList) {
	    for (Resourceallocations res : resList) {
	     if (res.getEmployeeId() == emp.getEmpId() && res.getProjectId() == projId
	       && res.getProjectCompleted().equalsIgnoreCase("N")) {
	      emp.setAssigned(true);
	      emp.setBillable(res.getBillable());
	      break;
	     } else {
	      emp.setAssigned(false);
	      emp.setBillable("N");
	     }

	    }
	    empList.add(emp);
	   }
	   model.put("empList", empList);
	   // model.put("resList", resList);
	   model.put("empSkillList", empSkillList);
	   model.put("projectList", projectList);
	   model.put("projId", projId);

	   return "allocateAllMassEmployees";
	  } else
	   return "redirect:/login";
	 }
	
	@RequestMapping(value = "/updateAllMultipleResources", method = RequestMethod.POST)
	public String updateAllEmpBulkResources(HttpServletRequest request, ModelMap model) throws ParseException {

		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Resourceallocations> resList = resourceDao.getResources();
			Date date1 = null, date2 = null;
			int projId = Integer.parseInt(request.getParameter("projId"));
			// String billable = request.getParameter("billable");
			// System.out.println("billableEmpId ====" +billableEmpId);
			String fromDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");
			if (fromDate != null && fromDate != "")
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			else
				date1 = new Date();
			if (endDate != null && endDate != "")
				date2 = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

			Resourceallocations res = null;
			boolean flag = true;
			boolean flagbillable = true;
			/*
			 * String billable1[] = request.getParameterValues("billable"); for
			 * (String b : billable1) { System.out.println("billable----" + b);
			 * }
			 */

			if (request.getParameterValues("empId") != null) {
				for (String empId : request.getParameterValues("empId")) {

					System.out.println("empId non billable=" + empId);
					// System.out.println("billable----" + billable);

					int id = Integer.parseInt(empId);
					if (resList != null && !resList.isEmpty())
						flag = compareResources(id, projId, resList);
					// flagbillable = compareResourcesBillable(id, projId,
					// billable, resList);

					if (flag) {
						res = new Resourceallocations();
						res.setEmployeeId(id);
						res.setProjectId(projId);
						res.setProjectFrom(date1);
						res.setBillable("N");
						if (request.getParameterValues("billable") != null) {
							for (String billable : request.getParameterValues("billable")) {
								if (billable.equalsIgnoreCase(empId)) {
									res.setBillable("Y");
									System.out.println("empId billable=" + empId);
									break;
								}

							}
						}
						if (date2 != null) {
							res.setProjectTo(date2);
							res.setProjectCompleted("Y");
						} else
							res.setProjectCompleted("N");
						res.setAllocation("P");
						resourceDao.saveOrUpdate(res);
					}
				}
			}

			return "redirect:/Resource/allEmpMassAllocations";
		} else
			return "redirect:/login";

	}
	@RequestMapping(value = "/removeAllMultipleResources/{projId}", method = RequestMethod.POST)
	public String removeallEmpMultipleResources(@PathVariable int projId, HttpServletRequest request, ModelMap model)
			throws ParseException {

		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Date date1 = null, date2 = null;
			// int projId = Integer.parseInt(request.getParameter("projId"));
			String fromDate = request.getParameter("startdate");
			String endDate = request.getParameter("enddate");

			if (fromDate != null && fromDate != "")
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromDate);
			else
				date1 = new Date();

			if (endDate != null && endDate != "")
				date2 = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
			else
				date2 = new Date();

			if (request.getParameterValues("empId") != null) {
				for (String empId : request.getParameterValues("empId")) {
					int id = Integer.parseInt(empId);
					resourceDao.removeMultipleResource(id, projId, date2);
				}
			}

			return "redirect:/Resource/allEmpMassAllocations";
		} else
			return "redirect:/login";

	}

}
