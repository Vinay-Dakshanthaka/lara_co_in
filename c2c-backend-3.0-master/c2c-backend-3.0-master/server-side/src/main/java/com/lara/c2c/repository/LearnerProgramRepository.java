package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lara.c2c.model.LearnerProgram;



public interface LearnerProgramRepository extends JpaRepository<LearnerProgram, Integer> {

	public List<LearnerProgram> findAll();
}
