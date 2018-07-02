package com.gcs.dbDaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Users;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.dbDao.UsersDao;

import com.gcs.responseDao.Response;

@Repository("usersDao")
@Transactional
public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

	@Transactional
	public Response validateLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "'");
		Response response = new Response();
		if (list.size() > 0) {
			System.out.println("User Exists::" + list);
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			System.out.println("User doesn't Exists::" + list);
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}

		return response;

	}

	@Override
	public void createUsers(Users u) {
		// TODO Auto-generated method stub

	}

	@Override
	public Response validateUsers(String userName, Integer userId, String loginName, String loginPassword, String email,
			boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Users userObj) {
		getHibernateTemplate().saveOrUpdate(userObj);
		getSession().flush();
		// TODO Auto-generated method stub

	}
	
	@Override
	public void createLoggingEmployee(Usersemployee empUser) {
		getHibernateTemplate().saveOrUpdate(empUser);
		getSession().flush();
		// TODO Auto-generated method stub

	}
	@Override
	public void updateManagerStatus(Integer id, boolean isEmployee) {
		if (isEmployee) {
			
			int query = getHibernateTemplate().bulkUpdate("update Employee set managerStatus=1 where empId= " + id);
		}
		getHibernateTemplate().flush();

	}
	
	
	/*@Override
	public void updateManagerStatus(Integer id, int isEmployee) {
		if (isEmployee==1) {
			
			int query = getHibernateTemplate().bulkUpdate("update Employee set managerStatus=1 where empId= " + id);
		}
		getHibernateTemplate().flush();

	}*/


	@Override
	public List<Users> getListUsers() {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users where isActive=true");
		return usersList;
	}
	
	@Override
	public List<Employee> getListEmpUsers(){
		@SuppressWarnings("unchecked")
		List<Employee> usersList = getHibernateTemplate().find("from Employee where empId in(select e.reportingTo from Employee e) or managerStatus=1 ");
	
		return usersList;
	}
	
	@Override
	public int checkListEmpUsers(){
		@SuppressWarnings("unchecked")
		List<Employee> usersList = getHibernateTemplate().find("from Employee where empId in(select e.reportingTo from Employee e) ");
		System.out.println("usersList== "+usersList.size());
		return usersList.size();
	}

	@Override
	public Users getUsersData(Integer userId) {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users a where userId like '%" + userId + "%'");
		if (usersList != null && usersList.get(0) != null)
			return usersList.get(0);

		return null;
	}

	@Override
	public void delete(Integer userId, boolean isUser) {
		if (isUser) {
			
			int query =  getHibernateTemplate().bulkUpdate("update Users set isActive=false where userId= "+userId);
			
		}
	}

	@Override
	public void insert(List<Users> usersList) {
		try {
			for (int i = 0; i < usersList.size(); i++) {
				Users users = usersList.get(i);
				getHibernateTemplate().saveOrUpdate(users);
				getSession().flush();
			}

			// getSession().flush();
		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}

	}

	@Override
	public long usersCount() {
		long count = ((long) getSession().createQuery("select count(*) from Users").uniqueResult());

		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void removeUser(Integer userId) {
		
		@SuppressWarnings("unused")
		List<Users> list = this.getHibernateTemplate().find("from Users a where userId like '%\"+userId+\"%'");
		Users users = (Users) getHibernateTemplate().load(Users.class, new Integer(userId));
		if (null != users) {
			getHibernateTemplate().delete(users);
		}
		
	}

	@Override
	public String userName() {
		return null;
		// TODO Auto-generated method stub

	}

	
	@Override
	public Users getUserData(String email) {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users a where email like '%" + email + "%'");
		if (usersList != null && !usersList.isEmpty() && usersList.get(0) != null) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public Employee getEmpUserData(String empId) {
		@SuppressWarnings("unchecked")
		List<Employee> usersList = getHibernateTemplate().find("from Employee where empId=(select empId from Employee where employeeId='"+empId+"')");
		if (usersList != null) {
			return usersList.get(0);
		}
		return usersList.get(0);
	}
	
	@Override
	public void removeUserEmp(Integer id, int status) {
		if (status==1) {
			System.out.println("id===" +id);
			int query =  getHibernateTemplate().bulkUpdate("update Usersemployee set status=0 where id= "+id);
			int query1 =  getHibernateTemplate().bulkUpdate("update Employee set managerStatus=0 where id= "+id);
			
		}		
		
	}
	
	@Override
	public void updateEmployeeLoginUsers(int empId,String password) {
		getHibernateTemplate().bulkUpdate("update Usersemployee set password= "+password+" where emp_Id="+ empId);
		
	}

	
}
