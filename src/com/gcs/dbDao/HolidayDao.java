package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Holidaycalender;

public interface HolidayDao {
	public void saveOrUpdate(Holidaycalender calender);
	public List<Holidaycalender> getHolidaysList();
}
