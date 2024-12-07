package com.lara.c2c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.learner.LearnerNotificationRequest;
import com.lara.c2c.model.LearnerNotification;
import com.lara.c2c.service.LearnerNotificationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/learner")
public class LearnerNotificationController {

	@Autowired
	private LearnerNotificationService learnerNotificationService;
	
	
	@RequestMapping(path="/saveLearnerNotification", method=RequestMethod.POST)
	public LearnerNotificationRequest save(@RequestBody LearnerNotificationRequest learnerNotificationRequest) {	
		LearnerNotification learnerNotification;
		for(Integer id : learnerNotificationRequest.getCoursePackageIds()) {
			learnerNotification = new LearnerNotification();
			learnerNotification.setCoursePackageId(id);
			learnerNotification.setDateAndTime(learnerNotificationRequest.getDateAndTime());			
			learnerNotification.setNote(learnerNotificationRequest.getNote());			
			learnerNotificationService.save(learnerNotification);	
		}			
		return learnerNotificationRequest;
	}	
	@RequestMapping(path="/readAllLearnerNotifications", method=RequestMethod.GET)
	public List<LearnerNotification> readAll() {		
		return learnerNotificationService.readAll();
	}	
	
	@RequestMapping(path="/readAllLearnerNotificationsForCurrentUser", method=RequestMethod.GET)
	public List<LearnerNotification> readAllForOneUser() {		
		return learnerNotificationService.readAllForOneUser();
	}	

}


/*
{
  	"id": 4041,
	"dateAndTime": "2014-09-27T10:30:00.000",
	"coursePackageIds":[1, 8, 19],
	"note": "some thing"
}
 */





	
	
