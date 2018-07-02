package com.gcs.bean;

import java.util.Date;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;


public class TimeSheetReportBean {
	private Employee emp;
	private Projects project;
	private Resourceallocations resource;
	private int numberEfforts;
	private int estimatedEfforts;
	private int timesheetEfforts;
	private int diffEfforts;
	private Date fromDate;
	private Date toDate;
	private String reporting;
	private String emailId;
	private String mobileNumber;
	private String skillSet;
	private Date benchDate;
	private String fromDateStr;
	private String toDateStr;
	private int estimatedUtilization;
	private int actualUtilization;
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
	public int getDiffEfforts() {
		return diffEfforts;
	}
	public void setDiffEfforts(int diffEfforts) {
		this.diffEfforts = diffEfforts;
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
	public String getReporting() {
		return reporting;
	}
	public void setReporting(String reporting) {
		this.reporting = reporting;
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
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}
	public Date getBenchDate() {
		return benchDate;
	}
	public void setBenchDate(Date benchDate) {
		this.benchDate = benchDate;
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
	public int getEstimatedUtilization() {
		return estimatedUtilization;
	}
	public void setEstimatedUtilization(int estimatedUtilization) {
		this.estimatedUtilization = estimatedUtilization;
	}
	public int getActualUtilization() {
		return actualUtilization;
	}
	public void setActualUtilization(int actualUtilization) {
		this.actualUtilization = actualUtilization;
	}
	
	
	

}
