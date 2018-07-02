package com.gcs.requestDao;

import java.util.List;

import com.gcs.db.businessDao.Timesheet;

public class TimesheetForm {

	private List<Timesheet> timeSheet;

	public List<Timesheet> getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(List<Timesheet> timeSheet) {
		this.timeSheet = timeSheet;
	}
	
	@Override
	public String toString() {
		return "TimesheetForm [timeSheet=" + timeSheet + "]";
	}
}
