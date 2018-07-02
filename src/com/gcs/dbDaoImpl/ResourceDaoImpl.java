package com.gcs.dbDaoImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcs.bean.EmpBenchBean;
import com.gcs.bean.ResourceReportBean;
import com.gcs.bean.SalesReportBean;
import com.gcs.bean.TimeSheetReportBean;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Timesheet;
import com.gcs.db.businessDao.Workplace;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ResourceDao;

@Transactional
@Repository("resourceDao")
public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {
	
	
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;

	@Override
	public List<Resourceallocations> getResources() {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate().find("from Resourceallocations order by employeeId");
		return list;
	}

	@Override
	public void saveOrUpdate(Resourceallocations resource) {
		getHibernateTemplate().saveOrUpdate(resource);
		getSession().flush();
		// getSession().getTransaction().commit();

	}

	/*
	 * @Override public long resourceCount() { long count = ((long)
	 * getSession().createQuery("select count(*) from Resourceallocations").
	 * uniqueResult()); // System.out.println("Count........."+count); return count;
	 * }
	 */
	@Override
	public List<SalesReportBean> getSalesSearch(String skillId, Double expr, String empName ) {
		List<SalesReportBean> reportList = new ArrayList<>();
		;

		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		SalesReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call SALESSEARCH(?,?,?)}");
			stmt.setString("skillId", skillId);
			stmt.setDouble("expr", expr != null ? expr : 0.0);
			stmt.setString("empName", empName);
			
			System.out.println("******skillid****");
			System.out.println(skillId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				// if (rs.getString(14).equalsIgnoreCase("Bench")) {
				emp = new Employee();
				cat = new Category();
				city = new Cities();
				bean = new SalesReportBean();
				resource = new Resourceallocations();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeMobilenumber(rs.getString(3));
				bean.setSkillSet(rs.getString(4));
				emp.setEmployeeName(rs.getString(5));
				emp.setEmployeeDesg(rs.getString(6));

				cat.setCategoryName(rs.getString(7));
				city.setName(rs.getString(8));
				System.out.println("/n...." + rs.getString(11));
				System.out.println("/n...wee." + rs.getString(12));
				emp.setEmployeeCategory(cat);
				emp.setEmployeeCity(city);
				
System.out.println("setPrimaryProjects=="  +rs.getString(12));
				bean.setStatus(rs.getString(11));
				bean.setPrimaryProjects(rs.getString(12));

				if (rs.getDate(13) != null) {
					resource.setProjectFrom(rs.getDate(13));
					resource.setProjectFromStr(formatter.format(rs.getDate(13)));

				}

				bean.setSecondaryProjects(rs.getString(14));
				
				emp.setEmployeeExperience(rs.getDouble(15));
				if (rs.getDate(16) != null) {
					emp.setJoinDate(rs.getDate(16));

				}
				
				bean.setBenchDate(rs.getDate(17));
				bean.setReporting(rs.getString(20));
				bean.setMobileNumber(rs.getString(21));
				bean.setEmailId(rs.getString(22));
				
				bean.setResource(resource);
				bean.setEmp(emp);
				reportList.add(bean);
				System.out.println("bean of quer////" + reportList.get(0));

			}
			// }
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
		System.out.println("bean of query=======" + reportList.size());
		return reportList;
	}
	@Override
	public List<Resourceallocations> getResourcesList() {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETRESOURSELIST()}");
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
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));

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
	}

	@Override
	public List<Resourceallocations> getResourcesListByEmpId(String employeeId) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETRESOURCELISTBYEMPID(?)}");
			stmt.setString("empId", employeeId);
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
				if (rs.getDate(6) != null) {
					resource.setProjectFrom(rs.getDate(6));
					resource.setProjectFromStr(formatter.format(rs.getDate(6)));
					resource.setProjectTo(rs.getDate(7));
				}
				if (rs.getDate(7) != null) {
					resource.setProjectFrom(rs.getDate(6));
					resource.setProjectFromStr(formatter.format(rs.getDate(6)));
					resource.setProjectTo(rs.getDate(7));
					resource.setProjectToStr(formatter.format(rs.getDate(7)));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				resource.setAllocation(rs.getString(10));
				resource.setBillable(rs.getString(11));
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
	}

	@Override
	public Resourceallocations getResourceByProjectId(String empId, String projectId) {
		CallableStatement stmt = null;
		ResultSet rs = null;

		Resourceallocations resource = null;
		try {
			stmt = getSession().connection().prepareCall("{call GETRESOURCEBYEMP_PROJECTID(?,?)}");
			stmt.setString("empId", empId);
			stmt.setString("projectId", projectId);
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
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
					resource.setProjectFrom(rs.getDate(6));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				resource.setAllocation(rs.getString(10));
				resource.setBillable(rs.getString(11));
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
		return resource;
	}

	@Override
	public void delete(String id, boolean isResource) {
		if (isResource) {
			Resourceallocations resource = null;
			@SuppressWarnings("unchecked")
			List<Resourceallocations> list = getHibernateTemplate()
					.find("from Resourceallocations a where resId like '%" + id + "%'");
			if (list != null && list.get(0) != null)
				resource = list.get(0);
			if (resource != null)
				getHibernateTemplate().delete(resource);
		} else {
			Resourceallocations res;
			res = (Resourceallocations) getHibernateTemplate().load(Resourceallocations.class, id);
			getHibernateTemplate().delete(res);
		}
		getHibernateTemplate().flush();
	}

	@Override
	public void insert(Resourceallocations res) {
		try {

			getHibernateTemplate().saveOrUpdate(res);
			getSession().flush();

		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}
	}

	/*
	 * @Override public void UpdateInsert(Resourceallocations res) {
	 * System.out.println("insert method"); List<Resourceallocations> listResDB =
	 * getResources(); for (int j = 0; j < listResDB.size(); j++) {
	 * Resourceallocations resDB = listResDB.get(j);
	 * System.out.println("resDB"+resDB);
	 * 
	 * { try {
	 * 
	 * 
	 * String ProjComp="Y";
	 * 
	 * 
	 * 
	 * if (resDB.getProjectId()==(resDB.getProjectId())) {
	 * 
	 * if (resDB.getProjectCompleted() == ProjComp ) {
	 * getHibernateTemplate().saveOrUpdate(res); }
	 * 
	 * } } //getSession().flush(); catch (Exception e) { System.out.print(e); //
	 * getHibernateTemplate()afterPropertiesSet();.rollback(); } } }
	 * 
	 * 
	 * }
	 */

	@Override
	public Resourceallocations getResourceByResId(int resId) {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate().find("from Resourceallocations where resId=" + resId);
		return list.get(0);
	}

	public List<Resourceallocations> checkResWithProjId(int empId, int projId) {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate()
				.find("from Resourceallocations where employeeId=" + empId + "and projectId=" + projId);
		return list;
	}

	@Override
	public void updateResource(Integer resId, Date date) {
		// TODO Auto-generated method stub
		System.out.println("ResId>>>>" + resId + ">>>>>>>>date" + date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String endDate = sdf.format(date);
		int query = getHibernateTemplate().bulkUpdate("update Resourceallocations set projectTo='" + endDate
				+ "',projectCompleted='Y' where resId= " + resId);
		getHibernateTemplate().flush();

	}

	@Override
	public void updateRes(Integer Id, Date date) {
		// TODO Auto-generated method stub
		// System.out.println("projectId>>>>"+projectId+">>>>>>>>date"+date);
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		// String endDate=sdf.format(date);
		Projects projects = new Projects();
		date = projects.getEndDate();
		int query = getHibernateTemplate().bulkUpdate("update Resourceallocations set projectTo='" + date
				+ "' ,projectCompleted='Y' where projectId=" + Id + " and projectCompleted='N'");
		getHibernateTemplate().flush();

	}

	/*@Override
	public List<ResourceReportBean> getEmployeeReport() {
		List<ResourceReportBean> reportList = new ArrayList<>();
		;
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		ResourceReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call ALLEMPLOYEESREPORT()}");
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				emp = new Employee();
				cat = new Category();
				city = new Cities();
				bean = new ResourceReportBean();
				resource = new Resourceallocations();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeMobilenumber(rs.getString(3));
				emp.setEmployeeSpecialization(rs.getString(4));
				emp.setEmployeeName(rs.getString(5));
				emp.setEmployeeDesg(rs.getString(6));

				cat.setCategoryName(rs.getString(7));
				city.setName(rs.getString(8));

				emp.setEmployeeCategory(cat);
				emp.setEmployeeCity(city);

				bean.setEmp(emp);
				bean.setStatus(rs.getString(11));
				bean.setPrimaryProjects(rs.getString(12));
				if (rs.getDate(13) != null) {
					resource.setProjectFrom(rs.getDate(13));
					resource.setProjectFromStr(formatter.format(rs.getDate(13)));

				}
				bean.setResource(resource);

				bean.setSecondaryProjects(rs.getString(14));
				if (rs.getDate(15) != null) {
					emp.setJoinDate(rs.getDate(15));
					emp.setJoinDateStr(formatter.format(rs.getDate(15)));

				}
				reportList.add(bean);

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
		return reportList;
	}
*/
	@Override
	public List<ResourceReportBean> getEmployeeReport() {
		List<ResourceReportBean> reportList = new ArrayList<>();
		;
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		ResourceReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call ALLEMPLOYEESREPORT()}");
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				emp = new Employee();
				cat = new Category();
				city = new Cities();
				bean = new ResourceReportBean();
				resource = new Resourceallocations();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeMobilenumber(rs.getString(3));
				bean.setEmpSkillSet(rs.getString(4));
				//emp.setEmployeeSpecialization(rs.getString(4));
				emp.setEmployeeName(rs.getString(5));
				emp.setEmployeeDesg(rs.getString(6));

				cat.setCategoryName(rs.getString(7));
				city.setName(rs.getString(8));

				emp.setEmployeeCategory(cat);
				emp.setEmployeeCity(city);

				bean.setEmp(emp);
				bean.setStatus(rs.getString(11));
				bean.setPrimaryProjects(rs.getString(12));
				if (rs.getDate(13) != null) {
					resource.setProjectFrom(rs.getDate(13));
					resource.setProjectFromStr(formatter.format(rs.getDate(13)));

				}
				bean.setResource(resource);

				bean.setSecondaryProjects(rs.getString(14));
				if (rs.getDate(15) != null) {
					emp.setJoinDate(rs.getDate(15));
					emp.setJoinDateStr(formatter.format(rs.getDate(15)));

				}
				bean.setReportingTo(rs.getString(16));
				bean.setEmpSkillSet(rs.getString(17));
				
			
				reportList.add(bean);

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
		return reportList;
	}

	
	@Override
	public List<ResourceReportBean> getBenchEmployeesUnderManager(int managerId) {
		List<ResourceReportBean> reportList = new ArrayList<>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		Workplace place = null;
		ResourceReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call BENCH_EMPLOYEES_UNDER_MANAGER(?)}");
			stmt.setInt("managerId", managerId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				emp = new Employee();
				cat = new Category();
				city = new Cities();
				place = new Workplace();
				bean = new ResourceReportBean();
				resource = new Resourceallocations();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeMobilenumber(rs.getString(3));
				emp.setEmployeeSpecialization(rs.getString(4));
				emp.setEmployeeName(rs.getString(5));
				emp.setEmployeeDesg(rs.getString(6));

				cat.setCategoryName(rs.getString(7));
				place.setId(rs.getInt(18));
				place.setName(rs.getString(8));
				// city.setName(rs.getString(8));

				emp.setEmployeeCategory(cat);
				emp.setWorkplace(place);
				// emp.setEmployeeCity(city);

				bean.setStatus(rs.getString(11));
				bean.setPrimaryProjects(rs.getString(12));
				if (rs.getDate(13) != null) {
					resource.setProjectFrom(rs.getDate(13));
					resource.setProjectFromStr(formatter.format(rs.getDate(13)));

				}
				bean.setResource(resource);

				bean.setSecondaryProjects(rs.getString(14));
				if (rs.getDate(15) != null) {
					emp.setJoinDate(rs.getDate(15));
					emp.setJoinDateStr(formatter.format(rs.getDate(15)));

				}
				emp.setBenchDate(rs.getDate(16));
				emp.setBenchStatus(rs.getBoolean(17));

				bean.setEmp(emp);
				reportList.add(bean);

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
		return reportList;
	}

	@Override
	public List<ResourceReportBean> getBenchEmployeesReport() {
		List<ResourceReportBean> reportList = new ArrayList<>();
		;
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		ResourceReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call ALLEMPLOYEESREPORT()}");
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				if (rs.getString(11).equalsIgnoreCase("Bench")) {
					emp = new Employee();
					cat = new Category();
					city = new Cities();
					bean = new ResourceReportBean();
					resource = new Resourceallocations();
					emp.setEmpId(rs.getInt(1));
					emp.setEmployeeId(rs.getString(2));
					emp.setEmployeeMobilenumber(rs.getString(3));
					emp.setEmployeeSpecialization(rs.getString(4));
					emp.setEmployeeName(rs.getString(5));
					emp.setEmployeeDesg(rs.getString(6));

					cat.setCategoryName(rs.getString(7));
					city.setName(rs.getString(8));

					emp.setEmployeeCategory(cat);
					emp.setEmployeeCity(city);

					bean.setEmp(emp);
					bean.setStatus(rs.getString(11));
					bean.setPrimaryProjects(rs.getString(12));
					if (rs.getDate(13) != null) {
						resource.setProjectFrom(rs.getDate(13));
						resource.setProjectFromStr(formatter.format(rs.getDate(13)));

					}
					bean.setResource(resource);

					bean.setSecondaryProjects(rs.getString(14));
					if (rs.getDate(15) != null) {
						emp.setJoinDate(rs.getDate(15));
						emp.setJoinDateStr(formatter.format(rs.getDate(15)));

					}
					bean.setReportingTo(rs.getString(16));
					
					reportList.add(bean);
					

				}
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
		return reportList;
	}

	/*@Override
	public List<SalesReportBean> getSalesSearch(int skillId) {
		List<SalesReportBean> reportList = new ArrayList<>();
		;
		
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		SalesReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call SALESSEARCH(?)}");
			stmt.setInt("skillId", skillId);
			System.out.println(skillId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				if (rs.getString(14).equalsIgnoreCase("Bench")) {
					emp = new Employee();
					cat = new Category();
					city = new Cities();
					bean = new SalesReportBean();
					resource = new Resourceallocations();
					emp.setEmpId(rs.getInt(1));
					emp.setEmployeeId(rs.getString(2));
					emp.setEmployeeMobilenumber(rs.getString(3));
					emp.setEmployeeSpecialization(rs.getString(4));
					emp.setEmployeeName(rs.getString(5));
					emp.setEmployeeDesg(rs.getString(6));
					
					cat.setCategoryName(rs.getString(7));
					city.setName(rs.getString(8));
System.out.println("/n...."+rs.getString(14));
System.out.println("/n...wee."+rs.getString(15));
					emp.setEmployeeCategory(cat);
					emp.setEmployeeCity(city);
					bean.setReporting(rs.getString(9));
					bean.setMobileNumber(rs.getString(10));
					bean.setEmailId(rs.getString(11));
					
					bean.setStatus(rs.getString(14));
					bean.setPrimaryProjects(rs.getString(15));
					
					if (rs.getDate(16) != null) {
						resource.setProjectFrom(rs.getDate(16));
						resource.setProjectFromStr(formatter.format(rs.getDate(16)));

					}
					
					

					bean.setSecondaryProjects(rs.getString(17));
					if (rs.getDate(18) != null) {
						emp.setJoinDate(rs.getDate(18));
						//emp.setJoinDateStr(formatter.format(rs.getDate(17)));

					}
					emp.setEmployeeExperience(rs.getString(19));
					bean.setSkillSet(rs.getString(20));
					bean.setBenchDate(rs.getDate(21));
					
					//bean.setReporting(rs.getString(18));
					bean.setResource(resource);
					bean.setEmp(emp);
					reportList.add(bean);
					System.out.println("bean of quer////" +reportList.get(0));

				}
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
		System.out.println("bean of query=======" +reportList.size());
		return reportList;
	}
	*/
	
	/*@Override
	public List<SalesReportBean> getSalesSearch(int skillId) {
		List<SalesReportBean> reportList = new ArrayList<>();
		;
		
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		SalesReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call SALESREPORT(?)}");
			stmt.setInt("skillId", skillId);
			System.out.println(skillId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				if (rs.getString(15).equalsIgnoreCase("Bench")) {
					emp = new Employee();
					cat = new Category();
					city = new Cities();
					bean = new SalesReportBean();
					resource = new Resourceallocations();
					emp.setEmpId(rs.getInt(1));
					emp.setEmployeeId(rs.getString(2));
					emp.setEmployeeMobilenumber(rs.getString(3));
					emp.setEmployeeSpecialization(rs.getString(4));
					emp.setEmployeeName(rs.getString(5));
					emp.setEmployeeDesg(rs.getString(6));
					emp.setEmployeeExperience(rs.getString(7));

					cat.setCategoryName(rs.getString(8));
					city.setName(rs.getString(9));

					emp.setEmployeeCategory(cat);
					emp.setEmployeeCity(city);
					
					bean.setReporting(rs.getString(10));
					bean.setMobileNumber(rs.getString(11));
					bean.setEmailId(rs.getString(12));
					bean.setEmp(emp);
					bean.setStatus(rs.getString(15));
					bean.setPrimaryProjects(rs.getString(16));
					
					if (rs.getDate(16) != null) {
						resource.setProjectFrom(rs.getDate(17));
						resource.setProjectFromStr(formatter.format(rs.getDate(17)));

					}
					bean.setResource(resource);

					bean.setSecondaryProjects(rs.getString(18));
					if (rs.getDate(19) != null) {
						emp.setJoinDate(rs.getDate(19));
						//emp.setJoinDateStr(formatter.format(rs.getDate(17)));

					}
					
					
					reportList.add(bean);
					System.out.println("bean of quer////" +reportList.get(0));
	}
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
		System.out.println("bean of query=======" +reportList.size());
		return reportList;
	}
	*/
	
	@Override
	public List<Resourceallocations> getEmpBenchPeriodReport(int empId, Date startDate, Date endDate) {
		CallableStatement stmt = null;
		int first = 0;
		ResultSet rs = null;
		int found = 0;
		Resourceallocations resource = null;
		List<Resourceallocations> resList = new ArrayList<>();
		try {
			stmt = getSession().connection().prepareCall("{call GETEMPBENCHREPORT(?,?,?)}");
			stmt.setInt("empId", empId);
			stmt.setDate("fromDate", new java.sql.Date(startDate.getTime()));
			stmt.setDate("endDate", new java.sql.Date(endDate.getTime()));
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				// System.out.println("resid =" + rs.getInt(1));
				resource.setEmployeeId(rs.getInt(2));
				resource.setProjectId(rs.getInt(3));
				resource.setAllocation(rs.getString(4));
				resource.setProjectFrom(rs.getDate(5));
				// System.out.println("rs.getDate(6) =" + rs.getDate(6));
				if (rs.getDate(6) != null) {
					resource.setProjectTo(rs.getDate(6));
				}

				else {
					found++;
					// System.out.println("found =" + found);

					/*
					 * if(found==1) { System.out.println("adding the resource to list ");
					 * resList.add(resource); }
					 */
				}
				// if(found==0)
				resList.add(resource);
				first++;
			}

			// System.out.println("resList="+resList);

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
		return resList;
	}

	@Override
	public List<Resourceallocations> getAllEmpBenchPeriodReport(Date fromDate, Date endDate) {
		CallableStatement stmt = null;
		int first = 0;
		ResultSet rs = null;
		int found = 0;
		Resourceallocations resource = null;
		List<Resourceallocations> resList = new ArrayList<>();
		try {
			stmt = getSession().connection().prepareCall("{call GETALLEMPBENCHPERIOD(?,?)}");
			// stmt.setInt("empId", empId);
			stmt.setDate("fromDate", new java.sql.Date(fromDate.getTime()));
			stmt.setDate("endDate", new java.sql.Date(endDate.getTime()));
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				resource.setEmployeeId(rs.getInt(2));
				resource.setProjectId(rs.getInt(3));
				resource.setAllocation(rs.getString(4));
				resource.setProjectFrom(rs.getDate(5));
				// System.out.println("rs.getDate(6) =" + rs.getDate(6));
				if (rs.getDate(6) != null) {
					resource.setProjectTo(rs.getDate(6));
				}

				else {
					found++;

				}
				// if(found==0)
				resList.add(resource);
				first++;
			}

			// System.out.println("resList="+resList);

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
		return resList;
	}

	@Override
	public List<EmpBenchBean> getEmpsTotalBenchPeriod(Date startDate, Date endDate) {
		CallableStatement stmt = null;
		int first = 0;
		ResultSet rs = null;

		EmpBenchBean bean = null;
		List<EmpBenchBean> listBean = new ArrayList<>();
		try {
			stmt = getSession().connection().prepareCall("{call TOTAL_BENCH_PERIOD(?,?)}");
			stmt.setDate("fromDate", new java.sql.Date(startDate.getTime()));
			stmt.setDate("endDate", new java.sql.Date(endDate.getTime()));
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				int found = 0;
				bean = new EmpBenchBean();
				bean.setEmpId(rs.getInt(1));
				bean.setFromDate(rs.getDate(2));
				if (rs.getDate(3) != null) {
					bean.setToDate(rs.getDate(3));
					found++;
				}
				bean.setEmployeeId(rs.getString(4));
				bean.setEmployeeName(rs.getString(5));
				// if (found != 0)
				listBean.add(bean);

			}

			System.out.println("listBean=" + listBean);

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
		return listBean;
	}

	@Override
	public Integer setResUpdate(Integer empId, Integer projId, Date fromDate) {
		int count = 0;
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		// Resourceallocations project=null;

		try {
			resource = new Resourceallocations();
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call SETRESUPLOADBYPROJ_ID(?,?,?)}");
			stmt.setInt("empId", empId);
			stmt.setInt("projId", projId);
			stmt.setDate("date", new java.sql.Date(fromDate.getTime()));

			stmt.execute();
			System.out.println("stmt.execute()  " + stmt.execute());
			// rs = stmt.getResultSet();

			ResultSet rs1 = stmt.getResultSet();
			if (rs1.next()) {

				count = 1;
				System.out.println("in if block count =" + count);
			} else {
				count = 0;
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
		System.out.println("before returning from the daoimpl count =  " + count);

		return count;
	}

	@Override
	public Integer checkEmpResoruceAllocation(Integer empId, Integer projectId) {
		int count = 0;
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		// Resourceallocations project=null;

		try {
			resource = new Resourceallocations();
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call checkEmpResoruceAllocation(?,?)}");
			stmt.setInt("empId", empId);
			stmt.setInt("projectId", projectId);
			stmt.execute();
			System.out.println("stmt.execute()  " + stmt.execute());
			rs = stmt.getResultSet();

			// ResultSet rs1 = stmt.getResultSet();
			if (rs.next()) {
				count = 1;
				System.out.println("first check this is " + count);
			} else {
				count = 0;
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

		return count;
	}

	@Override
	public List<Timesheet> getTimeSheet(Date fromDate, Date endDate) {

		List<Timesheet> list = getHibernateTemplate()
				.find("from Timesheet where fromDate='" + fromDate + "' and toDate='" + endDate + "'");

		ListIterator<Timesheet> itr = list.listIterator();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Timesheet sheet = null;
		while (itr.hasNext()) {
			sheet = new Timesheet();
			sheet = itr.next();
			sheet.setFromDateStr(formatter.format(sheet.getFromDate()));
			sheet.setToDateStr(formatter.format(sheet.getToDate()));
		}
		return list;
	}

	@Override
	public List<Timesheet> getManagerTimeSheet(Date fromDate, Date endDate, int managerId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement(
					"select t.emp_Id, employee_id,employee_name, from_date, to_date, number_efforts, (select sum(timesheet_efforts) from Timesheet as innrtbl,projects as prj where from_date=? and to_date=? and t.emp_Id=innrtbl.emp_Id and innrtbl.project_id=prj.project_id and prj.cal_bench='Y')  as nonbillable,(select sum(billable_efforts) \r\n" + 
					"from Timesheet as innrtbl,projects as prj where from_date=? and to_date=? and t.emp_Id=innrtbl.emp_Id \r\n" + 
					"and innrtbl.project_id=prj.project_id and prj.cal_bench='Y')  as billable,(select (billable+nonbillable)) as sumEfts, (select sum(timesheet_efforts) from Timesheet as innrtbl,projects as prj where from_date=? and to_date=? and t.emp_Id=innrtbl.emp_Id and innrtbl.project_id=prj.project_id and prj.cal_bench='N') as leavehours  from Timesheet as t,Employee as e where from_date=? and to_date=? and created_emp_Id=? and t.emp_Id=e.emp_id group by t.emp_Id");
			ps.setDate(1, (java.sql.Date) fromDate);
			ps.setDate(2, (java.sql.Date) endDate);
			ps.setDate(3, (java.sql.Date) fromDate);
			ps.setDate(4, (java.sql.Date) endDate);
			ps.setDate(5, (java.sql.Date) fromDate);
			ps.setDate(6, (java.sql.Date) endDate);
			ps.setDate(7, (java.sql.Date) fromDate);
			ps.setDate(8, (java.sql.Date) endDate);
			
			
			ps.setInt(9, managerId);
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeName(rs.getString(3));
				sheet.setEmpId(emp);
				sheet.setFromDate(rs.getDate(4));
				sheet.setToDate(rs.getDate(5));
				sheet.setNumberEfforts(rs.getInt(6));
				sheet.setTimesheetEfforts(rs.getInt(7));
				sheet.setBillableEfforts(rs.getInt(8));
				sheet.setSumHours(rs.getInt(9));
				sheet.setLeaveHours(rs.getInt(10));
				sheetList.add(sheet);
			}

			ListIterator<Timesheet> itr = sheetList.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			Timesheet sheet1 = null;
			while (itr.hasNext()) {
				sheet1 = new Timesheet();
				sheet1 = itr.next();
				sheet1.setFromDateStr(formatter.format(sheet.getFromDate()));
				sheet1.setToDateStr(formatter.format(sheet.getToDate()));
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
		return sheetList;
	}
	
	@Override
	public List<Timesheet> getManagerTimeSheetsSubmitted(Date fromDate, Date endDate) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement(
					"select e.emp_id,employee_id,employee_name,employee_mobilenumber,emailId,(select count(*) from employee as innrtbl,category as c where innrtbl.reporting_To=u.emp_Id and innrtbl.employee_category=c.category_id and c.is_category='Y' ) as totalkount, (select count(DISTINCT emp_id) from timesheet as t,projects as p where t.from_date=? and t.to_date=? and t.created_emp_Id=u.emp_Id and t.project_id=p.project_id and p.cal_bench='Y' group by t.created_emp_Id) as subnittedkount from employee as e,usersemployee as u where e.emp_Id=u.emp_Id");
			ps.setDate(1, (java.sql.Date) fromDate);
			ps.setDate(2, (java.sql.Date) endDate);
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeName(rs.getString(3));
				emp.setEmployeeMobilenumber(rs.getString(4));
				emp.setEmailId(rs.getString(5));
				sheet.setEmpId(emp);
				sheet.setTotalCount(rs.getInt(6));
				sheet.setSubmittedCount(rs.getInt(7));
				sheet.setBalanceCount(rs.getInt(6)-rs.getInt(7));
				sheetList.add(sheet);
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
		return sheetList;
	}
	
	
	
	@Override
	public List<Timesheet> getPmoManagerResourcesList() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement(
					"select e.emp_Id,e.employee_id,e.employee_name,e.employee_mobilenumber,e.emailId,\r\n" + 
					"(select count(*) from employee as innrtbl,category as c where innrtbl.reporting_To=u.emp_Id and innrtbl.employee_category=c.category_id and c.is_category='Y' ) as totalcount,\r\n" + 
					"(select count(*) from Employee a where  0 < (select count(*) from Resourceallocations as res,\r\n" + 
					"Projects as proj where res.employee_id=a.emp_Id and res.project_id=proj.project_id\r\n" + 
					" and proj.cal_bench='Y' and res.project_completed='N' and res.employee_id = a.emp_id) and a.reporting_To=u.emp_Id) as resCount\r\n" + 
					"from employee as e,usersemployee as u where e.emp_Id=u.emp_Id and (e.reporting_To is not null or e.reporting_To > 0)");
			
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeName(rs.getString(3));
				emp.setEmployeeMobilenumber(rs.getString(4));
				emp.setEmailId(rs.getString(5));
				sheet.setEmpId(emp);
				sheet.setTotalCount(rs.getInt(6));
				sheet.setResCount(rs.getInt(7));
				sheet.setBenchCount(rs.getInt(6)-rs.getInt(7));
				sheetList.add(sheet);
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
		return sheetList;
	}
	
	

	@Override
	public List<Timesheet> getMonthlyTimeSheet(int month, int year) {

		List<Timesheet> list = getHibernateTemplate().find("from Timesheet where (Month(fromDate) <=" + month
				+ ") and (Month(toDate)>=" + month + ") and YEAR(fromDate)=" + year + "order by empId,fromDate");
		ListIterator<Timesheet> itr = list.listIterator();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Timesheet sheet = null;
		while (itr.hasNext()) {
			sheet = new Timesheet();
			sheet = itr.next();
			sheet.setFromDateStr(formatter.format(sheet.getFromDate()));
			sheet.setToDateStr(formatter.format(sheet.getToDate()));
		}
		return list;
	}

	/*@Override
	public List<Timesheet> getManagerMonthlyTimeSheet(int month, int year, int managerId) {

		List<Timesheet> list = getHibernateTemplate().find("from Timesheet where (Month(fromDate) <=" + month
				+ ") and (Month(toDate)>=" + month + ") and YEAR(fromDate)=" + year + " and createdEmpId=" + managerId
				+ "order by empId,fromDate");
		ListIterator<Timesheet> itr = list.listIterator();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Timesheet sheet = null;
		while (itr.hasNext()) {
			sheet = new Timesheet();
			sheet = itr.next();
			sheet.setFromDateStr(formatter.format(sheet.getFromDate()));
			sheet.setToDateStr(formatter.format(sheet.getToDate()));
		}
		return list;
	}
	
*/	
	@Override
	 public List<Timesheet> getManagerMonthlyTimeSheet(int month, int year, int managerId) {
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  List<Timesheet> sheetList = new ArrayList<>();
	  try {
	   ps = getSession().connection().prepareStatement("select t.emp_id,e.employee_id,e.employee_name,sum(t.timesheet_efforts ) as non,sum(t.billable_efforts) as bill, sum(t.timesheet_efforts + t.billable_efforts) as totalef from Timesheet t,Employee e where (Month(from_date) <=" + month + ") and (Month(to_date)>=" + month + ") and YEAR(from_date)=" + year + " and created_emp_Id=" + managerId+" and t.emp_Id=e.emp_Id group by emp_Id");
	   
	   rs = ps.executeQuery();
	   Timesheet sheet = null;

	   Employee emp = null;
	   while (rs.next()) {
	    sheet = new Timesheet();
	    emp = new Employee();
	    emp.setEmpId(rs.getInt(1));
	    emp.setEmployeeId(rs.getString(2));
	    emp.setEmployeeName(rs.getString(3));
	    sheet.setTimesheetEfforts(rs.getInt(4));
	    sheet.setBillableEfforts(rs.getInt(5));
	    sheet.setSumHours(rs.getInt(6));
	    sheet.setEmpId(emp);
	    sheetList.add(sheet);
	   }
	   System.out.println("In dao..list.."+sheetList);
	   System.out.println("In dao.."+sheetList.size());
	   
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
	  return sheetList;
	 }
	

	
	
	@Override
	public List<TimeSheetReportBean> getempMonthlyTimeSheet(String skillId, Double expr,int month, int year) {
		List<TimeSheetReportBean> reportList = new ArrayList<>();
		;

		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp = null;
		Category cat = null;
		Cities city = null;
		TimeSheetReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			stmt = getSession().connection().prepareCall("{call SALESREPORT(?,?,?,?)}");
			stmt.setString("skillId", skillId);
			stmt.setDouble("expr", expr != null ? expr : 0.0);
			stmt.setInt("month", month);
			stmt.setInt("year", year);
			
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				
				emp = new Employee();
				cat = new Category();
				city = new Cities();
				bean = new TimeSheetReportBean();
				resource = new Resourceallocations();
				//emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(1));
				emp.setEmployeeName(rs.getString(2));
				emp.setEmployeeExperience(rs.getDouble(3));
				
				bean.setSkillSet(rs.getString(4));
				bean.setTimesheetEfforts(rs.getInt(5));
				bean.setReporting(rs.getString(6));
				bean.setMobileNumber(rs.getString(7));
				bean.setEmailId(rs.getString(8));
				
				bean.setResource(resource);
				bean.setEmp(emp);
				reportList.add(bean);

			}
			// }
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
		return reportList;
	}
	
	/*@Override
	public List<SalesReportBean> getempMonthlyTimeSheet(int month, int year,String skillId, Double expr) {
		CallableStatement stmt = null;
		//ResultSet rs = null;
		//List<Timesheet> list =null;
		List<SalesReportBean> reportList = new ArrayList<>();
		;
		Employee emp = null;
		Resourceallocations resource = null;
		Category cat = null;
		Cities city = null;
		SalesReportBean bean = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			//list = new Resourceallocations();
			//list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call SALESREPORT(?,?,?,?)}");
			stmt.setString("skillId", skillId);
			stmt.setDouble("expr", expr != null ? expr : 0.0);
		//	stmt.setDouble("expr", expr);
			stmt.setInt("month", month);
			stmt.setInt("year", year);

			stmt.execute();
			System.out.println("stmt.execute()  " + stmt.execute());
			// rs = stmt.getResultSet();

			ResultSet rs = stmt.getResultSet();
		
		while (rs.next()) {
			// if (rs.getString(14).equalsIgnoreCase("Bench")) {
			emp = new Employee();
			cat = new Category();
			city = new Cities();
			bean = new SalesReportBean();
			resource = new Resourceallocations();
			//emp.setEmpId(rs.getInt(1));
			emp.setEmployeeId(rs.getString(1));
			emp.setEmployeeName(rs.getString(2));
			emp.setEmployeeExperience(rs.getDouble(3));
			bean.setSkillSet(rs.getString(4));
			bean.setTimesheetEfforts(rs.getInt(5));

			cat.setCategoryName(rs.getString(7));
			city.setName(rs.getString(8));
			System.out.println("/n...." + rs.getString(11));
			System.out.println("/n...wee." + rs.getString(12));
			emp.setEmployeeCategory(cat);
			emp.setEmployeeCity(city);
			

			bean.setStatus(rs.getString(11));
			bean.setPrimaryProjects(rs.getString(12));

			if (rs.getDate(13) != null) {
				resource.setProjectFrom(rs.getDate(13));
				resource.setProjectFromStr(formatter.format(rs.getDate(13)));

			}

			bean.setSecondaryProjects(rs.getString(14));
			
			emp.setEmployeeExperience(rs.getDouble(15));
			if (rs.getDate(16) != null) {
				emp.setJoinDate(rs.getDate(16));

			}
			
			bean.setBenchDate(rs.getDate(17));
			bean.setReporting(rs.getString(6));
			System.out.println("Reporting to ----"   + bean.getReporting());
			bean.setMobileNumber(rs.getString(7));
			bean.setEmailId(rs.getString(8));
			
			bean.setResource(resource);
			bean.setEmp(emp);
			reportList.add(bean);
			System.out.println("bean of quer////" + reportList.get(0));

		}
		// }
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
	System.out.println("bean of query=======" + reportList.size());
	return reportList;
		
	}

*/	/*@Override
	public void removeMultipleResource(int empId, int projId, Date date) {
		getHibernateTemplate()
				.bulkUpdate("update Resourceallocations set projectTo='" + new java.sql.Date(date.getTime())
						+ "',projectCompleted='Y' where employeeId=" + empId + " and projectId=" + projId);
		getHibernateTemplate().flush();

	}*/
	
	@Override
	public void removeMultipleResource(int empId, int projId, Date date) {
	getHibernateTemplate()
	.bulkUpdate("update Resourceallocations set projectTo='" + new java.sql.Date(date.getTime())
	+ "',projectCompleted='Y' where employeeId=" + empId + " and projectId=" + projId+" and projectFrom <= '" + new java.sql.Date(date.getTime())+"'");
	getHibernateTemplate().flush();

	}
	
	@Override
	public void removeMultipleResourceBiller(int empId, int projId, Date date , String billable) {
		getHibernateTemplate()
				.bulkUpdate("update Resourceallocations set billable = 'N' where employeeId=" + empId + " and projectId=" + projId);
		getHibernateTemplate().flush();

	}

	@Override
	public long ResCountByReporting(int id) {
		long count = ((long) getSession().createQuery(
				"select count(*) from Employee a where a.empId in (select res.employeeId from Resourceallocations as res,Projects as proj where res.employeeId=a.empId and res.projectId=proj.projectId and proj.calBench='Y' and res.projectCompleted='N') and a.reportingTo ="
						+ id)
				.uniqueResult());

		return count;
	}

	@Override
	public long resourceCount() {
		long count = ((long) getSession().createQuery(
				"select count(*) from Employee a where a.empId in (select employeeId from Resourceallocations where employeeId=a.empId and projectCompleted='N') ")
				.uniqueResult());
		// long count = ((long) getSession().createQuery("select count(*) from
		// Resourceallocations").uniqueResult());
		// System.out.println("Count........."+count);

		return count;
	}
	
	
	
	

	@Override
	public List<Resourceallocations> getResourcesListUnderManager(int mnagerId) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETRESOURSELISTUNDERMANAGER(?)}");
			stmt.setInt("managerId", mnagerId);
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
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));

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
	}
	
	@Override
	public List<Resourceallocations> getResListUnderManager(int id) {
		@SuppressWarnings("unchecked")
		/*List<Resourceallocations> list = getHibernateTemplate().find(
				" from Employee a where a.empId in (select employeeId from Resourceallocations where employeeId=a.empId and projectCompleted='N' ) and a.reportingTo ="
						+ id);*/
		List<Resourceallocations> list =
		getHibernateTemplate().find(" from Employee a where a.empId in (select res.employeeId from Resourceallocations as res,Projects as proj where res.employeeId=a.empId and res.projectId=proj.projectId and proj.calBench='Y' and res.projectCompleted='N') and a.reportingTo =" + id);
		
		System.out.println("list=="+list.size());
		return list;
	}

	@Override
	public List<Resourceallocations> engagedResources() {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate().find(
				" from Employee a where a.empId in (select employeeId from Resourceallocations where employeeId=a.empId and projectCompleted='N' )");
		return list;
	}

	@Override
	public List<Resourceallocations> getBenchListUnderManager(int id) {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list =
		getHibernateTemplate().find("from Employee a where a.empId not in (select res.employeeId from Resourceallocations as res,Projects as proj where res.employeeId=a.empId and res.projectId=proj.projectId and proj.calBench='Y' and res.projectCompleted='N') and a.reportingTo =" + id);
		return list;
	}

	@Override
	public void delete(int employeeId, int projectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SalesReportBean> getempMonthlyTimeSheet(int month, int year, String skillId, Double expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Timesheet> getAccountsTimeSheet(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id ");
					
			
			ps.setDate(1, new java.sql.Date(startDate.getTime()));
			ps.setDate(2, new java.sql.Date(endDate.getTime()));
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				
				sheet.setEmployeeId(rs.getString(1));
				sheet.setEmployeeName(rs.getString(2));
				sheet.setBillableProjects(rs.getString(3));
				sheet.setNumberEfforts(rs.getInt(4));
				sheet.setTimesheetEfforts(rs.getInt(5));
				sheet.setBillableEfforts(rs.getInt(6));
				
				sheetList.add(sheet); 
				
				
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
		return sheetList;
		
	}
	@Override
	public List<Timesheet> getAccountsReportingManagerList(Date startDate, Date endDate,int id) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id and e.reporting_To="+id);
					
			
			ps.setDate(1, new java.sql.Date(startDate.getTime()));
			ps.setDate(2, new java.sql.Date(endDate.getTime()));
			System.out.println(id);
			ps.setInt(3, id);
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				
				sheet.setEmployeeId(rs.getString(1));
				sheet.setEmployeeName(rs.getString(2));
				sheet.setBillableProjects(rs.getString(3));
				sheet.setNumberEfforts(rs.getInt(4));
				sheet.setTimesheetEfforts(rs.getInt(5));
				sheet.setBillableEfforts(rs.getInt(6));
				
				sheetList.add(sheet); 
				
				
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
		return sheetList;
		
	}

	
	
	@Override
	public List<Timesheet> getReportingManagerList(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Timesheet> sheetList = new ArrayList<>();
		try {
			ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id ");
					
			
			ps.setDate(1, new java.sql.Date(startDate.getTime()));
			ps.setDate(2, new java.sql.Date(endDate.getTime()));
			rs = ps.executeQuery();
			Timesheet sheet = null;

			Employee emp = null;
			while (rs.next()) {
				sheet = new Timesheet();
				emp = new Employee();
				
				sheet.setEmployeeId(rs.getString(1));
				sheet.setEmployeeName(rs.getString(2));
				sheet.setBillableProjects(rs.getString(3));
				sheet.setNumberEfforts(rs.getInt(4));
				sheet.setTimesheetEfforts(rs.getInt(5));
				sheet.setBillableEfforts(rs.getInt(6));
				
				sheetList.add(sheet); 
				
				
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
		return sheetList;
		
	}
	
	/*@Override public List<Timesheet> getAccountsTimeSheet(Date startDate, Date endDate)
	{ 
		int count = 0;
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Timesheet> sheetList = new ArrayList<>(); 
		List<Resourceallocations> resList = null; 
		Resourceallocations resource = null; 
		try { 
			resource = new Resourceallocations(); 
	//sheetList = new ArrayList<>();
	stmt = getSession().connection().prepareCall("{call ACCOUNTSUTILIZATION(?,?)}");
	stmt.setDate("startDate", new java.sql.Date(startDate.getTime()));
	stmt.setDate("endDate", new java.sql.Date(endDate.getTime()));
	stmt.execute(); System.out.println("stmt.execute() " + stmt.execute());
	//rs = stmt.getResultSet();
	Timesheet sheet = null;
	Employee emp = null;
	rs = stmt.getResultSet(); 
	while (rs.next()){ sheet = new Timesheet();
	emp = new Employee(); 
	resource = new Resourceallocations(); 
	emp.setEmpId(rs.getInt(1)); 
	emp.setEmployeeId(rs.getString(2));
	emp.setEmployeeName(rs.getString(3));
	sheet.setEmpId(emp); 
	sheet.setBillableProjects(rs.getString(7)); 
	sheet.setNonBillableProjects(rs.getString(8));
	//sheet.setNumberEfforts(rs.getInt(6)); 
	sheet.setTimesheetEfforts(rs.getInt(9));
	sheet.setBillableEfforts(rs.getInt(10));
	//sheet.setLeaveHours(rs.getInt(10)); 
	sheetList.add(sheet); 
	System.out.println("sheet list billable== "+rs.getInt(9)+"sheet list nonbillable== "+rs.getInt(10));
	} } catch (DataAccessResourceFailureException e) 
	{ // TODO Auto-generated catch block e.printStackTrace();
		
	} catch (HibernateException e) 
	{ // TODO Auto-generated catch block e.printStackTrace();
		
	} catch (IllegalStateException e) 
	{ // TODO Auto-generated catch block e.printStackTrace(); 
		
	} catch (SQLException e) 
	{ // TODO Auto-generated catch block e.printStackTrace();
		
	} return sheetList;
	}*/
	
	
	/*@Override
	 public List<Timesheet> getManagerMonthlyTimeSheetsSubmitted(int month, int year) {
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  List<Timesheet> sheetList = new ArrayList<>();
	  try {
	   ps = getSession().connection().prepareStatement(
	     "select e.emp_id,employee_id,employee_name,employee_mobilenumber,emailId,\r\n" + 
	     "(select count(*) from employee as innrtbl where innrtbl.reporting_To=u.id ) as totalkount,\r\n" + 
	     " (select count(DISTINCT emp_id) from timesheet as t,projects as p \r\n" + 
	     " where (Month(t.from_date) <=" + month + ") and (Month(t.to_date)>=" + month + ") and YEAR(t.from_date)=" + year + "  and t.created_emp_Id=u.emp_Id and t.project_id=p.project_id and p.cal_bench='Y' \r\n" + 
	     " group by t.created_emp_Id) as subnittedkount from employee as e,usersemployee as u \r\n" + 
	     "where (e.reporting_To is not null or e.reporting_To > 0) and e.emp_Id= u.emp_Id");
	   ps.setDate(1, (java.sql.Date) fromDate);
	   ps.setDate(2, (java.sql.Date) endDate);
	   rs = ps.executeQuery();
	   Timesheet sheet = null;

	   Employee emp = null;
	   while (rs.next()) {
	    sheet = new Timesheet();
	    emp = new Employee();
	    emp.setEmpId(rs.getInt(1));
	    emp.setEmployeeId(rs.getString(2));
	    emp.setEmployeeName(rs.getString(3));
	    emp.setEmployeeMobilenumber(rs.getString(4));
	    emp.setEmailId(rs.getString(5));
	    sheet.setEmpId(emp);
	    sheet.setTotalCount(rs.getInt(6));
	    sheet.setSubmittedCount(rs.getInt(7));
	    sheet.setBalanceCount(rs.getInt(6)-rs.getInt(7));
	    sheetList.add(sheet);
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
	  return sheetList;
	 }
*/
	@Override
	 public List<Timesheet> getManagerMonthlyTimeSheetsSubmitted(int month, int year) {
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  List<Timesheet> sheetList = new ArrayList<>();
	  try {
	   ps = getSession().connection().prepareStatement(
	     "select (select emp_id from employee where emp_id = u.emp_Id) empid,"
	     + "(select employee_id from employee where emp_id = u.emp_Id) employeeId,(select employee_name from employee where emp_id = u.emp_Id) employeename,(select employee_mobilenumber from employee where emp_id = u.emp_Id) employeemobilenumber,(select emailId from employee where emp_id = u.emp_Id) emailId, (select count(*) from employee as innrtbl,category as c where innrtbl.reporting_To=u.emp_Id and innrtbl.employee_category=c.category_id and c.is_category='Y') as totalkount, from_date, to_date, count(DISTINCT ts.emp_id)  as subnittedkount from usersemployee as u left outer join timesheet as ts on ts.created_emp_Id = u.id and "
	     + "(Month(ts.from_date) =" + month + ") and YEAR(ts.from_date)=" + year + "  "
	     + "where u.status = 1 group by u.id, from_date");
	   /*ps.setDate(1, (java.sql.Date) fromDate);
	   ps.setDate(2, (java.sql.Date) endDate);*/
	   rs = ps.executeQuery();
	   Timesheet sheet = null;

	   Employee emp = null;
	   while (rs.next()) {
	    sheet = new Timesheet();
	    emp = new Employee();
	    emp.setEmpId(rs.getInt(1));
	    emp.setEmployeeId(rs.getString(2));
	    emp.setEmployeeName(rs.getString(3));
	    emp.setEmployeeMobilenumber(rs.getString(4));
	    emp.setEmailId(rs.getString(5));
	    sheet.setEmpId(emp);
	    sheet.setTotalCount(rs.getInt(6));
	    sheet.setFromDate(rs.getDate(7));
	    sheet.setToDate(rs.getDate(8));
	    sheet.setSubmittedCount(rs.getInt(9));
	    sheet.setBalanceCount(rs.getInt(6)-rs.getInt(9));
	    sheetList.add(sheet);
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
	  return sheetList;
	 }

	
	
	
	 
	 @Override
	 public List<Timesheet> getAccountsTimeSheet(Date startDate, Date endDate, int empId) {
	  // TODO Auto-generated method stub
	  
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  
	  List<Timesheet> sheetList = new ArrayList<>();
	  try {
	   ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.from_date,t.to_date,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id and e.reporting_To=? order by t.from_date asc ");
	     
	   
	   ps.setDate(1, new java.sql.Date(startDate.getTime()));
	   ps.setDate(2, new java.sql.Date(endDate.getTime()));
	   ps.setInt(3, empId);
	   rs = ps.executeQuery();
	   Timesheet sheet = null;

	   //Employee emp = null;
	   while (rs.next()) {
	    sheet = new Timesheet();
	    //emp = new Employee();
	    
	    sheet.setEmployeeId(rs.getString(1));
	    sheet.setEmployeeName(rs.getString(2));
	    sheet.setBillableProjects(rs.getString(3));
	    sheet.setFromDate(rs.getDate(4));
	    sheet.setToDate(rs.getDate(5));
	    sheet.setNumberEfforts(rs.getInt(6));
	    sheet.setTimesheetEfforts(rs.getInt(7));
	    sheet.setBillableEfforts(rs.getInt(8));
	    
	    sheetList.add(sheet); 
	    
	    
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
	  return sheetList;
	  
	 }
	 
	 
	 
	 @Override
	 public List<Timesheet> getProjectwiseTimeSheet(Date startDate, Date endDate, int projectId) {
	  // TODO Auto-generated method stub
	  
	  PreparedStatement ps = null;
	  ResultSet rs = null;
	  
	  List<Timesheet> sheetList = new ArrayList<>();
	  try {
	   ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.from_date,t.to_date,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id and p.project_Id=? order by t.from_date asc");
	     
	   
	   ps.setDate(1, new java.sql.Date(startDate.getTime()));
	   ps.setDate(2, new java.sql.Date(endDate.getTime()));
	   ps.setInt(3, projectId);
	   rs = ps.executeQuery();
	   Timesheet sheet = null;

	   Employee emp = null;
	   while (rs.next()) {
	    sheet = new Timesheet();
	    emp = new Employee();
	    
	    sheet.setEmployeeId(rs.getString(1));
	    sheet.setEmployeeName(rs.getString(2));
	    sheet.setBillableProjects(rs.getString(3));
	    sheet.setFromDate(rs.getDate(4));
	    sheet.setToDate(rs.getDate(5));
	    
	    sheet.setNumberEfforts(rs.getInt(6));
	    sheet.setTimesheetEfforts(rs.getInt(7));
	    sheet.setBillableEfforts(rs.getInt(8));
	    
	    sheetList.add(sheet); 
	    
	    
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
	  return sheetList;
	  
	 }
	 
	 
	/// for benchCount
	 
	 private List<Employee> empActiveList;
		private List<Resourceallocations> resCmpList;
		@Override	
	 public long benchCount() {
	  
	   List<ResourceReportBean> resourceList = getBenchEmployeesReport();

	   try {
	    empActiveList = empDao.getActiveEmployeeList();
	    resCmpList = getResources();
	    Employee employee = null;
	    Resourceallocations resource = null;
	    ResourceReportBean bean = null;
	    for (Employee emp : empActiveList) {
	     boolean found = false;
	     for (Resourceallocations res : resCmpList) {
	      if (emp.getEmpId() == res.getEmployeeId()) {
	    	  
	       found = true;

	      }
	     }
	    
	     if (found == false && emp.getEmployeeCategory().getIsCategory().equals("Y")) {
	      employee = new Employee();
	      bean = new ResourceReportBean();
	      resource = new Resourceallocations();
	      employee.setEmpId(emp.getEmpId());
	      employee.setEmployeeId(emp.getEmployeeId());
	      employee.setEmployeeName(emp.getEmployeeName());
	      employee.setEmployeeDesg(emp.getEmployeeDesg());
	      employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
	      employee.setEmployeeCategory(emp.getEmployeeCategory());
	      employee.setEmployeeCity(emp.getEmployeeCity());
	      employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
	      employee.setReportingTo(emp.getReportingTo());

	      resource.setProjectFromStr(null);

	      SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
	      if(employee.getJoinDateStr() != null)

	      employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
	      bean.setEmp(emp);
	      bean.setResource(resource);
	      bean.setStatus("Bench");
	      resourceList.add(bean);
	     }
	     

	    // model.put("resourceList", resourceList);
	    }
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  
	     long count = resourceList.size();
	   return count;
	 }
	
		@Override
		  public List<Timesheet> getProjectwiseTimeSheet(String startDate, String endDate, int projectId) {
		   // TODO Auto-generated method stub
		   
		   PreparedStatement ps = null;
		   ResultSet rs = null;
		   
		   List<Timesheet> sheetList = new ArrayList<>();
		   try {
		    ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.from_date,t.to_date,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id and p.project_Id=? order by t.from_date asc");
		      
		    
		    ps.setString(1, startDate);
		    ps.setString(2, endDate);
		    ps.setInt(3, projectId);
		    rs = ps.executeQuery();
		    Timesheet sheet = null;

		    Employee emp = null;
		    while (rs.next()) {
		     sheet = new Timesheet();
		     emp = new Employee();
		     
		     sheet.setEmployeeId(rs.getString(1));
		     sheet.setEmployeeName(rs.getString(2));
		     sheet.setBillableProjects(rs.getString(3));
		     sheet.setFromDate(rs.getDate(4));
		     sheet.setToDate(rs.getDate(5));
		     
		     sheet.setNumberEfforts(rs.getInt(6));
		     sheet.setTimesheetEfforts(rs.getInt(7));
		     sheet.setBillableEfforts(rs.getInt(8));
		     
		     sheetList.add(sheet); 
		     
		     
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
		   return sheetList;
		   
		  }
		 @Override
		  public List<Timesheet> getAccountsTimeSheet(String startDate, String endDate, int empId) {
		   // TODO Auto-generated method stub
		   
		   PreparedStatement ps = null;
		   ResultSet rs = null;
		   
		   List<Timesheet> sheetList = new ArrayList<>();
		   try {
		    ps = getSession().connection().prepareStatement("select e.employee_Id,e.employee_name,p.project_Name,t.from_date,t.to_date,t.number_efforts,t.timesheet_efforts as nonbillable,t.billable_efforts as billable from Timesheet t,Employee e,Projects p where from_date>=? and to_date<=? and e.emp_Id=t.emp_Id and t.project_Id=p.project_Id and e.reporting_To=? order by t.from_date asc ");
		      
		    
		    ps.setString(1, startDate);
		    ps.setString(2, endDate);
		    ps.setInt(3, empId);
		    rs = ps.executeQuery();
		    Timesheet sheet = null;

		    //Employee emp = null;
		    while (rs.next()) {
		     sheet = new Timesheet();
		     //emp = new Employee();
		     
		     sheet.setEmployeeId(rs.getString(1));
		     sheet.setEmployeeName(rs.getString(2));
		     sheet.setBillableProjects(rs.getString(3));
		     sheet.setFromDate(rs.getDate(4));
		     sheet.setToDate(rs.getDate(5));
		     sheet.setNumberEfforts(rs.getInt(6));
		     sheet.setTimesheetEfforts(rs.getInt(7));
		     sheet.setBillableEfforts(rs.getInt(8));
		     
		     sheetList.add(sheet); 
		     
		     
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
		   return sheetList;
		   
		  }
}
