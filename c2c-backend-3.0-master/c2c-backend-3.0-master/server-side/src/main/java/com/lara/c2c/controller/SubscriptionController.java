package com.lara.c2c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.dto.learner.LearnerBulkEmailRequest;
import com.lara.c2c.dto.learner.LearnerBulkEmailResponse;
import com.lara.c2c.dto.subscription.ManageSubscriptionRequest;
import com.lara.c2c.dto.subscription.ManageSubscriptionResponse;
import com.lara.c2c.service.MailService;
import com.lara.c2c.service.SubscriptionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
	
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/manageSubscription")
	public ResponseEntity<ManageSubscriptionResponse> manageUserSubscription( @RequestBody ManageSubscriptionRequest request) throws Exception {					
		ManageSubscriptionResponse response = subscriptionService._manageUserSubscription(request);			
		return ResponseEntity.ok().body(response);			
	}
	
	@PostMapping("/manageSubscriptionThroughExcel")
	public ResponseEntity<ManageSubscriptionResponse> manageSubscriptionThroughExcel( @RequestBody ManageSubscriptionRequest request) throws Exception {					
		
		ManageSubscriptionResponse response = subscriptionService._manageUserSubscription(request);			
		return ResponseEntity.ok().body(response);			
	}
	
	
	
	@GetMapping("/findUserSubscribedPkgs/{userId}")
	public ResponseEntity<List<Integer>> getSubscribedPackages(@PathVariable String userId){
		List<Integer> response = subscriptionService._getSubscribedPackages(userId);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/sendBulkEmail")
	public ResponseEntity<LearnerBulkEmailResponse> sendBulkEmailToLearners(@RequestBody LearnerBulkEmailRequest request){
		LearnerBulkEmailResponse response = mailService._sendBulkEmailToLearners(request);
		return ResponseEntity.ok().body(response);
	}
	
}
