package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.dto.learner.FindEduSpecResponse;
import com.lara.c2c.model.EducationSpecialization;

public interface EduSpecRepository extends JpaRepository<EducationSpecialization, Integer>{
	
	public List<EducationSpecialization> findByEducationId(Integer educationId);
}
