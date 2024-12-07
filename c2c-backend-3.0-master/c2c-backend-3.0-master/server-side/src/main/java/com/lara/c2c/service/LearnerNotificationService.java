package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.learner.LiveClassDTO;
import com.lara.c2c.model.LearnerNotification;
import com.lara.c2c.model.LiveClass;
import com.lara.c2c.repository.LearnerNotificationRepository;
import com.lara.c2c.repository.LiveClassRepository;
import com.lara.c2c.utility.UserInfo;

@Service
public class LearnerNotificationService {
	@Autowired
	HttpServletRequest hsRequest;
	
	@Autowired
	private LearnerNotificationRepository learnerNotificationRepository;
	
	public LearnerNotification save(LearnerNotification learnerNotification ) {		
		learnerNotificationRepository.save(learnerNotification);		
		return learnerNotification;
	}	
	
	
	public List<LearnerNotification> readAll() {		
		List<LearnerNotification> learnerNotifications = learnerNotificationRepository.readAllLearnerNotifications();
		learnerNotifications = new ArrayList<LearnerNotification>(new HashSet<LearnerNotification>(learnerNotifications));
		Collections.sort(learnerNotifications, (c1, c2) -> c1.getDateAndTime().compareTo(c2.getDateAndTime()));
		return learnerNotifications;
	}	
	
	public List<LearnerNotification> readAllForOneUser() {		
		String userId = UserInfo.getUserId(hsRequest);
		List<LearnerNotification> learnerNotifications = learnerNotificationRepository.readAllLearnerNotificationsForOneLearner(userId);
		learnerNotifications = new ArrayList<LearnerNotification>(new HashSet<LearnerNotification>(learnerNotifications));
		Collections.sort(learnerNotifications, (c1, c2) -> c1.getDateAndTime().compareTo(c2.getDateAndTime()));
		return learnerNotifications;
	}	
	
		
}
