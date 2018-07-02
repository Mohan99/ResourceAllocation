package com.gcs.dbDao;

import com.gcs.responseDao.Response;

public interface LoginDao {
public Response validateLogin(String email,String password);
public Response validatePmoLogin(String email,String password);
public Response validateEmployeeLogin(String empId,String password);
public Response validateSalesLogin(String email,String password);

public Response changePassword(Integer userId, String oldPassword,String newPassword);
public Response changeEmpPassword(Integer userId, String oldPassword,String newPassword);
public Response validateAccountsLogin(String email, String password);


}
