package com.lara.c2c.dto.learner;

import java.util.Date;

public class LiveClassDTO {

	private Integer id;
	private Date dateAndTime;
	private Integer coursePackageId;
	private String liveUrl;
	private String password;
	private String topic;
	private String dependents;
	private Boolean isPublic;
	private String note;
	public LiveClassDTO(Integer id, Date dateAndTime, Integer coursePackageId, String liveUrl, String password,
			String topic, String dependents, Boolean isPublic, String note) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.coursePackageId = coursePackageId;
		this.liveUrl = liveUrl;
		this.password = password;
		this.topic = topic;
		this.dependents = dependents;
		this.isPublic = isPublic;
		this.note = note;
	}
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
	public Integer getCoursePackageId() {
		return coursePackageId;
	}
	public void setCoursePackageId(Integer coursePackageId) {
		this.coursePackageId = coursePackageId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiveClassDTO other = (LiveClassDTO) obj;
		if (dateAndTime == null) {
			if (other.dateAndTime != null)
				return false;
		} else if (!dateAndTime.equals(other.dateAndTime))
			return false;
		return true;
	}

	
	
}
