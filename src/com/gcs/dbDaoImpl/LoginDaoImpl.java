package com.gcs.dbDaoImpl;

import java.util.List;

import org.hibernate.Query;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Users;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.dbDao.LoginDao;
import com.gcs.responseDao.Response;

@Repository("loginDao")
@Transactional
public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

	/*
	 * private SessionFactory sessionFactory;
	 * 
	 * public void setSessionFactory(SessionFactory sf) { this.sessionFactory =
	 * sf; }
	 */

	@Override
	public Response validateLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "' and role='a'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}
	
	@Override
	public Response validatePmoLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "' and role='p'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}
	
	/*@Override
	public Response validateAccountsLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "' and role='Ac'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}*/
	@Override
	public Response validateAccountsLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "' and role='Ac'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}
	
	@Override
	public Response validateSalesLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "' and role='s'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}
	
	@Override
	public Response validateEmployeeLogin(String empId, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Usersemployee> list = getHibernateTemplate()
				.find("from Usersemployee where empId=(select empId from Employee where employeeId='"+empId+"') and password='"+password+"'");
		Response response = new Response();
		if (list.size() > 0) {
			
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public Response changePassword(Integer userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		List<Users> list =getHibernateTemplate().find("from Users a where userId = '"+userId+"' and loginPassword='"+oldPassword+"'"); 
		Response response=new Response();
		if(list!=null&& list.size()>0)
		{
			Query q = getSession().createQuery("update Users set loginPassword=:password where userId=:Id");
			q.setString("password", newPassword);
			q.setInteger("Id", userId);
			int x=q.executeUpdate();
			if(x==-1) {
				response.setStatusCode(ConstantVariables.SC1);
				response.setStatusMessage("password not updated");
			}else {
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);
			}
			
		}else {
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage("old password not matched");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response changeEmpPassword(Integer userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		List<Users> list =getHibernateTemplate().find("from Usersemployee a where empId = '"+userId+"' and password='"+oldPassword+"'"); 
		Response response=new Response();
		if(list!=null&& list.size()>0)
		{
			Query q = getSession().createQuery("update Usersemployee set password=:password where empId=:Id");
			q.setString("password", newPassword);
			q.setInteger("Id", userId);
			int x=q.executeUpdate();
			if(x==-1) {
				response.setStatusCode(ConstantVariables.SC1);
				response.setStatusMessage("password not updated");
			}else {
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);
			}
			
		}else {
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage("old password not matched");
		}
		return response;
	}

}
