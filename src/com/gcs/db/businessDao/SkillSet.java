package com.gcs.db.businessDao;

public class SkillSet {

	
private int skillId;
private String skillSet;
private boolean status;
public int getSkillId() {
	return skillId;
}
public void setSkillId(int skillId) {
	this.skillId = skillId;
}


public String getSkillSet() {
	return skillSet;
}
public void setSkillSet(String skillSet) {
	this.skillSet = skillSet;
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}
@Override
public String toString() {
	return "SkillSet [skillId=" + skillId + ", skillSet=" + skillSet + ", status=" + status + "]";
}




}
