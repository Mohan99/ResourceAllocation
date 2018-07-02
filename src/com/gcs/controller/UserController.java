package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Users;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.LoginRequest;
import com.gcs.requestDao.UserEmployeeRequest;
import com.gcs.requestDao.UsersRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("/Users")
public class UserController extends BaseController {
	@Autowired
	private UsersDao usersDao;

	@Autowired
	private SessionData sessionobj;
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;

	@RequestMapping(value = "/createUsers", method = RequestMethod.GET)
	public ModelAndView createUsers(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			UsersRequest usersReq = new UsersRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("UsersRequest", usersReq);

			modelObj = new ModelAndView("createUsers", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ModelAndView validateUser(@ModelAttribute UsersRequest usersRequest, BindingResult result,
			LoginRequest loginRequest, ModelMap model) {
		ModelAndView loginModel = null;
		Response resp = null;
		if (usersRequest.getEmail() != null && usersRequest.getEmail().length() != 0) {
			resp = usersDao.validateUsers(usersRequest.getUserName(), usersRequest.getUserId(),
					usersRequest.getLoginName(), usersRequest.getLoginPassword(), usersRequest.getEmail(),
					usersRequest.isIsActive());
			Map<String, Object> mapModel = new HashMap<String, Object>();
			if (resp.getStatusCode() == "0") {
				sessionobj.setValidLogin(true);
				
				sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
				// sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
				mapModel.put("Response", resp);
				loginModel = new ModelAndView("dashboard", mapModel);

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

	@RequestMapping(value = "/createOrUpdateUsers", method = RequestMethod.GET)
	public String createOrUpdateUsers(@ModelAttribute UsersRequest usersRequest, BindingResult result, ModelMap model) {
		// System.out.println(empRequest.getEmployeeName());
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Users userObj = new Users();
			userObj.setUserId(usersRequest.getUserId());
			userObj.setUserName(usersRequest.getUserName());
			userObj.setLoginName(usersRequest.getLoginName());
			userObj.setLoginPassword(usersRequest.getLoginPassword());
			userObj.setIsActive(true);
			userObj.setEmail(usersRequest.getEmail());
			userObj.setRole(usersRequest.getRole());
			
			usersDao.saveOrUpdate(userObj);

			return "redirect:/Users/searchUsers";
		} else
			return "redirect:/login";
	}


	@RequestMapping(value = "/searchUsers", method = RequestMethod.GET)
	public ModelAndView usersList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Users> usersList = usersDao.getListUsers();
			modelObj = new ModelAndView("searchUsers", "Users", usersList);
			// modelObj = new ModelAndView("searchEmployee", "GcsEmpManager",
			// empList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/editUsers/{userId}", method = RequestMethod.GET)
	public ModelAndView editUsers(@PathVariable Integer userId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Users> usersList = usersDao.getListUsers();
			Users usersObj = usersDao.getUsersData(userId);

			UsersRequest usersReq = new UsersRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			mapModel.put("UsersRequest", usersReq);
			mapModel.put("usersList", usersList);
			mapModel.put("Users", usersObj);

			modelObj = new ModelAndView("editUsers", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/deleteUsers/{userId}", method = RequestMethod.GET)
	public String deleteUsers(@PathVariable Integer userId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (userId != null) {
				usersDao.delete(userId, true);
			}
			return "redirect:/Users/searchUsers";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/deleteMultiple", method = RequestMethod.GET)
	public String deleteMultipleRecords(HttpServletRequest request, ModelMap model) {
		try {
			if (request.getParameterValues("userId") != null) {
				for (String userId : request.getParameterValues("userId")) {
					int id = Integer.parseInt(userId);
					System.out.println("Id===" + id);
					this.usersDao.removeUser(id);
				}
				return "redirect:/Users/searchUsers";
			}

			else {
				return "redirect:/login";
			}
		} catch (Exception e) {
			System.out.println("Id===");
			model.put("error", e.getMessage());
			model.addAttribute("Users", new Users());
			model.addAttribute("listUsers", this.usersDao.getListUsers());
			return "redirect:/Users/searchUsers";
		}
	}

	@RequestMapping(value = "/createEmployeeLoging", method = RequestMethod.GET)
	public ModelAndView createEmployeeLogin(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<Employee> empList = empDao.getEmpListNotIn();
			UserEmployeeRequest userEmpRequest=new UserEmployeeRequest();
			model.put("empList", empList);
			model.put("userEmpRequest", userEmpRequest);
			//modelObj = new ModelAndView("employeeLoginCreation", mapModel);
			modelObj = new ModelAndView("loginCreationEmployee", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "/createLogin/{empId}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {
				
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				List<Countries> countryList = empDao.getCountryList();
			//	List<State> stateList = empDao.getSatesList(empObj.getEmployeeCountry().getId());
			//	List<Cities> cityList = empDao.getCitiesList(empObj.getEmployeeState().getId());
				mapModel.put("countryList", countryList);
			//	mapModel.put("stateList", stateList);
			//	mapModel.put("cityList", cityList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("createEmplyeeLogin", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "createEmployeeUser", method = RequestMethod.POST)
	public String createOrUpdateEmployee(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Usersemployee user=new Usersemployee();
			Employee emp=empDao.getEmployeeData(String.valueOf(empRequest.getEmpId()));
			user.setEmpId(emp);
			user.setPassword(empRequest.getPassword());
			user.setStatus(1);
			usersDao.updateManagerStatus(empRequest.getEmpId(),true);
			usersDao.createLoggingEmployee(user);
			
			return "redirect:/Users/employeeLoginUsers";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "/employeeLoginUsers", method = RequestMethod.GET)
	public ModelAndView empUsersList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> usersList = usersDao.getListEmpUsers();
			modelObj = new ModelAndView("empLoggingUsersList", "users", usersList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "/removeUserEmp/{id}", method = RequestMethod.GET)
	public String removeUserEmp(@PathVariable Integer id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			System.out.println("usersemp id==="  + id);
			if (id != null) {
				usersDao.removeUserEmp(id, 1);
			}
			return "redirect:/Users/employeeLoginUsers";
		} else
			return "redirect:/login";
	}
	
	
	@RequestMapping(value = "/upadateEmployeeLoginUsers/{empId}", method = RequestMethod.GET)
	public ModelAndView upadateEmployeeLoginUsers(ModelMap model,@PathVariable String empId,@ModelAttribute EmployeeRequest empRequest) {
		ModelAndView modelObj = null;
		
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {
				
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				List<Countries> countryList = empDao.getCountryList();
			//	List<State> stateList = empDao.getSatesList(empObj.getEmployeeCountry().getId());
			//	List<Cities> cityList = empDao.getCitiesList(empObj.getEmployeeState().getId());
				mapModel.put("countryList", countryList);
			//	mapModel.put("stateList", stateList);
			//	mapModel.put("cityList", cityList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);
				modelObj = new ModelAndView("editEmployeePassword", mapModel);
			/*List<Employee> usersList = usersDao.getListEmpUsers();
			//List<Usersemployee> usersempList = usersDao.updateEmployeeLoginUsers(empId,usersEmpReq.getPassword());
			//model.put("usersempList", usersempList);
			model.put("EmployeeRequest", empRequest);
			modelObj = new ModelAndView("editEmployeePassword", "users", usersList);
		*/
			
		}
		}else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}


@RequestMapping(value = "/upadatePasswdEmployeeLoginUsers", method = RequestMethod.POST)
	public String upadatePasswdEmployeeLoginUsers(ModelMap model,@ModelAttribute EmployeeRequest empRequest, BindingResult result) {
	
		
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Usersemployee user=new Usersemployee();
			Employee emp=empDao.getEmployeeData(String.valueOf(empRequest.getEmpId()));
			user.setEmpId(emp);
			user.setPassword(empRequest.getPassword());
			usersDao.updateEmployeeLoginUsers(user.getEmpId().getEmpId(),user.getPassword());
			//usersDao.createLoggingEmployee(user);
			usersDao.updateManagerStatus(empRequest.getEmpId(),true);
			//model.put("usersempList", usersempList);
			return "redirect:/Users/employeeLoginUsers";
		} else
			return "redirect:/login";
			
	}

}
