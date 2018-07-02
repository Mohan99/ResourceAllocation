package com.gcs.dbDaoImpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gcs.db.businessDao.Holidaycalender;
import com.gcs.db.businessDao.Leaves;
import com.gcs.dbDao.LeavesDao;

public class LeavesDaoImpl extends HibernateDaoSupport implements LeavesDao {

	@Override
	public void saveOrUpdate(Leaves leaves) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(leaves);
		 getSession().flush();
		
	}

	@Override
	public List<Leaves> getLeavesList() {
		// TODO Auto-generated method stub
		List<Leaves> list=getHibernateTemplate().find("from Leaves");
		return list;
	}

}
