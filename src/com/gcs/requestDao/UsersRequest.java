package com.gcs.requestDao;



public class UsersRequest {
	private int userId;
	
	private String userName;
	private String loginName;
	private String loginPassword;
	private boolean isActive;
	private String email;
	private String role;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UsersRequest [userId=" + userId + ", userName=" + userName + ", loginName=" + loginName
				+ ", loginPassword=" + loginPassword + ", isActive=" + isActive + ", email=" + email + "]";
	}
	
	

}
