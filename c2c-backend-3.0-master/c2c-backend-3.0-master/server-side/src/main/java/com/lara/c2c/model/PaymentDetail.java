package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "cb_payment_details")
public class PaymentDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paymentId")
	private int paymentId;
			
	@Column(name = "customer_id")
	private String customerId;	
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name="card_name")
	private String cardName;
	
	@Column(name = "bank_ref_no")
	private String bankRefNo;
	
	@Column(name = "currrency")
	private String currency;
	
	@Column(name = "order_id")
	private String orderId;
		
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "response_code")
	private int responseCode;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "trans_date")
	private String transDate;
		
	@Column(name = "tracking_id")
	private String trackingId;	
	
	@Column(name="mer_amount")
	private double merAmount;
	
	@Column(name = "offer_type")
	private String offerType;
	
	@Column(name = "offer_code")
	private String offerCode;
	
	@Column(name = "status_code")
	private String statusCode;
	
	@Column(name = "vault")
	private String vault;
	
	@Column(name = "failure_message")
	private String failureMessage;
	
	@Column(name = "retry")
	private String retry;
	
	@Column(name = "eci_value")
	private String eciValue;
	
	@Column(name = "status_message")
	private String statusMessage;
	
	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "discount_value")
	private double discountValue;
}
