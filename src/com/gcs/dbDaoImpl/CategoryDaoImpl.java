package com.gcs.dbDaoImpl;

import java.util.List;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.SkillSet;
import com.gcs.dbDao.CategoryDao;

@Transactional
@Repository("categoryDao")
public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao   {

	@Override
	public long categoryCount() {
		// TODO Auto-generated method stub
		long count = ((long) getSession().createQuery("select count(*) from Category").uniqueResult());
		 System.out.println("Count........."+count);
		return count;
	}

	@Override
	public List<Category> getCategory() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Category> list =getHibernateTemplate().find("from Category where status = 1"); 					
		return list;
	}
	
	@Override
	public long skillSetCount() {
		// TODO Auto-generated method stub
		long count = ((long) getSession().createQuery("select count(*) from SkillSet").uniqueResult());
		 System.out.println("Count........."+count);
		return count;
	}

	@Override
	public void saveOrUpdate(Category category) {
		// TODO Auto-generated method stub
		 getHibernateTemplate().saveOrUpdate(category);
		 getSession().flush();
		
	}
	
		
	@Override
	public Category getCategory(String categoryID) {
		@SuppressWarnings("unchecked")
		List<Category> list = getHibernateTemplate().find("from Category"
				+ " where categoryId like '%" + categoryID + "%'");
		if (list != null && list.get(0) != null) {
			System.out.println("Category By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
	}
	
	@Override
	public SkillSet getSkillSet(String skillId) {
		@SuppressWarnings("unchecked")
		List<SkillSet> list = getHibernateTemplate().find("from SkillSet"
				+ " where skillId like '%" + skillId + "%'");
		if (list != null && list.get(0) != null) {
			System.out.println("skillId By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
	}


	@Override
	public void saveOrUpdate(SkillSet skillSet) {
		// TODO Auto-generated method stub
		 getHibernateTemplate().saveOrUpdate(skillSet);
		 getSession().flush();
		
	}

	@Override
	public List<SkillSet> getSkillSet() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<SkillSet> list =getHibernateTemplate().find("from SkillSet where status=1"); 					
		return list;
	}

	@Override
	public void saveOrUpdate(EmployeeSkillSet empSkillSet) {
		// TODO Auto-generated method stub
		 getHibernateTemplate().saveOrUpdate(empSkillSet);
		 System.out.println("empSkillSet--"+empSkillSet);
		 getSession().flush();
	}

	@Override
	public List<EmployeeSkillSet> getEmpSkillSet() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<EmployeeSkillSet> list =getHibernateTemplate().find("from EmployeeSkillSet"); 			
		System.out.println("list---"+list);
		return list;
	}

	@Override
	public void delete(Integer skillId, boolean status) {
		// TODO Auto-generated method stub
		if (status) {
      int query =  getHibernateTemplate().bulkUpdate("update SkillSet set status=false where skillId= "+skillId);
			
		
		}
		
	}

	@Override
	public List<EmployeeSkillSet> getSkillSetByEmployee(int empId) {
		@SuppressWarnings("unchecked")
		List<EmployeeSkillSet> list =getHibernateTemplate().find("from EmployeeSkillSet where empId="+empId); 					
		return list;
	}

	@Override
	public void deleteEmpSkill(Integer empId) {
		// TODO Auto-generated method stub
		int query =  getHibernateTemplate().bulkUpdate("delete from EmployeeSkillSet where empId =" + empId);
		
	}
	@Override
	public void deletecat(Integer categoryId, boolean isProject) {
		// TODO Auto-generated method stub
		if (isProject) {
		      int query =  getHibernateTemplate().bulkUpdate("update Category set status=false where categoryId= "+categoryId);
					
				
				}
				
	}
	
}
