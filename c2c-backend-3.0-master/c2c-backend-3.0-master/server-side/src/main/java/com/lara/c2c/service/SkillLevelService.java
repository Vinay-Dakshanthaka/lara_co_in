package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.SkillLevel;
import com.lara.c2c.repository.SkillLevelRepository;

@Service
public class SkillLevelService {

	@Autowired
	private SkillLevelRepository skillLevelRepository;	
	
	/*public List<SkillLevel> getAllSkillLevel(){  
	    List<SkillLevel> skillLevelRecords = new ArrayList<>();  
	    skillLevelRepository.findAll().forEach(skillLevelRecords::add);  
	    return skillLevelRecords;  
	}*/

	public void addSkillLevel(SkillLevel skillLevel) {
		skillLevelRepository.save(skillLevel);
	}

	public Optional<SkillLevel> getSkillLevel(Integer id) {
		return skillLevelRepository.findById(id);
	}

	public void deleteSkillLevel(Integer id) {
		skillLevelRepository.deleteById(id);		
	}

	public void deleteAllSkillLevel() {
		skillLevelRepository.deleteAll();
	}
	

}
