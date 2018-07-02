package com.gcs.controller;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Users;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.responseDao.EmployeeResponseReport;

import org.springframework.context.annotation.ScopedProxyMode;

@Component("sessionObj")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	
	private boolean isValidLogin;
	private String statusCode;
	private String statusMessage;
	
	private String LoginUserName;
	private Employee empObj;
	private Users userObj;
	
	private String UserName;
	private String empId;
	private String projectId;
	private Date date;
	private String emialCallPath;
	
	private String oldDate;
	private String currentDate;
	
	private boolean isDataDeleted;
	private long employeeCount;
	private long projectsCount;
	private long resourceCount;
	private long benchCount;
	private long usersCount;
	private long skillSetCount;
	
	
	private int tsProjId;
	private int tsTotalEfforts;
	private String tsFromDate;
	private String tsToDate;
	private String startDateStr;
	private String endDateStr;
	
	
	

	private List<EmployeeResponseReport> responseReport;
	private String reportFrom;
	private boolean isTableDataEmpty;
	private String toEmail;
	private String mailSubject="Last week attendance report";
	private String mailMessage="Hi, Please find the enclosed Gemini Attendance Analysis Report of your team for february ";
	private Usersemployee empUsersObj;
	
	private long activeProjCount;
	private long closedProjCount;
	private long resCountByReporting;
	private long projCountByReporting;
	private long closedProjectsCount;
	private long activeProjectsCount;
	private long empCountByReporting;
	
	private Projects projectObj;
	
	
	public Projects getProjectObj() {
		return projectObj;
	}

	public void setProjectObj(Projects projectObj) {
		this.projectObj = projectObj;
	}

	public int getTsProjId() {
		return tsProjId;
	}

	public void setTsProjId(int tsProjId) {
		this.tsProjId = tsProjId;
	}

	public String getTsFromDate() {
		return tsFromDate;
	}

	public int getTsTotalEfforts() {
		return tsTotalEfforts;
	}

	public void setTsTotalEfforts(int tsTotalEfforts) {
		this.tsTotalEfforts = tsTotalEfforts;
	}

	public void setTsFromDate(String tsFromDate) {
		this.tsFromDate = tsFromDate;
	}

	public String getTsToDate() {
		return tsToDate;
	}

	public void setTsToDate(String tsToDate) {
		this.tsToDate = tsToDate;
	}
	
	
	

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Usersemployee getEmpUsersObj() {
		return empUsersObj;
	}

	public void setEmpUsersObj(Usersemployee empUsersObj) {
		this.empUsersObj = empUsersObj;
	}

	public long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public boolean getIsValidLogin() {
		return isValidLogin;
	}

	public void setValidLogin(boolean isValidLogin) {
		this.isValidLogin = isValidLogin;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getLoginUserName() {
		return LoginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		LoginUserName = loginUserName;
	}

	public long getActiveProjCount() {
		return activeProjCount;
	}

	public void setActiveProjCount(long activeProjCount) {
		this.activeProjCount = activeProjCount;
	}

	public long getClosedProjCount() {
		return closedProjCount;
	}

	public void setClosedProjCount(long closedProjCount) {
		this.closedProjCount = closedProjCount;
	}

	
	
	
	public long getSkillSetCount() {
		return skillSetCount;
	}

	public void setSkillSetCount(long skillSetCount) {
		this.skillSetCount = skillSetCount;
	}

	public long getResCountByReporting() {
		return resCountByReporting;
	}

	public void setResCountByReporting(long resCountByReporting) {
		this.resCountByReporting = resCountByReporting;
	}

	public long getProjCountByReporting() {
		return projCountByReporting;
	}

	public void setProjCountByReporting(long projCountByReporting) {
		this.projCountByReporting = projCountByReporting;
	}

	public long getClosedProjectsCount() {
		return closedProjectsCount;
	}

	public void setClosedProjectsCount(long closedProjectsCount) {
		this.closedProjectsCount = closedProjectsCount;
	}

	public long getActiveProjectsCount() {
		return activeProjectsCount;
	}

	public void setActiveProjectsCount(long activeProjectsCount) {
		this.activeProjectsCount = activeProjectsCount;
	}

	public long getEmpCountByReporting() {
		return empCountByReporting;
	}

	public void setEmpCountByReporting(long empCountByReporting) {
		this.empCountByReporting = empCountByReporting;
	}

	public Employee getEmpObj() {
		return empObj;
	}

	public void setEmpObj(Employee empObj) {
		this.empObj = empObj;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}


	public Users getUserObj() {
		return userObj;
	}

	public void setUserObj(Users userObj) {
		this.userObj = userObj;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getEmialCallPath() {
		return emialCallPath;
	}

	public void setEmialCallPath(String emialCallPath) {
		this.emialCallPath = emialCallPath;
	}

	public List<EmployeeResponseReport> getResponseReport() {
		return responseReport;
	}

	public void setResponseReport(List<EmployeeResponseReport> responseReport) {
		this.responseReport = responseReport;
	}

	public String getReportFrom() {
		return reportFrom;
	}

	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}

	public String getOldDate() {
		return oldDate;
	}

	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public boolean isDataDeleted() {
		return isDataDeleted;
	}

	public void setDataDeleted(boolean isDataDeleted) {
		this.isDataDeleted = isDataDeleted;
	}

	public boolean isTableDataEmpty() {
		return isTableDataEmpty;
	}

	public void setTableDataEmpty(boolean isTableDataEmpty) {
		this.isTableDataEmpty = isTableDataEmpty;
	}

	public long getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(long projectsCount) {
		this.projectsCount = projectsCount;
	}

	public long getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(long resourceCount) {
		this.resourceCount = resourceCount;
	}

	
	
	
	public long getBenchCount() {
		return benchCount;
	}

	public void setBenchCount(long benchCount) {
		this.benchCount = benchCount;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
