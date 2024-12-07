package com.lara.c2c.controller;

import java.util.Optional;

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

import com.lara.c2c.dto.skill.FindSkillLevelResponse;
import com.lara.c2c.model.SkillLevel;
import com.lara.c2c.service.SkillLevelService;

import com.lara.c2c.utility.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/skillLevel")
public class SkillLevelController {
	
	@Autowired
	private SkillLevelService skillLevelService;
	
	/*@GetMapping("/findAll")
	public ResponseEntity<FindSkillLevelResponse> retrieveAllSkillLevelData() {	
		
		FindSkillLevelResponse skillLevelResponse = new FindSkillLevelResponse();
		skillLevelResponse.setSkillLevelData(skillLevelService.getAllSkillLevel());
		
		if (Constants.isRequestFailed(skillLevelResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(skillLevelResponse);
		}
		return ResponseEntity.ok().body(skillLevelResponse);	
	}*/
	
	@PostMapping("/addSkillLevel")
	public ResponseEntity<Void> insertSkillLevelData(@RequestBody SkillLevel skillLevelRequest){
		skillLevelService.addSkillLevel(skillLevelRequest);
		return ResponseEntity.ok().build();			
	}
	
	@PutMapping("/updateSkillLevel/{id}")
	public ResponseEntity<Void> updateSkillData(@PathVariable Integer id, @RequestBody SkillLevel skillLevelRequest){
		Optional<SkillLevel> skillLevel = skillLevelService.getSkillLevel(id);
		if(skillLevel == null) {
			return ResponseEntity.notFound().build();
		}		
		skillLevelService.addSkillLevel(skillLevelRequest);
		return ResponseEntity.ok().build();		
	}
	
	@GetMapping("/getSkillLevel/{id}")
	public Optional<SkillLevel> retrieveSkillLevelData(@PathVariable Integer id) {			
		return skillLevelService.getSkillLevel(id);
	}
	
	@DeleteMapping("deleteSkillLevel/{id}")
	public ResponseEntity<Void> deleteSubPackRecord(@PathVariable Integer id){
		skillLevelService.deleteSkillLevel(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteAllSkillLevel")
	public ResponseEntity<Void> deleteAllSkillLevel(){
		skillLevelService.deleteAllSkillLevel();
		return ResponseEntity.ok().build();
	}
}
