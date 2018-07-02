package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.SkillSet;




public interface CategoryDao {
	
	public List<Category> getCategory();
	public void saveOrUpdate(Category category); 
	public long categoryCount();
	public Category getCategory(String categoryID);
	//public void editCategory(String categoryID);
	public void saveOrUpdate(SkillSet skillSet); 
	public void saveOrUpdate(EmployeeSkillSet empSkillSet); 
	public List<SkillSet> getSkillSet();
	public SkillSet getSkillSet(String skillId);
	public List<EmployeeSkillSet> getEmpSkillSet();
	public void delete(Integer skillId, boolean status);
	public List<EmployeeSkillSet> getSkillSetByEmployee(int empId);
	public void deleteEmpSkill (Integer empId);
	long skillSetCount();
	void deletecat(Integer categoryId, boolean isProject);
	
	

}
