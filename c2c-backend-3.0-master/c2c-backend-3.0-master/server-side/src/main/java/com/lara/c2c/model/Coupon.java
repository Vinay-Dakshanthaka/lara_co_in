package com.lara.c2c.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="cb_coupon")
public class Coupon{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_id")
	private Integer couponId;
	@Column(unique = true)
	private String couponCode;
	private String couponDesc;
	private Date startDate;
	private Date endDate;
	private Double reductionPercentage;
	
	private String cusomerGroup; //telecaller or bda name to whom we are assigning this coupon
	
	
	
	

}
