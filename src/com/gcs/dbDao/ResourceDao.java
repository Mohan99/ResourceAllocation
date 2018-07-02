package com.gcs.dbDao;


import java.util.Date;
import java.util.List;

import com.gcs.bean.EmpBenchBean;
import com.gcs.bean.ResourceReportBean;
import com.gcs.bean.SalesReportBean;
import com.gcs.bean.TimeSheetReportBean;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Timesheet;

public interface ResourceDao {

	public List<Resourceallocations> getResources();
	public void saveOrUpdate(Resourceallocations resource);
	public long resourceCount();
	public List<Resourceallocations> getResourcesList();
	public List<Resourceallocations> getResourcesListByEmpId(String employeeId);
	public Resourceallocations getResourceByProjectId(String empId,String projectId);
	public  void delete(String id,boolean isResource);
	public void insert(Resourceallocations res);
	public Resourceallocations getResourceByResId(int resId);
	public void updateResource(Integer resId, Date date);
	public List<ResourceReportBean> getEmployeeReport();
	public List<ResourceReportBean> getBenchEmployeesReport();
	//public Resourceallocations getProjectEndDate(Integer id, Date date);
	//public List<Resourceallocations> getProjectEndDate(String id, Date date);
	public void updateRes(Integer projectId, Date projectTo);
	//Resourceallocations getProjectEndDate(String id, Date projectTo);
	public List<Resourceallocations> getEmpBenchPeriodReport(int empId,Date startDate,Date endDate);
	public List<EmpBenchBean> getEmpsTotalBenchPeriod(Date startDate,Date endDate);
	//Integer setResUpdate(Integer empId, Integer projId, Date date, Date endDate);
	//void UpdateInsert(Resourceallocations res);
	List<Resourceallocations> getAllEmpBenchPeriodReport(Date fromDate, Date endDate);
	//Integer setResUpdate(Integer empId, Integer projId );
	Integer setResUpdate(Integer empId, Integer projId, Date date);
	Integer checkEmpResoruceAllocation(Integer empId,Integer projectId);
	public List<Resourceallocations> checkResWithProjId(int empId,int projId);
	public List<Timesheet> getTimeSheet(Date fromDate, Date endDate);
	public List<Timesheet> getManagerTimeSheet(Date fromDate, Date endDate,int managerId);
	public List<Timesheet> getMonthlyTimeSheet(int month,int year);
	public List<Timesheet> getManagerMonthlyTimeSheet(int month,int year,int managerId);
	public void removeMultipleResource(int empId,int projId, Date date);
	public long ResCountByReporting(int id);
	public List<Resourceallocations> getResourcesListUnderManager(int mnagerId);
	public List<ResourceReportBean> getBenchEmployeesUnderManager(int managerId);
	public List<Timesheet> getManagerTimeSheetsSubmitted(Date fromDate, Date endDate);
	List<Resourceallocations> getResListUnderManager(int id);
	List<Resourceallocations> getBenchListUnderManager(int id);
	//List<SalesReportBean> getSalesSearch(int skillId);
	List<Resourceallocations> engagedResources();
	public void removeMultipleResourceBiller(int empId, int projId, Date date, String billable);
	public void delete(int employeeId,int projectId);
	List<SalesReportBean> getSalesSearch(String skillId, Double expr, String empName);
	//List<Timesheet> getempMonthlyTimeSheet(int month, int year);
	List<SalesReportBean> getempMonthlyTimeSheet(int month, int year, String skillId, Double expr);
	List<TimeSheetReportBean> getempMonthlyTimeSheet(String skillId, Double expr, int month, int year);
	List<Timesheet> getPmoManagerResourcesList();
	List<Timesheet> getAccountsTimeSheet(Date startDate, Date endDate);
	List<Timesheet> getReportingManagerList(Date startDate, Date endDate);
	List<Timesheet> getManagerMonthlyTimeSheetsSubmitted(int month, int year);
	//List<Timesheet> getReportingManagerList(Date startDate, Date endDate, String reporting);
	List<Timesheet> getAccountsReportingManagerList(Date startDate, Date endDate, int id);
	List<Timesheet> getProjectwiseTimeSheet(Date startDate, Date endDate, int projId);
	List<Timesheet> getAccountsTimeSheet(Date startDate, Date endDate, int empId);
	//long benchCount();
	long benchCount();
	List<Timesheet> getProjectwiseTimeSheet(String startDate, String endDate, int projectId);
	List<Timesheet> getAccountsTimeSheet(String startDate, String endDate, int empId);

	
}
