package com.gcs.db.businessDao;
// Generated Nov 7, 2017 5:49:38 PM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Projects generated by hbm2java
 */
@SuppressWarnings("serial")
public class Projects implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int projectId;
	private String projectName;
	private Date startDate;
	private Date endDate;
	private String startDateStr;
	private String endDateStr;
	private Employee projectManager;
	private String calBench;
	private boolean status;
	private boolean deleteStatus;
	private String projectType;
	
	
	@SuppressWarnings("rawtypes")
	private Set resourceallocationses = new HashSet(0);

	public Projects() {
	}

	public Projects(String projectName, boolean status) {
		this.projectName = projectName;
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Projects(String projectName, Date startDate, Date endDate, boolean status,boolean deleteStatus,String projectType , Set resourceallocationses) {
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.resourceallocationses = resourceallocationses;
		this.projectManager = projectManager;
		this.deleteStatus = deleteStatus;
		this.deleteStatus = deleteStatus;
		
	}

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return this.projectName;
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

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
	public String getCalBench() {
		return calBench;
	}

	public void setCalBench(String calBench) {
		this.calBench = calBench;
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

	public void setProjectManager(Employee employee) {
		this.projectManager = employee;
	}

	@SuppressWarnings("rawtypes")
	public Set getResourceallocationses() {
		return this.resourceallocationses;
	}

	@SuppressWarnings("rawtypes")
	public void setResourceallocationses(Set resourceallocationses) {
		this.resourceallocationses = resourceallocationses;
	}

	@Override
	public String toString() {
		return "Projects [projectId=" + projectId + ", projectName=" + projectName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startDateStr=" + startDateStr + ", endDateStr=" + endDateStr
				+ ", projectManager=" + projectManager + ", calBench=" + calBench + ", status=" + status
				+ ", deleteStatus=" + deleteStatus + ", projectType=" + projectType + ", resourceallocationses="
				+ resourceallocationses + "]";
	}

	

}