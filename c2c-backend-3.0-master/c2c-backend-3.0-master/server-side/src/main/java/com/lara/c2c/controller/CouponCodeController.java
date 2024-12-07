package com.lara.c2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.model.Coupon;
import com.lara.c2c.service.CouponCodeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/learner")
public class CouponCodeController {

	@Autowired
	private CouponCodeService couponCodeService;
	
	@GetMapping("/coupon/{couponCode}")
	public Coupon getCoupon(@PathVariable String couponCode) {
		return couponCodeService.getCoupon(couponCode);
	}
	
	@GetMapping("/coupon/all")
	public Iterable<Coupon> getCoupons(){
		return couponCodeService.getCoupons();
	}

	@PostMapping("/coupon")
	public Coupon saveCoupon(@RequestBody Coupon coupon) {
		return couponCodeService.saveCoupon(coupon);
	}


}
