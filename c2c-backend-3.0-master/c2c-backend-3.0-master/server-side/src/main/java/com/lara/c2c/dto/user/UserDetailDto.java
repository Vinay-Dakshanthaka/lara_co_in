package com.lara.c2c.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UserDetailDto
{
	private Integer coursePackageId;
	private String coursePackageName;
	private String userId;
	private String firstName;
	//private String lastName;
	private String mobileNo;
	private String email;
	private Integer status;
	private Integer mailSubscription;
	private String activationCode;
	public UserDetailDto() {
		
	}
	
	public UserDetailDto(String userId, String firstName, String mobileNo, String email) {
		this.userId = userId;
		this.firstName = firstName;
		this.mobileNo = mobileNo;
		this.email = email;		
	}
	
	public UserDetailDto(String userId, String firstName, String mobileNo, String email, Integer status) {
		this.userId = userId;
		this.firstName = firstName;
		this.mobileNo = mobileNo;
		this.email = email;		
		this.status = status;
	}
	
	public UserDetailDto(Integer coursePackageId, String coursePackageName, String userId, String firstName, String mobileNo, String email, Integer status) {
		this.coursePackageId = coursePackageId;
		this.coursePackageName = coursePackageName;
		this.userId = userId;
		this.firstName = firstName;
		this.mobileNo = mobileNo;
		this.email = email;		
		this.status = status;
	}
	
	public UserDetailDto(String userId, String firstName, String mobileNo, String email, Integer status, Integer mailSubscription, String activationCode) {	
		this.userId = userId;
		this.firstName = firstName;
		this.mobileNo = mobileNo;
		this.email = email;		
		this.status = status;
		this.mailSubscription = mailSubscription;
		this.activationCode = activationCode;
	}
	
}
