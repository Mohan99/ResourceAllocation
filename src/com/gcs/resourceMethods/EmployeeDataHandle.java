package com.gcs.resourceMethods;

import java.util.List;



import com.gcs.db.businessDao.Employee;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.responseDao.EmployeeResponseReport;

public class EmployeeDataHandle {
private EmployeeDao empDao;
	public EmployeeResponseReport getEmployeeList() {
		
		EmployeeResponseReport empData=new EmployeeResponseReport();
		
		@SuppressWarnings("unused")
		List<Employee> list=  empDao.getEmpList();
		
						return empData;
		
	}
}
