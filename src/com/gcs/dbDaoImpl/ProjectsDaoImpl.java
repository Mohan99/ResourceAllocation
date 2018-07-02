package com.gcs.dbDaoImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.dbDao.ProjectDao;


@Transactional
@Repository("projectDao")
public class ProjectsDaoImpl extends HibernateDaoSupport implements ProjectDao {

	@Override
	public List<Projects> getProjects() {
		@SuppressWarnings("unchecked")
		List<Projects> list =getHibernateTemplate().find("from Projects where deleteStatus = 1 and calBench='Y' "); 	
		
		
		
		ListIterator<Projects> itr1=list.listIterator();  
		Projects project = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr1.hasNext()){  
			project = new Projects();
			project = itr1.next();
			project.setStartDateStr(formatter.format(project.getStartDate()));

			}  
		return list;
	}
	public List<Projects> getClosedProjects()
	{
		List<Projects> list =getHibernateTemplate().find("from Projects where status=0 and deleteStatus = 1");
		
		ListIterator<Projects> itr=list.listIterator();  
		Projects projects = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			projects = new Projects();
			projects = itr.next();
			projects.setStartDateStr(formatter.format(projects.getStartDate()));

			}  
		
		
		ListIterator<Projects> itr1=list.listIterator();  
		Projects project = null;
		//SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr1.hasNext()){  
			projects = new Projects();
			projects = itr1.next();
			//projects.setStartDateModified1(formatter.format(projects.getStartDate()));
			if(projects.getEndDate()!=null)
			projects.setEndDateStr(formatter.format(projects.getEndDate()));

			}  
		
		
		return list;  
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Projects> getActiveProjects()
	{
		List<Projects> list =getHibernateTemplate().find("from Projects where status=1 and deleteStatus = 1"); 		
		ListIterator<Projects> itr=list.listIterator();  
		Projects projects = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			projects = new Projects();
			projects = itr.next();
			projects.setStartDateStr(formatter.format(projects.getStartDate()));

			}  
		return list;
	}
	
	@Override
	public Projects getProjectData(String projectID) {
		@SuppressWarnings("unchecked")
		List<Projects> list = getHibernateTemplate().find("from Projects where  deleteStatus = 1 and projectId=" + projectID);
		if (list != null && list.get(0) != null) {
			System.out.println("Projects By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
	}
	@Override
	public void saveOrUpdate(Projects project) {
		 getHibernateTemplate().saveOrUpdate(project);
		 getSession().flush();
		
	}
	@Override
	public Boolean setProjectEndDate(Integer id, Date date) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Projects> list = null;
		Resourceallocations resource = null;
		Projects project=null;
		try {
			project=new Projects();
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call SETPROJECTEND_DATE(?,?)}");
			stmt.setInt("id", id);
			//date=project.getEndDate();
			System.out.println("getCurrentDate()..."+getCurrentDate());
			stmt.setDate("date", new java.sql.Date(date.getTime()));
			stmt.execute();
			System.out.println("stmt.execute()  "+stmt.execute());
			
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

	/*@Override
	public List<Resourceallocations> getProjectEndDate(String id,Date date) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETPROJECTEND_DATE(?,?)}");
			stmt.setString("id", id);
			stmt.setDate(id,  new java.sql.Date(date.getTime()));
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				resource.setEmployeeName(rs.getString(2));
				resource.setEmployeeId(rs.getInt(3));
				resource.setProjectId(rs.getInt(4));
				resource.setProjectName(rs.getString(5));
				resource.setProjectFrom(rs.getDate(6));
				resource.setProjectTo(rs.getDate(7));
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
					resource.setProjectFrom(rs.getDate(6));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				resource.setAllocation(rs.getString(10));
				list.add(resource);
			}
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
		return list;
	}*/
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	@Override
	public long ActiveProjCount(int id) {
		long count = ((long) getSession().createQuery("select count(*) from Projects where status=1 and deleteStatus = 1 and projectManager.id="+ id).uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	@Override
	public long ClosedProjCount(int id) {
		long count = ((long) getSession().createQuery("select count(*) from Projects where status=0 and deleteStatus = 1 and projectManager.id="+ id).uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	@Override
	public long projCountByReporting(int id) {
		long count = ((long) getSession().createQuery("select count(*) from Projects where  deleteStatus = 1 and projectManager.id="+ id).uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	
	@Override
	public long ActiveProjectsCount() {
		long count = ((long) getSession().createQuery("select count(*) from Projects where status=1 and deleteStatus = 1 and calBench ='Y' ").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	@Override
	public long ClosedProjectsCount() {
		long count = ((long) getSession().createQuery("select count(*) from Projects where status=0 and  deleteStatus = 1 and calBench ='Y'").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Projects> getActiveProjectsUnderManager(int id)
	{
		List<Projects> list =getHibernateTemplate().find(" from Projects where status=1 and deleteStatus = 1 and projectManager.id="+ id); 		
		ListIterator<Projects> itr=list.listIterator();  
		Projects projects = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			projects = new Projects();
			projects = itr.next();
			projects.setStartDateStr(formatter.format(projects.getStartDate()));

			}  
		return list;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Projects> getCompletedProjectsUnderManager(int id)
	{
		List<Projects> list =getHibernateTemplate().find(" from Projects where status=0 and deleteStatus = 1 and projectManager.id="+ id); 		
		ListIterator<Projects> itr=list.listIterator();  
		Projects projects = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			projects = new Projects();
			projects = itr.next();
			projects.setStartDateStr(formatter.format(projects.getStartDate()));

			}  
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Projects> getTotalProjectsUnderManager(int id)
	{
		List<Projects> list =getHibernateTemplate().find(" from Projects where deleteStatus = 1 and calBench =Y and projectManager.id="+ id); 		
		ListIterator<Projects> itr=list.listIterator();  
		Projects projects = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			projects = new Projects();
			projects = itr.next();
			projects.setStartDateStr(formatter.format(projects.getStartDate()));

			}  
		return list;
	}
	
	@Override
	public void delete(Integer id, boolean isProject) {
		if (isProject) {
			
			int query = getHibernateTemplate().bulkUpdate("update Projects set deleteStatus=false where projectId= " + id);
		}
		getHibernateTemplate().flush();

	}

}
