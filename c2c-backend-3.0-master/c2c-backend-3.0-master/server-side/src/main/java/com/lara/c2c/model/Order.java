package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cb_orders")
public class Order {
	
	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "user_id")
	private String userId;	
	
	@Column(name = "user_curr_order_sess_time_id")
	private String userCurrOrderSessTimeId;
	
	@Column(name = "total_amount")
	private double totalAmount;
	
	@Column(name = "order_items")
	private String orderItems;
	
	@Column(name = "channel_id")
	private String channelId;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "order_date")
	private Date date;
	
	@Column(name = "order_status_message")
	private String orderStatusMessage;
	
	private String couponCode;
	
}
