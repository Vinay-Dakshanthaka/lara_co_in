package com.lara.c2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.OrderState;
import com.lara.c2c.dto.PhonePeBody;
import com.lara.c2c.dto.UserAndCoursePackagesInfo;
import com.lara.c2c.service.Orders_1Service;

@RestController
@RequestMapping("/api/orders/v2")
@CrossOrigin("*")
public class Orders_1Controller {

	@Autowired
	private Orders_1Service orderService;

	@PostMapping("initiate")
	public ResponseEntity<Long> initiateOrder(@RequestBody UserAndCoursePackagesInfo userAndCourseInfo) {
		return orderService.initiateOrder(userAndCourseInfo);
	}

	@PostMapping("callback")
	public void callback() {
		System.out.println("Callback");
	}

	@PostMapping("checkStatus")
	public ResponseEntity<OrderState> checkStatus(@RequestParam Long orderId) {
		System.out.println(orderId + "from checkStatus");
		return orderService.checkStatus(orderId);
	}

	@PostMapping("confirmOrder/{orderId}/{merchantId}")
	public void confirmOrder(@PathVariable Long orderId, @PathVariable String merchantId,
			@RequestHeader(name = "X-VERIFY") String xVerify, @RequestBody PhonePeBody request) {
		orderService.confirmOrder(orderId, merchantId, xVerify, request);
		return;
	}
}
