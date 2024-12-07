package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.CourseContent;

	
public interface CourseContentRepository extends JpaRepository<CourseContent, Integer>{

	CourseContent findByCourseId(int courseId);
	
} 


