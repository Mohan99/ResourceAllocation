package com.gcs.db.businessDao;
// Generated Nov 7, 2017 5:49:38 PM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Employee generated by hbm2java
 */
@SuppressWarnings("serial")
public class Employee implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int empId;
	private Category employeeCategory;
	private Cities employeeCity;
	private Workplace workplace;
	private Countries employeeCountry;
	private State employeeState;
	private String employeeId;
	private String employeeName;
	private String employeeDesg;
	private String employeeSpecialization;
	private String employeeMobilenumber;
	private Double employeeExperience;
	private boolean status;
	private Date joinDate;
	private String joinDateStr;
	private Employee reportingTo;
	private Date benchDate;
	private String emailId;
	private Date exitDate;
	private String exitDateStr;
	private boolean managerStatus;
	
	
	
	public String getExitDateStr() {
		return exitDateStr;
	}

	public void setExitDateStr(String exitDateStr) {
		this.exitDateStr = exitDateStr;
	}

	private EmployeeSkillSet skillSet;
	private boolean benchStatus;
	
	private boolean assigned;
	private String billable;
	
	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}

	public EmployeeSkillSet getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(EmployeeSkillSet skillSet) {
		this.skillSet = skillSet;
	}

	
	
	public boolean isBenchStatus() {
		return benchStatus;
	}

	public void setBenchStatus(boolean benchStatus) {
		this.benchStatus = benchStatus;
	}

	
	
	

	public boolean isManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(boolean managerStatus) {
		this.managerStatus = managerStatus;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public Date getBenchDate() {
		return benchDate;
	}

	public void setBenchDate(Date benchDate) {
		this.benchDate = benchDate;
	}

	@SuppressWarnings("rawtypes")
	private Set resourceallocationses = new HashSet(0);

	public Employee() {
	}

	public Employee(Category category,Workplace workplace, Cities cities, Countries countries, State states, String employeeId,
			String employeeName, String employeeDesg, String employeeSpecialization, String employeeMobilenumber,Double employeeExperience,
			boolean status,Date joinDate,Employee reportingTo , String emailId) {
		this.employeeCategory = category;
		this.employeeCity = cities;
		this.employeeCountry = countries;
		this.employeeState = states;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeDesg = employeeDesg;
		this.employeeSpecialization = employeeSpecialization;
		this.employeeMobilenumber = employeeMobilenumber;
		this.employeeExperience = employeeExperience;
		this.status = status;
		this.joinDate =joinDate;
		this.reportingTo =reportingTo;
		this.emailId = emailId;
	
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	@SuppressWarnings("rawtypes")
	public Employee(Category category, Cities cities, Countries countries, State states, String employeeId,
			String employeeName, String employeeDesg, String employeeSpecialization, String employeeMobilenumber,Double employeeExperience,
			boolean status,Date joinDate, Set resourceallocationses , Employee reportingTo , String emailId) {
		this.employeeCategory = category;
		this.employeeCity = cities;
		this.employeeCountry = countries;
		this.employeeState = states;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeDesg = employeeDesg;
		this.employeeSpecialization = employeeSpecialization;
		this.employeeMobilenumber = employeeMobilenumber;
		this.employeeExperience = employeeExperience;
		this.status = status;
		this.joinDate =joinDate;
		this.reportingTo = reportingTo;
		this.resourceallocationses = resourceallocationses;
		this.emailId = emailId;
	}


	

	
	

	public Double getEmployeeExperience() {
		return employeeExperience;
	}

	public void setEmployeeExperience(Double employeeExperience) {
		this.employeeExperience = employeeExperience;
	}

	public Employee getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(Employee userEmp) {
		this.reportingTo = userEmp;
	}

	public Integer getEmpId() {
		return this.empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Category getEmployeeCategory() {
		return this.employeeCategory;
	}

	public void setEmployeeCategory(Category category) {
		this.employeeCategory = category;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeDesg() {
		return this.employeeDesg;
	}

	public void setEmployeeDesg(String employeeDesg) {
		this.employeeDesg = employeeDesg;
	}

	public String getEmployeeSpecialization() {
		return this.employeeSpecialization;
	}

	public void setEmployeeSpecialization(String employeeSpecialization) {
		this.employeeSpecialization = employeeSpecialization;
	}

	public String getEmployeeMobilenumber() {
		return this.employeeMobilenumber;
	}

	public void setEmployeeMobilenumber(String employeeMobilenumber) {
		this.employeeMobilenumber = employeeMobilenumber;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Set getResourceallocationses() {
		return this.resourceallocationses;
	}

	@SuppressWarnings("rawtypes")
	public void setResourceallocationses(Set resourceallocationses) {
		this.resourceallocationses = resourceallocationses;
	}

	public Cities getEmployeeCity() {
		return employeeCity;
	}

	public void setEmployeeCity(Cities employeeCity) {
		this.employeeCity = employeeCity;
	}

	public Countries getEmployeeCountry() {
		return employeeCountry;
	}

	public void setEmployeeCountry(Countries employeeCountry) {
		this.employeeCountry = employeeCountry;
	}

	public State getEmployeeState() {
		return employeeState;
	}

	public void setEmployeeState(State employeeState) {
		this.employeeState = employeeState;
	}


	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getJoinDateStr() {
		return joinDateStr;
	}

	public void setJoinDateStr(String joinDateStr) {
		this.joinDateStr = joinDateStr;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", employeeCategory=" + employeeCategory + ", employeeCity=" + employeeCity
				+ ", workplace=" + workplace + ", employeeCountry=" + employeeCountry + ", employeeState="
				+ employeeState + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeDesg="
				+ employeeDesg + ", employeeSpecialization=" + employeeSpecialization + ", employeeMobilenumber="
				+ employeeMobilenumber + ", employeeExperience=" + employeeExperience + ", status=" + status
				+ ", joinDate=" + joinDate + ", joinDateStr=" + joinDateStr + ", reportingTo=" + reportingTo
				+ ", benchDate=" + benchDate + ", emailId=" + emailId + ", exitDate=" + exitDate + ", skillSet="
				+ skillSet + ", benchStatus=" + benchStatus + ", assigned=" + assigned + ", billable=" + billable
				+ ", resourceallocationses=" + resourceallocationses + "]";
	}

	/*@Override
	public String toString() {
		return "Employee [empId=" + empId + ", employeeCategory=" + employeeCategory + ", employeeCity=" + employeeCity
				+ ", workplace=" + workplace + ", employeeCountry=" + employeeCountry + ", employeeState="
				+ employeeState + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeDesg="
				+ employeeDesg + ", employeeSpecialization=" + employeeSpecialization + ", employeeMobilenumber="
				+ employeeMobilenumber + ", employeeExperience=" + employeeExperience + ", status=" + status
				+ ", joinDate=" + joinDate + ", joinDateStr=" + joinDateStr + ", reportingTo=" + reportingTo
				+ ", benchDate=" + benchDate + ", emailId=" + emailId + ", exitDate=" + exitDate + ", exitDateStr="
				+ exitDateStr + ", skillSet=" + skillSet + ", benchStatus=" + benchStatus + ", assigned=" + assigned
				+ ", billable=" + billable + ", resourceallocationses=" + resourceallocationses + "]";
	}
*/
	
	
	

}
