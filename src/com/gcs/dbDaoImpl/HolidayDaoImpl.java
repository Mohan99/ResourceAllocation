package com.gcs.dbDaoImpl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Holidaycalender;
import com.gcs.db.businessDao.Projects;
import com.gcs.dbDao.HolidayDao;

@Transactional
@Repository("holidayDao")
public class HolidayDaoImpl extends HibernateDaoSupport implements HolidayDao {

	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(Holidaycalender calender) {
		getHibernateTemplate().saveOrUpdate(calender);
		 getSession().flush();
		
	}

	@Override
	public List<Holidaycalender> getHolidaysList() {
		List<Holidaycalender> list=getHibernateTemplate().find("from Holidaycalender");
		ListIterator<Holidaycalender> itr=list.listIterator();  
		Holidaycalender calender = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		while(itr.hasNext()){  
			calender = new Holidaycalender();
			calender = itr.next();
			calender.setDateStr(formatter.format(calender.getDate()));

			} 
		return list;
	}
}
