package com.gcs.requestDao;

import java.util.Date;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;

public class TimesheetRequest {

	private int id;
	private Employee empId;
	private Projects projectId;
	private Date fromDate;
	private Date toDate;
	private int numberEfforts;
	private int estimatedEfforts;
	private int timesheetEfforts;
	private int billableEfforts;
	private int diffEfforts;
	private Date createdDate;
	private int createdEmpId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Employee getEmpId() {
		return empId;
	}
	public void setEmpId(Employee empId) {
		this.empId = empId;
	}
	public Projects getProjectId() {
		return projectId;
	}
	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getNumberEfforts() {
		return numberEfforts;
	}
	public void setNumberEfforts(int numberEfforts) {
		this.numberEfforts = numberEfforts;
	}
	public int getEstimatedEfforts() {
		return estimatedEfforts;
	}
	public void setEstimatedEfforts(int estimatedEfforts) {
		this.estimatedEfforts = estimatedEfforts;
	}
	public int getTimesheetEfforts() {
		return timesheetEfforts;
	}
	public void setTimesheetEfforts(int timesheetEfforts) {
		this.timesheetEfforts = timesheetEfforts;
	}
	
	
	public int getBillableEfforts() {
		return billableEfforts;
	}
	public void setBillableEfforts(int billableEfforts) {
		this.billableEfforts = billableEfforts;
	}
	public int getDiffEfforts() {
		return diffEfforts;
	}
	public void setDiffEfforts(int diffEfforts) {
		this.diffEfforts = diffEfforts;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedEmpId() {
		return createdEmpId;
	}
	public void setCreatedEmpId(int createdEmpId) {
		this.createdEmpId = createdEmpId;
	}
}
