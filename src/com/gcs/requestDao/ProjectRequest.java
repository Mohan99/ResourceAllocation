package com.gcs.requestDao;

import java.util.Date;

import com.gcs.db.businessDao.Employee;


public class ProjectRequest {

	private Integer projectId;
	private String projectName;
	private Date startDate;
	private Date endDate;
	private String startDateStr;
	private String endDateStr;
	private Employee projectManager;
	private boolean status;
	private String calBench;
	private boolean deleteStatus;
	private String projectType;
	
	public String getCalBench() {
		return calBench;
	}
	public void setCalBench(String calBench) {
		this.calBench = calBench;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	public boolean isDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Employee getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}
	
	
	/*public Usersemployee getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(Usersemployee projectManager) {
		this.projectManager = projectManager;
	}*/
	/*@Override
	public String toString() {
		return "ProjectRequest [projectId=" + projectId + ", projectName=" + projectName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startDateStr=" + startDateStr + ", endDateStr=" + endDateStr
				+ ", projectManager=" + projectManager + ", status=" + status + ", calBench=" + calBench
				+ ", deleteStatus=" + deleteStatus + ", projectType=" + projectType + "]";
	}*/
	
	
}
