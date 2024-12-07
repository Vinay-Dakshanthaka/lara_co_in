package com.lara.c2c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.PaymentDetail;
import com.lara.c2c.repository.PaymentDetailRepository;

@Service
public class PaymentDetailService {
	
	@Autowired
	private PaymentDetailRepository paymentDetailRepo;
	
	public void savePaymentDetail(PaymentDetail paymentDetailRequest) {
		paymentDetailRepo.save(paymentDetailRequest);
	}
	
	public PaymentDetail _getPaymentDetails(String userId, String orderId) {
		return paymentDetailRepo.findByCustomerIdAndOrderId(userId, orderId);
	}
	
}
