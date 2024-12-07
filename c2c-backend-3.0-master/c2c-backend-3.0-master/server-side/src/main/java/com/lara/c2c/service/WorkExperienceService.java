package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.DeleteRecordResponse;
import com.lara.c2c.dto.learner.AddLearnerWorkExpResp;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.LearnerWorkExperience;
import com.lara.c2c.repository.LearnerWorkExpRepository;

@Service
public class WorkExperienceService{
	
	@Autowired
	private LearnerWorkExpRepository learnerWorkExpRepo;
	
	public List<LearnerWorkExperience> _getAllWorkExp(){  
	    List<LearnerWorkExperience> workExpRecords = new ArrayList<>();  
	    learnerWorkExpRepo.findAll().forEach(workExpRecords::add);  
	    return workExpRecords;  
	}
	
	public AddLearnerWorkExpResp _addLearnerWorkExpRecords(List<LearnerWorkExperience> learnerWorkExpRequest){
		AddLearnerWorkExpResp response = new AddLearnerWorkExpResp();
		try {
			for(LearnerWorkExperience lrWorkExpRecord: learnerWorkExpRequest) {
				learnerWorkExpRepo.save(lrWorkExpRecord);
			}
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
			System.out.println(ex);
		}		
		return response;
	}
	
	public DeleteRecordResponse _deleteWorkExpRowByLearnerId(String userId, Integer workExpId){		
		DeleteRecordResponse response = new DeleteRecordResponse();
		try {
			learnerWorkExpRepo.deleteLearnerWorkExp(workExpId, userId);
			response.setDeleted(true);
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		return response;
	}

}
