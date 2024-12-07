package com.lara.c2c.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.DeleteRecordResponse;
import com.lara.c2c.dto.learner.AddLearnerWorkExpResp;
import com.lara.c2c.dto.learner.LearnerWorkExpReqResp;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.LearnerWorkExperience;
import com.lara.c2c.service.WorkExperienceService;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/workexp")
public class WorkExperienceController{
	
	@Autowired
	private WorkExperienceService workExpService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@GetMapping("/findAllWorkExp")
	public ResponseEntity<LearnerWorkExpReqResp> getAllWorkExp() {	
		
		LearnerWorkExpReqResp workExpRes = new LearnerWorkExpReqResp();
		workExpRes.setLearnerWorkExpList(workExpService._getAllWorkExp());			
		return ResponseEntity.ok().body(workExpRes);	
	}
	
	@PostMapping("/addWorkExp")
	public ResponseEntity<AddLearnerWorkExpResp> addLearnerWorkExpRecords( @RequestBody List<LearnerWorkExperience> learnerWorkExpRequest){								
		AddLearnerWorkExpResp response = workExpService._addLearnerWorkExpRecords(learnerWorkExpRequest);
		return ResponseEntity.ok().body(response);			
	}
	
	@DeleteMapping("/deleteLearnerWorkExp/{workExpId}")
	public ResponseEntity<DeleteRecordResponse> deleteWorkExpRowByLearnerId(@PathVariable Integer workExpId){
		String userId = UserInfo.getUserId(hsRequest);
		DeleteRecordResponse response = workExpService._deleteWorkExpRowByLearnerId(userId, workExpId);
		return ResponseEntity.ok().body(response);	
	}
}
