package com.gcs.dbDaoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Timesheet;
import com.gcs.dbDao.TimeSheetDao;

@Transactional
@Repository("timeSheetDao")
public class TimeSheetDaoImpl extends HibernateDaoSupport implements TimeSheetDao {

	@Override
	public List<Projects> getProjectsByEmpId(Integer empId) {
		List<Projects> list = getHibernateTemplate()
				.find("from Projects where projectId in(select projectId from Resourceallocations  where employeeId="
						+ empId + ")");
		return list;
	}

	@Override
	public List<Employee> getResourcesByProjId(Integer projId, Integer empId) {
		List<Employee> list = getHibernateTemplate()
				.find("from Employee where empId in(select employeeId from Resourceallocations  where projectId="
						+ projId + ") and empId!=" + empId+" and empId not in(select empId from Timesheet)");
		return list;
	}

	@Override
	public Integer getActualEfforts(Date startDate, Date endDate) {
		java.sql.Date sDate = new java.sql.Date(startDate.getTime());
		java.sql.Date eDate = new java.sql.Date(endDate.getTime());
		int holidays = getHibernateTemplate()
				.find("from Holidaycalender where (date BETWEEN '" + sDate + "' and '" + eDate + "')").size();
		return holidays;
	}

	@Override
	public void saveOrUpdate(Timesheet sheet) {
		getHibernateTemplate().saveOrUpdate(sheet);
		getSession().flush();
	}

	@Override
	public List<Timesheet> getActualEffortsList(int empId) {
	
		
		List<Timesheet> list = getHibernateTemplate().find("from Timesheet where createdEmpId="+ empId);
		ListIterator<Timesheet> itr = list.listIterator();
		Timesheet sheet = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while (itr.hasNext()) {
			sheet = new Timesheet();
			sheet = itr.next();
			sheet.setFromDateStr(formatter.format(sheet.getFromDate()));
			sheet.setToDateStr(formatter.format(sheet.getToDate()));
		}
		return list;
	}

	@Override
	public Timesheet getActualEffortsById(int id) {
		@SuppressWarnings("unchecked")
		List<Timesheet> list = getHibernateTemplate().find("from Timesheet where id=" + id);
		if (list != null && list.get(0) != null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateEfforts(int eftId, int utilEfforts) {
		getHibernateTemplate().bulkUpdate("update Timesheet set timesheetEfforts=" + utilEfforts + " where id=" + eftId);
		getHibernateTemplate().flush();
	}
	
	@Override
	public void updateEfforts(int eftId, int utilEfforts , int billableEfforts) {
		getHibernateTemplate().bulkUpdate("update Timesheet set timesheetEfforts=" + utilEfforts + ", billableEfforts=" +billableEfforts + " where id=" + eftId);
		getHibernateTemplate().flush();
	}
	
	@Override
	public List<Resourceallocations> getResourcesListByManager(int managerId) {
		List<Resourceallocations> list = getHibernateTemplate()
				.find("from Resourceallocations r where r.employeeId in(select e.empId from Employee e where e.reportingTo="+managerId+")");
		return list;
	}
	
	@Override
	public List<Resourceallocations> getResourcesListByManagerByEmployee(int managerId,int empId,String fromDate,String toDate) {
		List<Resourceallocations> list = getHibernateTemplate()
				.find("from Resourceallocations r where r.employeeId in(select e.empId from Employee e where e.reportingTo="+managerId+") and r.projectId not in (select projectId from Timesheet t where r.employeeId=t.empId and t.fromDate='"+fromDate+"' and t.toDate='"+toDate+"') and r.projectCompleted='N' and r.employeeId="+empId);
		return list;
	}
	
	
	@Override
	public List<Resourceallocations> getProjectListByManagerByEmployee(int managerId,int projectId,String fromDate,String toDate) {
		List<Resourceallocations> list = getHibernateTemplate()
				.find("from Resourceallocations r where r.projectId="+projectId +" and r.projectId " + 
						" not in (select projectId from Timesheet t where r.employeeId=t.empId and " + 
						" r.projectId=t.projectId and t.fromDate='"+fromDate +"' and t.toDate='"+ toDate+"') " + 
						" and  r.employeeId in(select e.empId from Employee e where e.reportingTo="+managerId +") and r.projectCompleted='N'" );
		
          System.out.println("RES LIST=== "+list);
          System.out.println("reslist size in daoimpl=====" + list.size());
		return list;
	}

}
