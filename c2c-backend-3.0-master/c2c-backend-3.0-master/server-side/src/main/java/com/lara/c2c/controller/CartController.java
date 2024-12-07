package com.lara.c2c.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("addToCart")
	public ResponseEntity<SuccessStatus> addToCart(@RequestParam String userId, @RequestParam Integer packageId){
		System.out.println(userId);
		System.out.println(packageId);
		return cartService.addToCart(userId, packageId);
	}
	
	@PostMapping("getCartNoOfItems")
	public ResponseEntity<Integer> getCartNoOfItems(@RequestParam String userId){
		return cartService.getCartNoOfItems(userId);
	}
	
	@PostMapping("getUserCart")
	public ResponseEntity<Set<CoursePackage>> getUserCart(@RequestParam String userId){
		return cartService.getUserCart(userId);
	}
	
	@PostMapping("deleteFromCart")
	public ResponseEntity<SuccessStatus> deleteFromCart(@RequestParam String userId, @RequestParam Integer packageId){
		return cartService.deleteFromCart(userId, packageId);
	}
}
