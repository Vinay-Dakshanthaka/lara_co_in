package com.lara.c2c.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.oncmp.ProgramSaveDTORequest;
import com.lara.c2c.dto.oncmp.SaveLearnerLogicDTO;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.LearnerProgram;
import com.lara.c2c.model.LearnerQuestion;
import com.lara.c2c.model.LearnerSolution;
import com.lara.c2c.repository.LearnerLogicRepository;
import com.lara.c2c.repository.LearnerProgramRepository;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.utility.UserInfo;


@Service
public class LearnerServiceImpl {

	@Autowired
	private LearnerProgramRepository learnerProgramRepo;
	
	@Autowired
	private LearnerLogicRepository learnerLogicRepo;
	
	@Autowired
	private LearnerRepository learnerRepository;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	
	public List<LearnerProgram> getLearnerById(String id) {
		// TODO Auto-generated method stub
		Learner learner = learnerRepository.findByUserId(id).orElse(null);
		return (learner!=null)?learner.getLearnerProgram():null;
	}

	
	public List<LearnerProgram> saveLearnerProgram(ProgramSaveDTORequest prog) {
		// TODO Auto-generated method stub
		//System.out.println(1111 + ":" + prog.getLearnerId());
		String userId = UserInfo.getUserId(hsRequest);
	
		//System.out.println(1112 + ":" + userId);
		Learner learner = learnerRepository.findByUserId(userId).orElse(null);
		//System.out.println(2222 + ":" + learner);
		List<LearnerProgram> learnerProgram = learner.getLearnerProgram();
		//System.out.println("learner:" + learner);
		if(learner!=null)
		{
			LearnerProgram lp = new LearnerProgram();
			lp.setNote(prog.getNotes());
			lp.setProgram(prog.getProgram());
			lp.setTitle(prog.getTitle()); 
			lp.setDateTime(new Date());
			learnerProgram.add(lp);
			learner.setLearnerProgram(learnerProgram);
			learnerProgramRepo.save(lp);
			learnerRepository.save(learner);
			
		}
		
		return learner.getLearnerProgram();
		
	}
	
	/*
	public List<LearnerSolution> saveLearnerLogic(SaveLearnerLogicDTO prog) {
		// TODO Auto-generated method stub
		System.out.println(1111 + ":" + prog.getLearnerId());
		String userId = UserInfo.getUserId(hsRequest);
	
		System.out.println(1112 + ":" + userId);
		Learner learner = learnerRepository.findByUserId(userId).orElse(null);
		List<LearnerSolution> learnerLogic = learner.getLearnerSolution();
		if(learner!=null)
		{
			LearnerSolution ls = new LearnerSolution();
			ls.setLearnerSolution(prog.getSolution());
			ls.setLearnerId(prog.getLearnerId());
			ls.setLearnerQuestionId(prog.getQuestionId());
			
			learnerLogicRepo.save(ls);
			learnerRepository.save(learner);
			
		}		
		return learner.getLearnerSolution();
	}
	*/
	
	
	public List<LearnerProgram> updateLearnerProgram(ProgramSaveDTORequest prog, Integer id) {
		// TODO Auto-generated method stub
		LearnerProgram lp = learnerProgramRepo.findById(id).orElse(null);
		if(lp!=null)
		{
			lp.setNote(prog.getNotes());
			lp.setProgram(prog.getProgram());
			lp.setTitle(prog.getTitle());
			lp.setDateTime(new Date());
			learnerProgramRepo.save(lp);
		}
		//System.out.println("lp "+lp);
		Learner learner = learnerRepository.findById(prog.getLearnerId()).orElse(null);
		System.out.println("learner "+learner);
		return (learner!=null)?learner.getLearnerProgram():null;
	}

}
