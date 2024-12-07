package com.lara.c2c.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.DeleteRecordResponse;
import com.lara.c2c.dto.learner.AddLearnerSkillResp;
import com.lara.c2c.dto.skill.FindSkillLevelResponse;
import com.lara.c2c.dto.skill.FindSkillsResponse;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.Skill;
import com.lara.c2c.service.SkillLevelService;
import com.lara.c2c.service.SkillService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/skills")
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private SkillLevelService skillLevelService;	
	
	@GetMapping("/findAllSkills")
	public ResponseEntity<FindSkillsResponse> getAllSkills() {	
		
		FindSkillsResponse skillsResponse = new FindSkillsResponse();
		skillsResponse.setSkillsData(skillService._getAllSkills());
		
		if (Constants.isRequestFailed(skillsResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(skillsResponse);
		}
		return ResponseEntity.ok().body(skillsResponse);	
	}
	
	@PostMapping("/addSkill")
	public ResponseEntity<Void> insertSkillData(@RequestBody Skill skillRequest){
		skillService.addSkill(skillRequest);
		return ResponseEntity.ok().build();			
	}
	
	@PutMapping("/updateSkill/{id}")
	public ResponseEntity<Void> updateSkillData(@PathVariable Integer id, @RequestBody Skill skillRequest){
		Optional<Skill> skill = skillService.getSkill(id);
		if(skill == null) {
			return ResponseEntity.notFound().build();
		}		
		skillService.addSkill(skillRequest);
		return ResponseEntity.ok().build();		
	}
	
	@GetMapping("/getSkill/{id}")
	public Optional<Skill> retrieveSkillData(@PathVariable Integer id) {			
		return skillService.getSkill(id);
	}
	
	@DeleteMapping("deleteSkill/{id}")
	public ResponseEntity<Void> deleteSubPackRecord(@PathVariable Integer id){
		skillService.deleteSkill(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteAllSkill")
	public ResponseEntity<Void> deleteAllSubPackRecord(){
		skillService.deleteAllSkill();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/findAllSkillLevel")
	public ResponseEntity<FindSkillLevelResponse> getAllSkillLevel() {	
		
		FindSkillLevelResponse skillLevelResponse = new FindSkillLevelResponse();
		skillLevelResponse.setSkillLevelData(skillService._getAllSkillLevel());
		
		if (Constants.isRequestFailed(skillLevelResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(skillLevelResponse);
		}
		return ResponseEntity.ok().body(skillLevelResponse);	
	}
	
	@PostMapping("/addLearnerSkills")
	public ResponseEntity<AddLearnerSkillResp> addLearnerskillRecords( @RequestBody List<LearnerSkill> learnerSkillRequest){								
		AddLearnerSkillResp response = skillService._addLearnerSkillRecords(learnerSkillRequest);
		return ResponseEntity.ok().body(response);			
	}
	
	@DeleteMapping("/deleteLearnerSkill/{skillId}")
	public ResponseEntity<DeleteRecordResponse> deleteSkillRowByLearnerId(@PathVariable Integer skillId){
		String userId = UserInfo.getUserId(hsRequest);
		DeleteRecordResponse response = skillService._deleteSkillRowByLearnerId(userId, skillId);
		return ResponseEntity.ok().body(response);	
	}
	
}
