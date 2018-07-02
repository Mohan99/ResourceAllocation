package com.gcs.requestDao;

import java.util.List;

import com.gcs.db.businessDao.Employee;

public class EmployeeDataHandleObj {

	private List<Employee> managerList;
	private Employee empObj;
	
	public List<Employee> getManagerList() {
		return managerList;
	}
	public void setManagerList(List<Employee> managerList) {
		this.managerList = managerList;
	}
	public Employee getEmpObj() {
		return empObj;
	}
	public void setEmpObj(Employee empObj) {
		this.empObj = empObj;
	}
	
}
