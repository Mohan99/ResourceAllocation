package com.gcs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Project")
public class ProjectController extends BaseController {
	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	@Qualifier("UsersDao")
	private UsersDao usersDao;
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "activeProject", method = RequestMethod.GET)
	public ModelAndView activeProjectList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			
			List<Projects> projectList = projectDao.getActiveProjects();
			modelObj = new ModelAndView("activeProject", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	@RequestMapping(value = "closedProject", method = RequestMethod.GET)
	public ModelAndView closedProjectList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getClosedProjects();
			modelObj = new ModelAndView("closedProjects", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createProject", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ProjectRequest projectReq = new ProjectRequest();
			List<Employee> userEmpList=empDao.getActiveEmployeeList();
			
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("projectRequest", projectReq);
			
			model.put("userEmpList", userEmpList);

			modelObj = new ModelAndView("createProject", mapModel);
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
		return strDate;
	}

	@RequestMapping(value = "/editProject/{projectId}", method = RequestMethod.GET)
	public ModelAndView editProject(@PathVariable String projectId,ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getProjects();
			List<Employee> userEmpList=usersDao.getListEmpUsers();
			Projects project = projectDao.getProjectData(projectId);			
			if(project != null){
				
					if (project.getStartDate() != null)
						project.setStartDateStr(getDateFormat(project.getStartDate()));
					if (project.getEndDate() != null){
						project.setEndDateStr(getDateFormat(project.getEndDate()));
					}
					

			
			ProjectRequest projectReq = new ProjectRequest();
			
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("projectRequest", projectReq);
			model.put("project", project);
			model.put("projectList", projectList);
			model.put("userEmpList",userEmpList);
			
			//model.put("projects", projects);
			modelObj = new ModelAndView("editProject", mapModel);
		} 
		}
		else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	
	
	@RequestMapping(value = "createOrupdateProject", method = RequestMethod.POST)
	public String createOrUpdateEmployee(@ModelAttribute ProjectRequest projectRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Projects project = new Projects();
			if(projectRequest.getProjectId()!= null){
				project.setProjectId(projectRequest.getProjectId());
			}
			project.setProjectName(projectRequest.getProjectName());
			project.setProjectManager(projectRequest.getProjectManager());
			Resourceallocations re= new Resourceallocations();
			
		    if (projectRequest.getStartDate() != null)
				project.setStartDate(projectRequest.getStartDate());
			if (projectRequest.getEndDate() != null) {
				project.setEndDate(projectRequest.getEndDate());
				project.setStatus(false);
				Boolean isTrue= projectDao.setProjectEndDate(projectRequest.getProjectId(), projectRequest.getEndDate());
			} else
				project.setStatus(true);
			project.setDeleteStatus(true);
			
			if(projectRequest.getCalBench()!=null)
				project.setCalBench("Y");
			else
				project.setCalBench("N");
			
			project.setProjectType(projectRequest.getProjectType());
			
			projectDao.saveOrUpdate(project);
			return "redirect:/Project/activeProject";
		} else
			return "redirect:/login";
	}
	
	
	@RequestMapping(value = "activeProjectUnderManager", method = RequestMethod.GET)
	public ModelAndView activeProjectUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		int id=sessionobj.getEmpObj().getEmpId();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			
			List<Projects> projectList = projectDao.getActiveProjectsUnderManager(id);
			modelObj = new ModelAndView("activeProjectUnderManager", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	@RequestMapping(value = "completedProjectsUnderManager", method = RequestMethod.GET)
	public ModelAndView completedProjectsUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		int id=sessionobj.getEmpObj().getEmpId();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getCompletedProjectsUnderManager(id);
			modelObj = new ModelAndView("completedProjectUnderManager", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "totalProjectsUnderManager", method = RequestMethod.GET)
	public ModelAndView totalProjectsUnderManager(ModelMap model) {
		ModelAndView modelObj = null;
		int id=sessionobj.getEmpObj().getEmpId();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getTotalProjectsUnderManager(id);
			modelObj = new ModelAndView("searchProject", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "totalProjects", method = RequestMethod.GET)
	public ModelAndView totalProjectsList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			
			List<Projects> projectList = projectDao.getProjects();
			modelObj = new ModelAndView("searchProject", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "deleteProject/{projectId}", method = RequestMethod.GET)
	public String deleteProject(@PathVariable Integer projectId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			
				projectDao.delete(projectId, true);
			
				return "redirect:/Project/activeProject";
		} else
			return "redirect:/login";
	}
	
	

}
