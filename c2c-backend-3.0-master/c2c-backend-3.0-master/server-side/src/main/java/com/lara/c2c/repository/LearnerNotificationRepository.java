package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.lara.c2c.model.LearnerNotification;

public interface LearnerNotificationRepository extends CrudRepository<LearnerNotification, Integer>{

	
	
	@Query("select n from LearnerNotification n where YEARWEEK(n.dateAndTime)=YEARWEEK(NOW())")
	public List<LearnerNotification> readAllLearnerNotifications();
	
	
	@Query("select n from LearnerNotification n inner join LearnerCourse learnerCourse on (n.coursePackageId = learnerCourse.coursePackageId)  "
		   + "inner join Learner learner on (learner.userId = learnerCourse.userId) "
		   + "where learner.userId = :userId and "
		   + "YEARWEEK(n.dateAndTime)=YEARWEEK(NOW())")
	public List<LearnerNotification> readAllLearnerNotificationsForOneLearner(String userId);
	
	
	
}
