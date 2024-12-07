package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.DeleteRecordResponse;
import com.lara.c2c.dto.learner.AddLearnerSkillResp;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.Skill;
import com.lara.c2c.model.SkillLevel;
import com.lara.c2c.repository.LearnerSkillRepository;
import com.lara.c2c.repository.SkillLevelRepository;
import com.lara.c2c.repository.SkillRepository;

@Service
public class SkillService {
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private LearnerSkillRepository learnerSkillRepo;
	
	public List<Skill> _getAllSkills(){  
	    List<Skill> skillRecords = new ArrayList<>();  
	    skillRepository.findAll().forEach(skillRecords::add);  
	    return skillRecords;  
	}

	@Autowired
	private SkillLevelRepository skillLevelRepository;	
	
	public List<SkillLevel> _getAllSkillLevel(){  
	    List<SkillLevel> skillLevelRecords = new ArrayList<>();  
	    skillLevelRepository.findAll().forEach(skillLevelRecords::add);  
	    return skillLevelRecords;  
	}
	
	public void addSkill(Skill skill) {
		skillRepository.save(skill);		
	}

	public Optional<Skill> getSkill(Integer id) {
		return skillRepository.findById(id);
	}

	public void deleteSkill(Integer id) {
		skillRepository.deleteById(id);
	}

	public void deleteAllSkill() {
		skillRepository.deleteAll();
	} 
	
	public AddLearnerSkillResp _addLearnerSkillRecords(List<LearnerSkill> learnerSkillRequest){
		AddLearnerSkillResp response = new AddLearnerSkillResp();
		try {
			for(LearnerSkill lrSkillRecord: learnerSkillRequest) {
				learnerSkillRepo.save(lrSkillRecord);
			}
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}		
		return response;
	}

	public DeleteRecordResponse _deleteSkillRowByLearnerId(String userId, Integer eduId){		
		DeleteRecordResponse response = new DeleteRecordResponse();
		try {
			learnerSkillRepo.deleteLearnerSkill(eduId, userId);
			response.setDeleted(true);
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		return response;
	}
}
