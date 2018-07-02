package com.gcs.requestDao;

import java.util.Date;

import com.gcs.db.businessDao.Employee;

public class LeavesRequest {
	
	private int id;
	private Employee empId;
	private Date fromdate;
	private Date todate;
	private int totalleaves;
	
	
	public Employee getEmpId() {
		return empId;
	}
	public void setEmpId(Employee empId) {
		this.empId = empId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	public int getTotalleaves() {
		return totalleaves;
	}
	public void setTotalleaves(int totalleaves) {
		this.totalleaves = totalleaves;
	}
	@Override
	public String toString() {
		return "LeavesRequest [id=" + id + ", empId=" + empId + ", fromdate=" + fromdate + ", todate=" + todate
				+ ", totalleaves=" + totalleaves + "]";
	}
	
	
	

}
