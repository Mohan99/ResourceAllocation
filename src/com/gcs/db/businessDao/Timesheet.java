package com.gcs.db.businessDao;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")

public class Timesheet {

	@Id
	@GeneratedValue
	private int id;
	private Employee empId;
	private String employeeId;
	private String employeeName;
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
	private String fromDateStr;
	private String toDateStr;
	private int estimatedUtilization;
	private int actualUtilization;
	private String reporting;
	private String emailId;
	private String mobileNumber;
	private String skillSet;
	private int leaveHours;
	private int sumHours;
	
	private int totalCount;
	private int submittedCount;
	private int balanceCount;
	private int resCount;
	private int benchCount;
	private String billableProjects;
	private String nonBillableProjects;
	private int projId;
	private String ProjType;
	
	
	
	public String getProjType() {
		return ProjType;
	}
	public void setProjType(String projType) {
		ProjType = projType;
	}
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public String getBillableProjects() {
		return billableProjects;
	}
	public void setBillableProjects(String billableProjects) {
		this.billableProjects = billableProjects;
	}
	public String getNonBillableProjects() {
		return nonBillableProjects;
	}
	public void setNonBillableProjects(String nonBillableProjects) {
		this.nonBillableProjects = nonBillableProjects;
	}
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
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
	public int getLeaveHours() {
		return leaveHours;
	}
	public void setLeaveHours(int leaveHours) {
		this.leaveHours = leaveHours;
	}
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
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSubmittedCount() {
		return submittedCount;
	}
	public void setSubmittedCount(int submittedCount) {
		this.submittedCount = submittedCount;
	}
	public int getBalanceCount() {
		return balanceCount;
	}
	public void setBalanceCount(int balanceCount) {
		this.balanceCount = balanceCount;
	}
	
	
	
	
	public int getResCount() {
		return resCount;
	}
	public void setResCount(int resCount) {
		this.resCount = resCount;
	}
	public int getBenchCount() {
		return benchCount;
	}
	public void setBenchCount(int benchCount) {
		this.benchCount = benchCount;
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
	
	
	public int getSumHours() {
		return sumHours;
	}
	public void setSumHours(int sumHours) {
		this.sumHours = sumHours;
	}
	
	
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	/*@Override
	public String toString() {
		return "Timesheet [id=" + id + ", empId=" + empId + ", projectId=" + projectId + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", numberEfforts=" + numberEfforts + ", estimatedEfforts=" + estimatedEfforts
				+ ", timesheetEfforts=" + timesheetEfforts + ", billableEfforts=" + billableEfforts + ", diffEfforts="
				+ diffEfforts + ", createdDate=" + createdDate + ", createdEmpId=" + createdEmpId + ", fromDateStr="
				+ fromDateStr + ", toDateStr=" + toDateStr + ", estimatedUtilization=" + estimatedUtilization
				+ ", actualUtilization=" + actualUtilization + ", reporting=" + reporting + ", emailId=" + emailId
				+ ", mobileNumber=" + mobileNumber + ", skillSet=" + skillSet + ", leaveHours=" + leaveHours
				+ ", sumHours=" + sumHours + ", totalCount=" + totalCount + ", submittedCount=" + submittedCount
				+ ", balanceCount=" + balanceCount + ", resCount=" + resCount + ", benchCount=" + benchCount + "]";
	}*/
	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", empId=" + empId + ", projectId=" + projectId + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", numberEfforts=" + numberEfforts + ", estimatedEfforts=" + estimatedEfforts
				+ ", timesheetEfforts=" + timesheetEfforts + ", billableEfforts=" + billableEfforts + ", diffEfforts="
				+ diffEfforts + ", createdDate=" + createdDate + ", createdEmpId=" + createdEmpId + ", fromDateStr="
				+ fromDateStr + ", toDateStr=" + toDateStr + ", estimatedUtilization=" + estimatedUtilization
				+ ", actualUtilization=" + actualUtilization + ", reporting=" + reporting + ", emailId=" + emailId
				+ ", mobileNumber=" + mobileNumber + ", skillSet=" + skillSet + ", leaveHours=" + leaveHours
				+ ", sumHours=" + sumHours + ", totalCount=" + totalCount + ", submittedCount=" + submittedCount
				+ ", balanceCount=" + balanceCount + ", resCount=" + resCount + ", benchCount=" + benchCount
				+ ", billableProjects=" + billableProjects + ", nonBillableProjects=" + nonBillableProjects + "]";
	}
	
}
