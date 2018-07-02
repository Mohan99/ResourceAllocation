package com.gcs.bean;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;

public class ResourceReportBean {
	private Employee emp;
	private Projects project;
	private Resourceallocations resource;
	private String primaryProjects;
	private String secondaryProjects;
	private String status;
	private String reportingTo;
	private String empSkillSet;
	
	
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
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public String getEmpSkillSet() {
		return empSkillSet;
	}
	public void setEmpSkillSet(String empSkillSet) {
		this.empSkillSet = empSkillSet;
	}
	
	

}
