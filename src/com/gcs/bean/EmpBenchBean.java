package com.gcs.bean;

import java.util.Date;

public class EmpBenchBean {

	private String employeeId;
	private String employeeName;
	private Date fromDate;
	private Date toDate;
	private Integer empId;
	private long benchPeriod;
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public void setBenchPeriod(int benchPeriod) {
		this.benchPeriod = benchPeriod;
	}
	public long getBenchPeriod() {
		return benchPeriod;
	}
	public void setBenchPeriod(long benchPeriod) {
		this.benchPeriod = benchPeriod;
	}
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "EmpBenchBean [employeeId=" + employeeId + ", employeeName=" + employeeName + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", empId=" + empId + ", benchPeriod=" + benchPeriod + "]";
	}
	
	
	
}
