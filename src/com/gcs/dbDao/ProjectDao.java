package com.gcs.dbDao;

import java.util.Date;
import java.util.List;


import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;

public interface ProjectDao {

	public List<Projects> getProjects();
	public void saveOrUpdate(Projects project);
	public Projects getProjectData(String projectID);
	public List<Projects> getClosedProjects();
	public List<Projects> getActiveProjects();
//	public List<Projects> getProjectEndDate(String id, Date date);
	//public Projects getProjectEndDate(String id, Date date);
	Boolean setProjectEndDate(Integer id, Date date);
	//void insert(Projects project);
	
	long ActiveProjCount(int id);
	long ClosedProjCount(int id);
	long projCountByReporting(int id);
	long ActiveProjectsCount();
	long ClosedProjectsCount();
	List<Projects> getActiveProjectsUnderManager(int id);
	List<Projects> getCompletedProjectsUnderManager(int id);
	List<Projects> getTotalProjectsUnderManager(int id);
	void delete(Integer id, boolean isProject);
}
