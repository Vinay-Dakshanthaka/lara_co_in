package com.lara.c2c.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UsersForMarketing {
	
	@Id
	private String email;
	private String firstName;
	private String mobileNumber;
	private Integer subscribeStatus; //1 for subscribe and 0 for unsubscribe
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getSubscribeStatus() {
		return subscribeStatus;
	}
	public void setSubscribeStatus(Integer subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}
	
	
	

}
