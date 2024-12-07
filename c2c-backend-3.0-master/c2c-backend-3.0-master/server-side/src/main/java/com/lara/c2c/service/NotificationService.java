package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.WatchedVideoNotification;
import com.lara.c2c.repository.WatchedVideoNotifRepository;

@Service
public class NotificationService {
	
	@Autowired
	private WatchedVideoNotifRepository notificationRepository;
	
	public void addNotification(WatchedVideoNotification notification) {
		notificationRepository.save(notification);
	}
	
	public List<WatchedVideoNotification> getAllWatchedVideoNotifications(){  
	    List<WatchedVideoNotification> notifRecords = new ArrayList<>();  
	    notificationRepository.findAll().forEach(notifRecords::add);  
	    return notifRecords;  
	} 
	
	public List<WatchedVideoNotification> getAllWatchedVideoNotifications(String userId){  
	    List<WatchedVideoNotification> notifRecords = new ArrayList<>();  
	    notificationRepository.findByUserId(userId).forEach(notifRecords::add);  
	    return notifRecords;  
	} 
}
