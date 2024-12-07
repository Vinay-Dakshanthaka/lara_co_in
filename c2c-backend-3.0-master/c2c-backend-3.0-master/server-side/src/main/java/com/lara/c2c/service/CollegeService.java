package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.college.FindCollegeResponse;
import com.lara.c2c.model.College;
import com.lara.c2c.model.Learner;
import com.lara.c2c.repository.CollegeRepository;

@Service
public class CollegeService{
	
	@Autowired
	private CollegeRepository collegeRepo;

	public FindCollegeResponse _getCollegeList(){
		FindCollegeResponse response = new FindCollegeResponse();
		List<College> collegeRecords = new ArrayList<>();
		collegeRepo.findAll().forEach(collegeRecords::add);
		response.setCollegeList(collegeRecords);
		return response;
	}
}
