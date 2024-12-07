package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lara.c2c.model.LearnerSolution;

public interface LearnerLogicRepository extends JpaRepository<LearnerSolution, Integer> {

}
