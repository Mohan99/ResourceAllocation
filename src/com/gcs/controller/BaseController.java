package com.gcs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.gcs.requestDao.LoginRequest;
import com.gcs.responseDao.Response;

public class BaseController {
	public ModelAndView getLogoutView(Response res) {
		   ModelAndView loginModel=null;
			Map<String, Object> mapModel = new HashMap<String, Object>();
			 LoginRequest loginRequest =new LoginRequest();
			
			mapModel.put("LoginRequest", loginRequest);
			mapModel.put("Response", res);
			loginModel = new ModelAndView("login", mapModel);
			return loginModel;
	   }
	   
	   public Response getResponse(String statusCode,String statusMsg) {
		   Response response=new Response();
		   response.setStatusCode(statusCode);
		   response.setStatusMessage(statusMsg);
		   return response;
	   }
}
