package com.gcs.controller;

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
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.SkillSet;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.requestDao.CategoryRequest;
import com.gcs.requestDao.EmployeeSkillSetRequest;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.requestDao.SkillSetRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Category")
public class CategoryController extends BaseController {

	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;

	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;

	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "/createCategory", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CategoryRequest categoryReq = new CategoryRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("categoryRequest", categoryReq);

			modelObj = new ModelAndView("createCategory", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/createSkillSet", method = RequestMethod.GET)
	public ModelAndView createSkillSet(ModelMap model, SkillSetRequest skillsetReq) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			skillsetReq = new SkillSetRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("skillsetReq", skillsetReq);

			modelObj = new ModelAndView("createSkillSet", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/searchCategory", method = RequestMethod.GET)
	public ModelAndView employeeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = categoryDao.getCategory();
			modelObj = new ModelAndView("searchCategory", "categoryList", categoryList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/editCategory/{categoryId}", method = RequestMethod.GET)
	public ModelAndView editCategory(@PathVariable String categoryId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = categoryDao.getCategory();
			Category category = categoryDao.getCategory(categoryId);

			if (category != null) {

				/*
				 * if (project.getStartDate() != null)
				 * project.setStartDateStr(getDateFormat(project.getStartDate())
				 * ); if (project.getEndDate() != null)
				 * project.setEndDateStr(getDateFormat(project.getEndDate()));
				 */

				CategoryRequest categoryReq = new CategoryRequest();

				Map<String, Object> mapModel = new HashMap<String, Object>();
				model.put("categoryRequest", categoryReq);
				model.put("category", category);
				model.put("categoryList", categoryList);

				modelObj = new ModelAndView("editCategory", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createOrupdateCategory", method = RequestMethod.GET)
	public String createOrupdateCategory(@ModelAttribute CategoryRequest categoryRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Category category = new Category();
			if (categoryRequest.getCategoryId() != null) {
				category.setCategoryId(categoryRequest.getCategoryId());
			}
			category.setCategoryName(categoryRequest.getCategoryName());

			category.setStatus(true);
			
			if(categoryRequest.getIsCategory()!=null)
				category.setIsCategory("Y");
			else
				category.setIsCategory("N");

			categoryDao.saveOrUpdate(category);
			return "redirect:/Category/searchCategory";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/searchSkillSet", method = RequestMethod.GET)
	public ModelAndView searchSkillSet(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			modelObj = new ModelAndView("searchSkillSet", "skillSetList", skillSetList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createOrupdateSkillSet", method = RequestMethod.GET)
	public String createOrupdateSkillSet(@ModelAttribute SkillSetRequest skillSetRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			SkillSet skillSet = new SkillSet();
			if (skillSetRequest.getSkillId() != 0) {
				skillSet.setSkillId(skillSetRequest.getSkillId());
			}
			skillSet.setSkillSet(skillSetRequest.getSkillSet());

			skillSet.setStatus(true);

			categoryDao.saveOrUpdate(skillSet);
			return "redirect:/Category/searchSkillSet";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/editSkillSet/{skillId}", method = RequestMethod.GET)
	public ModelAndView editSkillSet(@PathVariable String skillId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<SkillSet> skillSetList = categoryDao.getSkillSet();
			SkillSet skillSet = categoryDao.getSkillSet(skillId);

			if (skillSet != null) {

				SkillSetRequest skillSetReq = new SkillSetRequest();

				Map<String, Object> mapModel = new HashMap<String, Object>();
				model.put("categoryRequest", skillSetReq);
				model.put("skillSet", skillSet);
				// model.put("categoryList", categoryList);

				modelObj = new ModelAndView("editSkillSet", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "/deleteSkillSet/{skillId}", method = RequestMethod.GET)
	public String deleteSkillSet(@PathVariable Integer skillId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (skillId != null) {
				categoryDao.delete(skillId, true);
			}
			return "redirect:/Category/searchSkillSet";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "deleteCategory/{categoryId}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable Integer categoryId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			
			categoryDao.deletecat(categoryId, true);
			
				return "redirect:/Category/searchCategory";
		} else
			return "redirect:/login";
	}
}
