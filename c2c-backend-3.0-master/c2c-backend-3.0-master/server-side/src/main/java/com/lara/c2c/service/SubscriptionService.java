package com.lara.c2c.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.subscription.ManageSubscriptionRequest;
import com.lara.c2c.dto.subscription.ManageSubscriptionResponse;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.repository.LearnerCourseRepository;

@Service
public class SubscriptionService {
	
	@Autowired
	private LearnerCourseRepository learnerCourseRepo;
	
	public ManageSubscriptionResponse _manageUserSubscription(ManageSubscriptionRequest request) {
		ManageSubscriptionResponse response = new ManageSubscriptionResponse();			
		try {
			//activation
			for(int i=0; i<request.getActCoursePkgList().size(); i++) {
				Optional<LearnerCourse> lrCourse = learnerCourseRepo.findByUserIdAndCoursePackageId(request.getUserId(), request.getActCoursePkgList().get(i));
				if(lrCourse.isPresent()) {
					learnerCourseRepo.activateLearnerCoursePackage(request.getUserId(), request.getActCoursePkgList().get(i));
				}else {
					LearnerCourse lrCourseObj = new LearnerCourse();
					lrCourseObj.setUserId(request.getUserId());
					lrCourseObj.setCoursePackageId(request.getActCoursePkgList().get(i));
					learnerCourseRepo.save(lrCourseObj);
				}
			}
			
			//deActivation
			for(int i=0; i<request.getDeActCoursePkgList().size(); i++) {
				Optional<LearnerCourse> lrCourse = learnerCourseRepo.findByUserIdAndCoursePackageId(request.getUserId(), request.getDeActCoursePkgList().get(i));
				if(lrCourse.isPresent()) {
					learnerCourseRepo.deActivateLearnerCoursePackage(request.getUserId(), request.getDeActCoursePkgList().get(i));
				}
			}	
		}catch(Exception ex) {
			response.setExceptionReport(new ExceptionReport(ex));
		}
		return response;	
	}
	
	
	
	
	public List<Integer> _getSubscribedPackages(String userId){
		return learnerCourseRepo.findSubscribedCoursePackageId(userId, 1);
	}
	
	
}
