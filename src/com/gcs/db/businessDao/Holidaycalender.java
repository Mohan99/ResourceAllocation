package com.gcs.db.businessDao;

import java.util.Date;

public class Holidaycalender {

	private int id;
	private Date date;
	private String reason;
	private int type;
	private Date createdDate;
	private String dateStr;
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Holidaycalender [id=" + id + ", date=" + date + ", reason=" + reason + ", type=" + type
				+ ", createdDate=" + createdDate + "]";
	}
	
	
}
