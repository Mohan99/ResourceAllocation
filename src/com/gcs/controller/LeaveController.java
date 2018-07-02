package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gcs.db.businessDao.Holidaycalender;
import com.gcs.db.businessDao.Leaves;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.HolidayDao;
import com.gcs.dbDao.LeavesDao;
import com.gcs.requestDao.HolidaycalenderRequest;
import com.gcs.requestDao.LeavesRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Leaves")
public class LeaveController extends BaseController {
	
	
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("leavesDao")
	private LeavesDao leavesDao;

	@Autowired
	private SessionData sessionobj;
	
	@RequestMapping(value = "/createLeaves", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			LeavesRequest leavesRequest = new LeavesRequest();
			List<Employee> empList = empDao.getEmpList();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("leavesRequest", leavesRequest);
			model.put("empList", empList);

			modelObj = new ModelAndView("createLeave", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "/createOrupdateLeaves", method = RequestMethod.GET)
	public String createOrUpdateResource(@ModelAttribute LeavesRequest leavesRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Leaves leaves = new Leaves();
			System.out.println("create or updateleave...." + leavesRequest);
			if (leavesRequest.getId() != 0) {
				leaves.setId(leavesRequest.getId());
			}
			System.out.println("EmpId=="+leavesRequest.getEmpId());
			leaves.setEmpId(leavesRequest.getEmpId());
		//	leaves.setEmpId(leavesRequest.getEmpId());
			System.out.println("empId...." +leavesRequest.getEmpId());
			leaves.setFromdate(leavesRequest.getFromdate());
			System.out.println("fromDate...." +leavesRequest.getFromdate());
			leaves.setTodate(leavesRequest.getTodate());
			leaves.setTotalleaves(leavesRequest.getTotalleaves());
			
			
			leavesDao.saveOrUpdate(leaves);
			return "redirect:/Leaves/searchLeaves";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "/searchLeaves", method = RequestMethod.GET)
	public ModelAndView employeeList(@ModelAttribute LeavesRequest leavesRequest, ModelMap model) {
		ModelAndView modelObj = null;
		
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Leaves> leavesList = leavesDao.getLeavesList();
			modelObj = new ModelAndView("searchLeaves", "leavesList", leavesList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

}
