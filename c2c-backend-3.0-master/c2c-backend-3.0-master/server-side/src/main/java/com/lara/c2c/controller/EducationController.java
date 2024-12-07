package com.lara.c2c.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lara.c2c.dto.learner.AddLearnerEduResp;
import com.lara.c2c.dto.learner.FindEduSpecResponse;
import com.lara.c2c.dto.learner.FindEducationResponse;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.service.EducationService;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/education")
public class EducationController{
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@GetMapping("/findAllEdu")
	public ResponseEntity<FindEducationResponse> getEducationList() {			
		FindEducationResponse eduResponse = new FindEducationResponse();
		eduResponse.setEducationResp(educationService._getEducationList());				
		return ResponseEntity.ok().body(eduResponse);	
	}
	
	@GetMapping("/findAllEduSpec")
	public ResponseEntity<FindEduSpecResponse> getEducationSpecList() {			
		FindEduSpecResponse eduSpecResponse = new FindEduSpecResponse();
		eduSpecResponse.setEduSpecList(educationService._getEducationSpecList());				
		return ResponseEntity.ok().body(eduSpecResponse);	
	}
	
	@GetMapping("/findAllEduSpecByEduId/{eduId}")
	public ResponseEntity<FindEduSpecResponse> getEduSpecListByEduId(@PathVariable Integer eduId){
		FindEduSpecResponse eduSpecResponse = new FindEduSpecResponse();
		eduSpecResponse.setEduSpecList(educationService._getEduSpecListByEduId(eduId));
		return ResponseEntity.ok().body(eduSpecResponse);	
	}
	
	@PostMapping("/addLearnerEducation")
	public ResponseEntity<AddLearnerEduResp> addLearnerEduRecords( @RequestBody List<LearnerEducation> learnerEduRequest){
		String userId = UserInfo.getUserId(hsRequest);
		AddLearnerEduResp response = educationService._addLearnerEduRecords(learnerEduRequest, userId); 		
		return ResponseEntity.ok().body(response);	
		
	}
	
	@DeleteMapping("/deleteEducation/{eduId}")
	public ResponseEntity<DeleteRecordResponse> deleteEducationRowByLearnerId(@PathVariable Integer eduId){
		String userId = UserInfo.getUserId(hsRequest);
		DeleteRecordResponse response = educationService._deleteEducationRowByLearnerId(userId, eduId);
		return ResponseEntity.ok().body(response);	
	}
	
	/*@GetMapping("/findAllLrEduRecords")
	public ResponseEntity<FindEducationResponse> getLrEduList() {			
		FindEducationResponse eduResponse = new FindEducationResponse();
		eduResponse.setEducationResp(educationService._getLrEduList());				
		return ResponseEntity.ok().body(eduResponse);	
	}*/

}
