package com.gcs.dbDaoImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Users;
import com.gcs.db.businessDao.Workplace;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.requestDao.EmpReportDataRequest;
import com.gcs.responseDao.Response;

@Transactional
@Repository("employeeDao")
public class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	@Override
	public List<Employee> getInActiveEmpList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where status=false");
		
		ListIterator<Employee> itr = list.listIterator();
		Employee emp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while (itr.hasNext()) {
		emp = new Employee();
		emp = itr.next();
		// projects.setStartDateModified1(formatter.format(projects.getStartDate()));
		if (emp.getExitDate() != null)
		emp.setExitDateStr(formatter.format(emp.getExitDate()));

		}

		System.out.println("Employee Data...." + list);
		return list;
	}

	@Override
	public Employee getEmployeeData(String empID) {
		@SuppressWarnings("unchecked")
		int id=Integer.parseInt(empID);
		List<Employee> list = getHibernateTemplate().find("from Employee a where empId=" + id );
		if (list != null && list.get(0) != null) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public void delete(Integer id, boolean isEmployee) {
		if (isEmployee) {
			
			int query = getHibernateTemplate().bulkUpdate("update Employee set status=false where empId= " + id);
		}
		getHibernateTemplate().flush();

	}



	@Override
	public void insert(List<Employee> empList) {
		try {
			List<Employee> listEmpDB = getEmpList();
			for (int i = 0; i < empList.size(); i++) {
				Employee emp = empList.get(i);
				boolean isOldEmpId = false;
				for (int j = 0; j < listEmpDB.size(); j++) {
					Employee empDB = listEmpDB.get(j);

					if (empDB.getEmployeeId().equalsIgnoreCase(emp.getEmployeeId())) {
						isOldEmpId = true;
						emp.setEmpId(empDB.getEmpId());
						break;
					} else
						isOldEmpId = false;
				}
				if (!isOldEmpId) {
					getHibernateTemplate().save(emp);
				} else {
					getHibernateTemplate().merge(emp);
				}

				// getHibernateTemplate().save(emp);
				// getSession().flush();
				// getSession().getTransaction().commit();
			}

			// getSession().flush();
		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}

	}

	@Override
	public ResultSet getSwipeData(EmpReportDataRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long empCount() {
		long count = ((long) getSession().createQuery("select count(*) from Employee where status=1").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	
	@Override
	public long skillSetCount() {
		long count = ((long) getSession().createQuery("select count(*) from SkillSet where status=1").uniqueResult());
		 System.out.println("Count........."+count);
		return count;
	}
	
	

	@Override
	public boolean deleteSwipeData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllEmployees() {
		// TODO Auto-generated method stub
		String hql = "delete  from Employee";
		Query query = getSession().createQuery(hql);
		int result = query.executeUpdate();
		System.out.println(result);

	}

	@Override
	public List<Category> getCategoryList() {
		@SuppressWarnings("unchecked")
		List<Category> list = getHibernateTemplate().find("from Category where status=1");
		return list;
	}

	@Override
	public List<Workplace> getWorkplaceList() {
		@SuppressWarnings("unchecked")
		List<Workplace> list = getHibernateTemplate().find("from Workplace");
		return list;
	}

	@Override
	public List<Countries> getCountryList() {
		@SuppressWarnings("unchecked")
		List<Countries> list = getHibernateTemplate().find("from Countries");
		return list;
	}

	@Override
	public long projectsCount() {
		long count = ((long) getSession().createQuery("select count(*) from Projects where deleteStatus = 1 and calBench ='Y' ").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
/*	@Override
	public Response validateEmployee(String employeeId) {
		// TODO Auto-generated method stub
		
		System.out.println(employeeId);
		List<Employee> list = getHibernateTemplate().find(" from Employee where employeeId=" + employeeId );
		System.out.println(list.size());
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.Emp1);
			response.setStatusMessage(ConstantVariables.EmpMSGSUCESS);
			System.out.println(response.getStatusCode());
			System.out.println(response.getStatusMessage());

		} else {
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		System.out.println(response);
		return response;
		
	}*/
	@Override
	public void saveOrUpdate(Employee emp) {
		
			
			getHibernateTemplate().saveOrUpdate(emp);
			getSession().flush();
		}
		
	@Override
	public List<State> getSatesList(int countryId) {
		@SuppressWarnings("unchecked")
		List<State> list = getHibernateTemplate().find("from State where countryId=" + countryId);
		return list;
	}

	
	@Override
	public List<Cities> getCitiesList(int stateId) {
		@SuppressWarnings("unchecked")
		List<Cities> list = getHibernateTemplate().find("from Cities where stateId=" + stateId);
		return list;
	}

	@Override
	public List<State> getStates() {
		@SuppressWarnings("unchecked")
		List<State> list = getHibernateTemplate().find("from State ");
		return list;
	}

	@Override
	public List<Cities> getCities() {
		@SuppressWarnings("unchecked")
		List<Cities> list = getHibernateTemplate().find("from Cities ");
		return list;
	}

	@Override
	public Cities getCityByName(String cityName) {
		List<Cities> list = getHibernateTemplate().find("from Cities where name='" + cityName + "'");
		if (list!= null && !list.isEmpty()) {
			System.out.println("list=="+list);
			return list.get(0);
		}
		else
			return null;
	}

	@Override
	public List<Employee> getActiveEmployeeList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where status=true order by empId");
		ListIterator<Employee> itr = list.listIterator();
		Employee emp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while (itr.hasNext()) {
			emp = new Employee();
			emp = itr.next();
			// projects.setStartDateModified1(formatter.format(projects.getStartDate()));
			if (emp.getJoinDate() != null)
				emp.setJoinDateStr(formatter.format(emp.getJoinDate()));

		}

		return list;
	}

	@Override
	public List<Employee> getEmpListNotIn() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate()
				.find("from Employee where status=true and empId not in (select reportingTo from Employee ) or managerStatus=0 and status=1");
		ListIterator<Employee> itr = list.listIterator();
		Employee emp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while (itr.hasNext()) {
			emp = new Employee();
			emp = itr.next();
			// projects.setStartDateModified1(formatter.format(projects.getStartDate()));
			if(emp.getJoinDate()!=null)
			emp.setJoinDateStr(formatter.format(emp.getJoinDate()));

		}

		System.out.println("Employee Data...." + list);
		return list;
	}

	@Override
	public List<Employee> getEmpList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee");
		return list;
	}

	@Override
	public void updateStatus(String empId, boolean isEmployee) {
		// TODO Auto-generated method stub
		if (isEmployee) {

			int query = getHibernateTemplate().bulkUpdate("update Employee set status=true,exitDate=null where empId= " + empId);
		}
		getHibernateTemplate().flush();

	}

	@Override
	public Boolean setEndDateByEmpId(Integer empid, Date date) {
		CallableStatement stmt = null;

		try {

			stmt = getSession().connection().prepareCall("{call SETENDDATEBYEMP_ID(?,?)}");
			stmt.setInt("empid", empid);
			// date=project.getEndDate();
			// System.out.println("getCurrentDate()..."+getCurrentDate());
			stmt.setDate("date", new java.sql.Date(date.getTime()));
			stmt.execute();
			System.out.println("stmt.execute()  " + stmt.execute());

		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateEmployeeAsBench(int empId, String date) {
		getHibernateTemplate().bulkUpdate("update Employee set benchStatus=0,benchDate='"+date+"' where empId="+empId);
	}
	
	@Override
	public void updateEmployeeAsAssigned(int empId) {
		getHibernateTemplate().bulkUpdate("update Employee set benchStatus=1,benchDate=null where empId="+empId);
	}

	@Override
	public List<Employee> getEmployeesUnderManagers(int id) {
		List<Employee> list = getHibernateTemplate().find("from Employee where reportingTo=" + id + " and status=1 order by empId");
		return list;
	}
	
	@Override
	public long empCountByReporting(int id) {
		long count = ((long) getSession().createQuery("select count(*) from Employee where status =1 and reportingTo="+ id).uniqueResult());
		 System.out.println("Count........."+count);
		return count;
	}

	@Override
	public List<Employee> getInActiveEmpUnderManager(int id) {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where reportingTo=" + id +"and status=false order by empId");
		
		return list;
	}
	
	@Override
	public void updateEmployeeExitDate(int empId, String date) {
		getHibernateTemplate().bulkUpdate("update Employee set status=0,exitDate='"+date+"' where empId="+empId);
	}
	
	@Override
	 public Employee getEmployeeDataByEmployeeId(String empID) {
	  @SuppressWarnings("unchecked")
	  List<Employee> list = getHibernateTemplate().find("from Employee a where employeeId='" + empID +"'");
	  if (list != null && list.get(0) != null) {
	   return list.get(0);
	  }

	  return null;
	 }
	
	@Override
	public List<Employee> getEmpListForValidation(String employeeId) {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where employeeId=" + employeeId);
		System.out.println(list.size());
		return list;
	}
}
