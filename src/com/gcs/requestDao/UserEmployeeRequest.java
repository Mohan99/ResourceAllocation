package com.gcs.requestDao;

import java.util.Date;

import com.gcs.db.businessDao.Employee;

public class UserEmployeeRequest {
	private int id;
	private Employee empId;
	private String password;
	private int status;
	private Date createdDate;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "UserEmployeeRequest [id=" + id + ", empId=" + empId + ", password=" + password + ", status=" + status
				+ ", createdDate=" + createdDate + "]";
	}
	
}
