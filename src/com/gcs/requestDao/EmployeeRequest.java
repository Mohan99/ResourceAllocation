package com.gcs.requestDao;

import java.util.Arrays;
import java.util.Date;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.SkillSet;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Usersemployee;
import com.gcs.db.businessDao.Workplace;

public class EmployeeRequest {

	private Integer empId;
	private Category category;
	private Cities cities;
	private Countries countries;
	private State states;
	private String employeeId;
	private String employeeName;
	private String employeeDesg;
	private String employeeSpecialization;
	private String employeeMobilenumber;
	private Double employeeExperience;
	private boolean status;
	private Date joinDate;
	private String joinDateStr;
	private String password;
	private Employee reportingTo;
	private Date benchDate;
	private Workplace workplace;
	private boolean benchStatus;
	private String emailId;
	private Date exitDate;
	private String exitDateStr;
	public String getExitDateStr() {
		return exitDateStr;
	}

	public void setExitDateStr(String exitDateStr) {
		this.exitDateStr = exitDateStr;
	}


	private EmployeeSkillSet skillSet;
	public EmployeeSkillSet getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(EmployeeSkillSet skillSet) {
		this.skillSet = skillSet;
	}


	private SkillSet [] skills;

	public SkillSet[] getSkills() {
		return skills;
	}

	public void setSkills(SkillSet[] skills) {
		this.skills = skills;
	}
	
	
	private SkillSet skill;

	public Double getEmployeeExperience() {
		return employeeExperience;
	}

	public void setEmployeeExperience(Double employeeExperience) {
		this.employeeExperience = employeeExperience;
	}

	public boolean isBenchStatus() {
		return benchStatus;
	}

	public void setBenchStatus(boolean benchStatus) {
		this.benchStatus = benchStatus;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	public Date getBenchDate() {
		return benchDate;
	}

	public void setBenchDate(Date benchDate) {
		this.benchDate = benchDate;
	}

	public Employee getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(Employee reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Cities getCities() {
		return cities;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}

	public Countries getCountries() {
		return countries;
	}

	public void setCountries(Countries countries) {
		this.countries = countries;
	}

	public State getStates() {
		return states;
	}

	public void setStates(State states) {
		this.states = states;
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

	public String getEmployeeDesg() {
		return employeeDesg;
	}

	public void setEmployeeDesg(String employeeDesg) {
		this.employeeDesg = employeeDesg;
	}

	public String getEmployeeSpecialization() {
		return employeeSpecialization;
	}

	public void setEmployeeSpecialization(String employeeSpecialization) {
		this.employeeSpecialization = employeeSpecialization;
	}

	public String getEmployeeMobilenumber() {
		return employeeMobilenumber;
	}

	public SkillSet getSkill() {
		return skill;
	}

	public void setSkill(SkillSet skill) {
		this.skill = skill;
	}

	public void setEmployeeMobilenumber(String employeeMobilenumber) {
		this.employeeMobilenumber = employeeMobilenumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
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
		return "EmployeeRequest [empId=" + empId + ", category=" + category + ", cities=" + cities + ", countries="
				+ countries + ", states=" + states + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", employeeDesg=" + employeeDesg + ", employeeSpecialization=" + employeeSpecialization
				+ ", employeeMobilenumber=" + employeeMobilenumber + ", employeeExperience=" + employeeExperience
				+ ", status=" + status + ", joinDate=" + joinDate + ", joinDateStr=" + joinDateStr + ", password="
				+ password + ", reportingTo=" + reportingTo + ", benchDate=" + benchDate + ", workplace=" + workplace
				+ ", benchStatus=" + benchStatus + ", emailId=" + emailId + ", exitDate=" + exitDate + ", skillSet="
				+ skillSet + ", skills=" + Arrays.toString(skills) + ", skill=" + skill + "]";
	}

	/*@Override
	public String toString() {
		return "EmployeeRequest [empId=" + empId + ", category=" + category + ", cities=" + cities + ", countries="
				+ countries + ", states=" + states + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", employeeDesg=" + employeeDesg + ", employeeSpecialization=" + employeeSpecialization
				+ ", employeeMobilenumber=" + employeeMobilenumber + ", employeeExperience=" + employeeExperience
				+ ", status=" + status + ", joinDate=" + joinDate + ", joinDateStr=" + joinDateStr + ", password="
				+ password + ", reportingTo=" + reportingTo + ", benchDate=" + benchDate + ", workplace=" + workplace
				+ ", benchStatus=" + benchStatus + ", emailId=" + emailId + ", exitDate=" + exitDate + ", exitDateStr="
				+ exitDateStr + ", skillSet=" + skillSet + ", skills=" + Arrays.toString(skills) + ", skill=" + skill
				+ "]";
	}*/

	
	

}
