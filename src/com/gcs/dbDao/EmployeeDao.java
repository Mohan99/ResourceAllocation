package com.gcs.dbDao;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.State;
import com.gcs.db.businessDao.Workplace;
import com.gcs.requestDao.EmpReportDataRequest;
import com.gcs.responseDao.Response;

@Transactional               
public interface EmployeeDao {
	public List<Employee> getInActiveEmpList();
	public List<Category> getCategoryList();
	public List<Countries> getCountryList();
	public List<State> getSatesList(int countryId);
	public List<Cities> getCitiesList(int stateId);
	public List<Workplace> getWorkplaceList();
	public Employee getEmployeeData(String empID);
	public  void delete(Integer id,boolean isEmployee);
	public void insert(List<Employee> empList);
	public ResultSet getSwipeData(EmpReportDataRequest req); 
	public long empCount();
	public long projectsCount();
	public  boolean deleteSwipeData();
	public  void getData();
    public  void deleteAllEmployees();
    public void saveOrUpdate(Employee emp);
	public List<State> getStates();
	public List<Cities> getCities();
	public Cities getCityByName(String cityName);
	public List<Employee> getActiveEmployeeList();
	public List<Employee> getEmpListNotIn();
	public List<Employee> getEmpList();
	void updateStatus(String empId, boolean isEmployee);
	Boolean setEndDateByEmpId(Integer empid, Date date);
	//List<Employee> getActiveEmpList();
	public void updateEmployeeAsBench(int empId, String date);
	public List<Employee> getEmployeesUnderManagers(int id);
	public long empCountByReporting(int id);
	public void updateEmployeeAsAssigned(int empId);
	public List<Employee> getInActiveEmpUnderManager(int id);
	void updateEmployeeExitDate(int empId, String date);
	public Employee getEmployeeDataByEmployeeId(String empID);
	long skillSetCount();
	List<Employee> getEmpListForValidation(String employeeId);
	//Response validateEmployee(String employeeId);
}
