package com.lara.c2c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lara.c2c.repository.CourseMaterialRepository;


@Service
public class CourseMaterialService {
	@Autowired
	private CourseMaterialRepository courseMaterialRepository;	
	
	public Iterable<String> readAll(Integer courseId){
		return courseMaterialRepository.readAllMaterialsForOneCourse(courseId);
	}
	
}
