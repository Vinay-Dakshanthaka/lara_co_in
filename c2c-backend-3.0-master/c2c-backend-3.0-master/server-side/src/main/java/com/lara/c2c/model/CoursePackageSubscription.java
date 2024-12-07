package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_package_sunscription")
public class CoursePackageSubscription {
	
	@Id
	@Column(name = "subscription_id")
	private int subscriptionId;
	
	@Column(name = "course_package_id")
	private int coursePackageId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "subscription_date")
	private Date subscriptionDate;
	
}


