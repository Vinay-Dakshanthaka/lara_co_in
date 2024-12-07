package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.PaymentDetail;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Integer>{

	PaymentDetail findByCustomerIdAndOrderId(String userId, String orderId);

}
