package com.lara.c2c.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.LearnerTotalPoint;
import com.lara.c2c.repository.LearnerTotalPointRepository;
import com.lara.c2c.utility.UserInfo;

@Service
public class LearnerTotalPointService {
	
	@Autowired
	private LearnerTotalPointRepository lrTotalPointRepo;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	public void addOrUpdateTotUserPts(double points) {
		String userId = UserInfo.getUserId(hsRequest);
		double totalPoints = points;
		Optional<LearnerTotalPoint> learnerTotalPoint = lrTotalPointRepo.findByUserId(userId);
		LearnerTotalPoint learnerTotalPointObj = new LearnerTotalPoint();
		if(learnerTotalPoint.isPresent()) {
			totalPoints = learnerTotalPoint.get().getTotalPoint() + points;
			learnerTotalPointObj.setId(learnerTotalPoint.get().getId());			
		}
		learnerTotalPointObj.setUserId(userId);
		learnerTotalPointObj.setTotalPoint(totalPoints);
		lrTotalPointRepo.save(learnerTotalPointObj);
	}
}
