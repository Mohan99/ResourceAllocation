package com.gcs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Employee;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.LoginDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.ChangePasswordRequest;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.LoginRequest;
import com.gcs.requestDao.UserEmployeeRequest;
import com.gcs.requestDao.UsersRequest;
import com.gcs.responseDao.Response;

@Controller
// @RequestMapping("/Login")
public class LoginController extends BaseController {
	@Autowired
	private SessionData sessionobj;

	@Autowired
	@Qualifier("loginDao")
	private LoginDao loginDao;
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;

	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	@Autowired
	// @Qualifier("usersDao")
	private UsersDao usersDao;

	public EmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String dashBoard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {

			sessionobj.setEmployeeCount(empDao.empCount());
			sessionobj.setResourceCount(resourceDao.resourceCount());
			sessionobj.setBenchCount(resourceDao.benchCount());
			sessionobj.setProjectsCount(empDao.projectsCount());
			sessionobj.setUsersCount(usersDao.usersCount());
			sessionobj.setActiveProjectsCount(projectDao.ActiveProjectsCount());
			sessionobj.setClosedProjectsCount(projectDao.ClosedProjectsCount());
			return "dashboard";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "pmoDashboard", method = RequestMethod.GET)
	public String pmoDashboard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {

			sessionobj.setEmployeeCount(empDao.empCount());
			sessionobj.setResourceCount(resourceDao.resourceCount());
			sessionobj.setProjectsCount(empDao.projectsCount());
			sessionobj.setUsersCount(usersDao.usersCount());
			sessionobj.setActiveProjectsCount(projectDao.ActiveProjectsCount());
			sessionobj.setClosedProjectsCount(projectDao.ClosedProjectsCount());
			return "pmoDashboard";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "salesDashboard", method = RequestMethod.GET)
	public String salesDashboard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {

			sessionobj.setEmployeeCount(empDao.empCount());
			sessionobj.setResourceCount(resourceDao.resourceCount());
			sessionobj.setProjectsCount(empDao.projectsCount());
			sessionobj.setUsersCount(usersDao.usersCount());
			sessionobj.setActiveProjectsCount(projectDao.ActiveProjectsCount());
			sessionobj.setClosedProjectsCount(projectDao.ClosedProjectsCount());
			sessionobj.setSkillSetCount(categoryDao.skillSetCount());
			return "salesDashboard";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "managerdashboard", method = RequestMethod.GET)
	public String managerdashboard(ModelMap model, Employee userEmpReq) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {

			sessionobj
					.setActiveProjCount(projectDao.ActiveProjCount(sessionobj.getEmpObj().getEmpId()));
			sessionobj
					.setClosedProjCount(projectDao.ClosedProjCount(sessionobj.getEmpObj().getEmpId()));
			sessionobj.setResCountByReporting(
					resourceDao.ResCountByReporting(sessionobj.getEmpObj().getEmpId()));
			sessionobj.setProjCountByReporting(
					projectDao.projCountByReporting(sessionobj.getEmpObj().getEmpId()));

			return "managerdashboard";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginCall(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");

		return "redirect:/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");
		LoginRequest loginRequest = new LoginRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("LoginRequest", loginRequest);
		ModelAndView loginModel = new ModelAndView("login", mapModel);
		return loginModel;
	}

	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ModelAndView validateLogin(@ModelAttribute LoginRequest loginRequest, UsersRequest usersRequest,
			EmployeeRequest userEmpReq, BindingResult result, ModelMap model) {
		boolean userFlag = true,pmoFlag=false,empFlag=false,salesFlag=false;
		ModelAndView loginModel = null;
		Response resp = null, resp1 = null;

		if (loginRequest.getEmail() != null && loginRequest.getEmail().length() != 0) {
			resp = loginDao.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

			if (resp.getStatusCode() == "1") {
				resp = loginDao.validatePmoLogin(loginRequest.getEmail(), loginRequest.getPassword());
				userFlag = false;
				pmoFlag=true;
			}
			if (resp.getStatusCode() == "1") {
				resp = loginDao.validateAccountsLogin(loginRequest.getEmail(), loginRequest.getPassword());
				userFlag = false;
				pmoFlag=true;
			}
			
			if (resp.getStatusCode() == "1") {
				resp = loginDao.validateSalesLogin(loginRequest.getEmail(), loginRequest.getPassword());
				salesFlag=true;
				userFlag = false;
				pmoFlag=false;
			}
			
			if (resp.getStatusCode() == "1") {
				resp = loginDao.validateEmployeeLogin(loginRequest.getEmail(), loginRequest.getPassword());
				empFlag=true;
				userFlag = false;
				pmoFlag=false;
				salesFlag=false;
			}
			if (resp.getStatusCode() == "1") {
				resp = loginDao.validateAccountsLogin(loginRequest.getEmail(), loginRequest.getPassword());
				userFlag = false;
				pmoFlag=true;
			}
			
			Map<String, Object> mapModel = new HashMap<String, Object>();
			if (resp.getStatusCode() == "0") {
				sessionobj.setValidLogin(true);
				sessionobj.setEmployeeCount(empDao.empCount());
				sessionobj.setResourceCount(resourceDao.resourceCount());
				sessionobj.setBenchCount(resourceDao.benchCount());
				sessionobj.setProjectsCount(empDao.projectsCount());
				sessionobj.setUsersCount(usersDao.usersCount());
				sessionobj.setActiveProjectsCount(projectDao.ActiveProjectsCount());
				sessionobj.setClosedProjectsCount(projectDao.ClosedProjectsCount());


				if (userFlag) {
					sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
					sessionobj.setEmpObj(null);
					mapModel.put("Response", resp);
					loginModel = new ModelAndView("dashboard", mapModel);
				}
				else if(pmoFlag) {
					sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
					sessionobj.setEmpObj(null);
					mapModel.put("Response", resp);
					loginModel = new ModelAndView("pmoDashboard", mapModel);
				}
				
				else if(salesFlag) {
					sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
					sessionobj.setSkillSetCount(categoryDao.skillSetCount());
					sessionobj.setEmpObj(null);
					mapModel.put("Response", resp);
					loginModel = new ModelAndView("salesDashboard", mapModel);
				}

				else if(empFlag) {
					sessionobj.setEmpObj(usersDao.getEmpUserData(loginRequest.getEmail()));
					sessionobj.setResCountByReporting(
							resourceDao.ResCountByReporting(sessionobj.getEmpObj().getEmpId()));
					sessionobj.setEmpCountByReporting(
							empDao.empCountByReporting(sessionobj.getEmpObj().getEmpId()));
					sessionobj.setActiveProjCount(
							projectDao.ActiveProjCount(sessionobj.getEmpObj().getEmpId()));
					sessionobj.setClosedProjCount(
							projectDao.ClosedProjCount(sessionobj.getEmpObj().getEmpId()));
					sessionobj.setProjCountByReporting(
							projectDao.projCountByReporting(sessionobj.getEmpObj().getEmpId()));

					long count = sessionobj.getEmpCountByReporting() - sessionobj.getResCountByReporting();

					sessionobj.setUserObj(null);
					mapModel.put("Response", resp);
					loginModel = new ModelAndView("managerdashboard", mapModel);
				}

			} else {
				resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
				loginModel = getLogoutView(resp);
			}
		} else {
			resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
			loginModel = getLogoutView(resp);
		}
		return loginModel;

	}

	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");
		ChangePasswordRequest changeRequest = new ChangePasswordRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("ChangePasswordRequest", changeRequest);
		ModelAndView loginModel = new ModelAndView("changePassword", mapModel);
		return loginModel;
	}

	@RequestMapping(value = "changeEmpUserPassword", method = RequestMethod.GET)
	public ModelAndView changeEmpUserPassword(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");
		ChangePasswordRequest changeRequest = new ChangePasswordRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("ChangePasswordRequest", changeRequest);
		ModelAndView loginModel = new ModelAndView("changeEmpUserPwd", mapModel);
		return loginModel;
	}

	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute ChangePasswordRequest passwordRequest, BindingResult result,
			ModelMap model) {
		ModelAndView loginModel = null;
		Response resp = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {

			resp = loginDao.changePassword(sessionobj.getUserObj().getUserId(), passwordRequest.getOldPassword(),
					passwordRequest.getNewPassword());
			Map<String, Object> mapModel = new HashMap<String, Object>();
			mapModel.put("Response", resp);
			if (resp.getStatusCode() == "0") {
				loginModel = new ModelAndView("success", mapModel);
			} else {
				mapModel.put("ChangePasswordRequest", passwordRequest);
				loginModel = new ModelAndView("changePassword", mapModel);
			}

		} else {
			resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
			loginModel = getLogoutView(resp);
		}
		return loginModel;
	}

	@RequestMapping(value = "updateEmpUserPwd", method = RequestMethod.POST)
	public ModelAndView updateEmpUserPassword(@ModelAttribute ChangePasswordRequest passwordRequest,
			BindingResult result, ModelMap model) {
		ModelAndView loginModel = null;
		Response resp = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resp = loginDao.changeEmpPassword(sessionobj.getEmpObj().getEmpId(), passwordRequest.getOldPassword(),
					passwordRequest.getNewPassword());
			Map<String, Object> mapModel = new HashMap<String, Object>();
			mapModel.put("Response", resp);
			if (resp.getStatusCode() == "0") {
				loginModel = new ModelAndView("success", mapModel);
			} else {
				mapModel.put("ChangePasswordRequest", passwordRequest);
				loginModel = new ModelAndView("changeEmpUserPwd", mapModel);
			}

		} else {
			resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
			loginModel = getLogoutView(resp);
		}
		return loginModel;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest req) {
		sessionobj.setValidLogin(false);
		req.getSession(false).invalidate();
		return "redirect:/login";
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

}
