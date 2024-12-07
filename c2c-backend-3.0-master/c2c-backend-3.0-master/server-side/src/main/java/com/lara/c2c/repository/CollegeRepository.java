package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.College;

public interface CollegeRepository extends JpaRepository<College, Integer>{
	
	@Query("select collegeName from College c where c.collegeId = :collegeId")
	String getCollegeNameByCollegeId(int collegeId);

}
