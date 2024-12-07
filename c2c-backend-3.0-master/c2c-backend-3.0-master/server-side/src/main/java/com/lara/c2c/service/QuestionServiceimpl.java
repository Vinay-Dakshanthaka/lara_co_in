package com.lara.c2c.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.oncmp.AddQuestionDTORequest;
import com.lara.c2c.dto.oncmp.SaveLearnerLogicDTO;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.LearnerLevel;
import com.lara.c2c.model.LearnerQuestion;
import com.lara.c2c.model.LearnerSolution;
import com.lara.c2c.model.LearnerTopic;
import com.lara.c2c.repository.LearnerLevelRepository;
import com.lara.c2c.repository.LearnerQuestionRepository;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.LearnerSolutionRepository;
import com.lara.c2c.repository.LearnerTopicRepository;
import com.lara.c2c.utility.ApiStatus;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.Utils;

@Service
public class QuestionServiceimpl {

	@Autowired
	private LearnerTopicRepository topicRepo;

	@Autowired
	private LearnerLevelRepository levelRepo;

	@Autowired
	private LearnerQuestionRepository questionRepo;

	@Autowired
	private LearnerSolutionRepository solutionRepo;

	@Autowired
	private LearnerRepository learnerRepo;
	
	
	public List<LearnerLevel> getAllLevel() {
		// TODO Auto-generated method stub
		return levelRepo.findAll();
	}


	public List<LearnerTopic> getAllTopic() {
		// TODO Auto-generated method stub
		return topicRepo.findAll();
	}


	public ApiStatus addQuestions(AddQuestionDTORequest questionDTO) {
		// TODO Auto-generated method stub
		LearnerLevel level = levelRepo.findById(questionDTO.getLevelId()).orElse(null);
		LearnerTopic topic = topicRepo.findById(questionDTO.getTopicId()).orElse(null);
		if(level!=null && topic!=null) {
			LearnerQuestion ques = new LearnerQuestion();
			ques.setLevel(level);
			ques.setTopic(topic);
			ques.setProgramContent(questionDTO.getQuestions());
			ques.setProgramDescription(questionDTO.getDescription());
			questionRepo.save(ques);
			return Utils.successStatus(Constants.QUESTION_ADDED);
		}
		return Utils.errorStatus(Constants.INVALID_ENTRY);
	}


	public List<LearnerQuestion> getQuestions(Integer topicId, Integer levelId) {
		// TODO Auto-generated method stub
		List<LearnerQuestion> list = questionRepo.findByTopicIdAndLevelId(topicId, levelId);
		return list;
	}

	
	public ApiStatus saveLearnerLogic(SaveLearnerLogicDTO solutionDTO) {
		// TODO Auto-generated method stub
		Learner learner = learnerRepo.findByUserId(solutionDTO.getLearnerId()).orElse(null);
		LearnerQuestion question = questionRepo.findById(solutionDTO.getQuestionId()).orElse(null);
		if(learner!=null && question!=null)
		{
			List<LearnerSolution> solutionList = learner.getLearnerSolution();
			LearnerSolution solution = new LearnerSolution();
			solution.setLearnerQuestion(question);
			byte[] actualByte= Base64.getDecoder().decode(solutionDTO.getSolution());
			String program= new String(actualByte);
			String logic = Utils.getLogic(program);
			String format= Base64.getEncoder().encodeToString(logic.getBytes());
			solution.setLearnerSolution(format);
			solutionList.add(solution);
			solutionRepo.save(solution);
			learnerRepo.save(learner);
			return Utils.successStatus(Constants.SAVE_SUCCESS);
			
		}
		
		return Utils.errorStatus(Constants.RECORD_NOT_FOUND);
	}

}
