package com.lara.c2c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lara.c2c.model.Coupon;
import com.lara.c2c.repository.CouponRepository;

@Service
public class CouponCodeService {

	@Autowired
	private CouponRepository couponRepository;
	
	public Coupon getCoupon(String couponCode) {
		return couponRepository.findByCouponCode(couponCode);
	}
	
	public Iterable<Coupon> getCoupons(){
		return couponRepository.findAll();
	}
	
	public Coupon saveCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}
	
	
}
