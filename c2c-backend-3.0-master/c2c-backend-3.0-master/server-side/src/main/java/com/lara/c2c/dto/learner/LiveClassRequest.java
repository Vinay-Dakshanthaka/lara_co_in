package com.lara.c2c.dto.learner;

import java.util.Date;

public class LiveClassRequest {
	private Integer id;
	private Date dateAndTime;
	private Integer[] coursePackageIds;
	private String liveUrl;
	private String password;
	private String topic;
	private String dependents;
	private Boolean isPublic;
	private String note;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public Integer[] getCoursePackageIds() {
		return coursePackageIds;
	}
	public void setCoursePackageIds(Integer[] coursePackageIds) {
		this.coursePackageIds = coursePackageIds;
	}
	public String getLiveUrl() {
		return liveUrl;
	}
	public void setLiveUrl(String liveUrl) {
		this.liveUrl = liveUrl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDependents() {
		return dependents;
	}
	public void setDependents(String dependents) {
		this.dependents = dependents;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
