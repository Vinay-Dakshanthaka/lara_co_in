package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.DeleteRecordResponse;
import com.lara.c2c.dto.learner.AddLearnerEduResp;
import com.lara.c2c.dto.learner.LearnerEduReqResp;
import com.lara.c2c.model.Education;
import com.lara.c2c.model.EducationSpecialization;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.LearnerAddress;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.repository.EduSpecRepository;
import com.lara.c2c.repository.EducationRepository;
import com.lara.c2c.repository.LearnerEducationRepository;

@Service
public class EducationService{
	
	@Autowired
	private EducationRepository eduRepo;
	
	@Autowired
	private EduSpecRepository eduSpecRepo;
	
	@Autowired
	private LearnerEducationRepository learnerEduRepo;
	
	public List<Education> _getEducationList(){  
		List<Education> educationRecords = new ArrayList<Education>();  
		eduRepo.findAll().forEach(educationRecords::add);  
	    return educationRecords;  
	} 
	
	public List<EducationSpecialization> _getEducationSpecList(){
		List<EducationSpecialization> eduSpecRecords = new ArrayList<EducationSpecialization>();
		eduSpecRepo.findAll().forEach(eduSpecRecords::add);
		return eduSpecRecords;
	}
	
	public List<EducationSpecialization> _getEduSpecListByEduId(Integer eduId){
		List<EducationSpecialization> eduSpecRecords = new ArrayList<EducationSpecialization>();
		eduSpecRepo.findByEducationId(eduId).forEach(eduSpecRecords::add);
		return eduSpecRecords;
	}
	
	public AddLearnerEduResp _addLearnerEduRecords(List<LearnerEducation> learnerEduRequest, String userId){
		AddLearnerEduResp response = new AddLearnerEduResp();
		try {
			for(LearnerEducation lrEduRecord: learnerEduRequest) {
				lrEduRecord.setUserId(userId);
				learnerEduRepo.save(lrEduRecord);
			}
		}catch(Exception ex) {
			System.out.println(ex);
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}	
		return response;
	}

	public DeleteRecordResponse _deleteEducationRowByLearnerId(String userId, Integer eduId){		
		DeleteRecordResponse response = new DeleteRecordResponse();
		try {
			learnerEduRepo.deleteLearnerEducation(eduId, userId);
			response.setDeleted(true);
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		return response;
	}
}
