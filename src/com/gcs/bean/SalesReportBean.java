package com.gcs.bean;

import java.util.Date;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Usersemployee;

public class SalesReportBean {
	
	private Employee emp;
	private Projects project;
	private Resourceallocations resource;
	private String primaryProjects;
	private String secondaryProjects;
	private String status;
	private Usersemployee userEmp;
	private String reporting;
	private String emailId;
	private String mobileNumber;
	private int timesheetEfforts;
	private Date fromDate;
	private Date toDate;
	private String skillSet;
	private Date benchDate;
	private int numberEfforts;
	private String fromDateStr;
	private String toDateStr;
	private int actualUtilization;
	
	
	
	public int getActualUtilization() {
		return actualUtilization;
	}
	public void setActualUtilization(int actualUtilization) {
		this.actualUtilization = actualUtilization;
	}
	public String getFromDateStr() {
		return fromDateStr;
	}
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}
	public String getToDateStr() {
		return toDateStr;
	}
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}
	public int getNumberEfforts() {
		return numberEfforts;
	}
	public void setNumberEfforts(int numberEfforts) {
		this.numberEfforts = numberEfforts;
	}
	public int getTimesheetEfforts() {
		return timesheetEfforts;
	}
	public void setTimesheetEfforts(int timesheetEfforts) {
		this.timesheetEfforts = timesheetEfforts;
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
	public Date getBenchDate() {
		return benchDate;
	}
	public void setBenchDate(Date benchDate) {
		this.benchDate = benchDate;
	}
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getReporting() {
		return reporting;
	}
	public void setReporting(String reporting) {
		this.reporting = reporting;
	}
	public Usersemployee getUserEmp() {
		return userEmp;
	}
	public void setUserEmp(Usersemployee userEmp) {
		this.userEmp = userEmp;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Projects getProject() {
		return project;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public Resourceallocations getResource() {
		return resource;
	}
	public void setResource(Resourceallocations resource) {
		this.resource = resource;
	}
	public String getPrimaryProjects() {
		return primaryProjects;
	}
	public void setPrimaryProjects(String primaryProjects) {
		this.primaryProjects = primaryProjects;
	}
	public String getSecondaryProjects() {
		return secondaryProjects;
	}
	public void setSecondaryProjects(String secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
