package com.gcs.db.businessDao;

public class EmployeeSkillSet {
	
	private int id;
	private SkillSet skillSet;
	private Employee empId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SkillSet getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(SkillSet skillSet) {
		this.skillSet = skillSet;
	}
	public Employee getEmpId() {
		return empId;
	}
	public void setEmpId(Employee empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "EmployeeSkillSet [id=" + id + ", skillSet=" + skillSet + ", empId=" + empId + "]";
	}
	
	
	
	

}
