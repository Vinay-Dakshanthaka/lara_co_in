package com.lara.c2c.repository;

import org.springframework.data.repository.CrudRepository;

import com.lara.c2c.model.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Integer>{

	public Coupon findByCouponCode(String couponCode);
	
}
