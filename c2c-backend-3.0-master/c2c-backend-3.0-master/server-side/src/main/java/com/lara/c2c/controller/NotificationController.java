package com.lara.c2c.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.FindNotificationResponse;
import com.lara.c2c.model.WatchedVideoNotification;
import com.lara.c2c.service.NotificationService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@GetMapping("/findAll")
	public ResponseEntity<FindNotificationResponse> retrieveAllLearnerData() {	
		
		FindNotificationResponse notifResponse = new FindNotificationResponse();
		notifResponse.setNotificationData(notificationService.getAllWatchedVideoNotifications());
		
		if (Constants.isRequestFailed(notifResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifResponse);
		}
		return ResponseEntity.ok().body(notifResponse);	
	}
	
	@GetMapping("/findAllbyUserId")
	public ResponseEntity<FindNotificationResponse> retrieveAllLearnerDataByUserId() {	
		String userId = UserInfo.getUserId(hsRequest);
		FindNotificationResponse notifResponse = new FindNotificationResponse();
		notifResponse.setNotificationData(notificationService.getAllWatchedVideoNotifications(userId));
		
		if (Constants.isRequestFailed(notifResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifResponse);
		}
		return ResponseEntity.ok().body(notifResponse);	
	}
	
	@PostMapping("/addNotify")
	public ResponseEntity<Void> addNotificationMsg(@RequestBody WatchedVideoNotification notificationRequest){
		notificationRequest.setCreated(ServiceUtils.getCurrentDateTime());
		notificationService.addNotification(notificationRequest);
		return ResponseEntity.ok().build();		
	}

}
