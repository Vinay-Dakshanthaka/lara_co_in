/**
 * @author Dev
 */

package com.lara.c2c.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dev_orders")
public class Orders_1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "total_amount")
	private long totalAmount;

	@Column(name = "subtotal")
	private double subTotal;

	@Column(name = "gst")
	private double gst;

	@OneToMany(mappedBy = "orders_1Id", cascade = CascadeType.ALL)
	private List<Orders_1_packages_prices> coursesBought;

	@Column(name = "order_date")
	@CreationTimestamp
	private Date date;

	private String couponCode;

	private String state;

	private String xVerfiy;

	@CreationTimestamp
	private LocalDateTime createdOn;

	private boolean urlHit;

	private LocalDateTime urlHitTimeStamp;

	private String mop;

	private boolean checksumMatch;

	private boolean jsonError;

	private boolean orderExpired;

	private boolean merchantMatch;

	private String paymentStatus;

	private boolean orderClosed;

	private String base64;
}
