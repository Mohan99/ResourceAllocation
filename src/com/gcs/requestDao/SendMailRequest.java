package com.gcs.requestDao;

public class SendMailRequest {
private String toEmail;
private String ccEmails;
private String subject;
private String message;
public String getToEmail() {
	return toEmail;
}
public void setToEmail(String toEmail) {
	this.toEmail = toEmail;
}
public String getCcEmails() {
	return ccEmails;
}
public void setCcEmails(String ccEmails) {
	this.ccEmails = ccEmails;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
