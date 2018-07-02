package com.gcs.dbDao;

import java.util.Date;
import java.util.List;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Timesheet;

public interface TimeSheetDao {

	public List<Projects> getProjectsByEmpId(Integer empId);

	public List<Employee> getResourcesByProjId(Integer projId, Integer empId);

	public Integer getActualEfforts(Date startDate, Date endDate);

	public void saveOrUpdate(Timesheet sheet);
	public List<Timesheet> getActualEffortsList(int empId);
	public Timesheet getActualEffortsById(int id);
	public void updateEfforts(int eftId,int utilEfforts);
	public List<Resourceallocations> getResourcesListByManager(int managerId);
	public List<Resourceallocations> getResourcesListByManagerByEmployee(int managerId,int empId,String fromDate,String toDate);

	void updateEfforts(int eftId, int utilEfforts, int billableEfforts);

	List<Resourceallocations> getProjectListByManagerByEmployee(int managerId, int projectId, String fromDate,
			String toDate);
}
