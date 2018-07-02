package com.gcs.requestDao;

public class EmpReportDataRequest {
	private String startDate;
	private String endDate;
	private String ManagerId;
	private String empId;
	private String searchedBy;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getManagerId() {
		return ManagerId;
	}
	public void setManagerId(String managerId) {
		ManagerId = managerId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getSearchedBy() {
		return searchedBy;
	}
	public void setSearchedBy(String searchedBy) {
		this.searchedBy = searchedBy;
	}
}
