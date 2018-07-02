package com.gcs.dbDao;

import java.util.List;


import com.gcs.db.businessDao.Leaves;

public interface LeavesDao {
	
	public void saveOrUpdate(Leaves leaves);
	public List<Leaves> getLeavesList();

}
