package com.lara.c2c.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.LearnerTotalPoint;

public interface LearnerTotalPointRepository extends JpaRepository<LearnerTotalPoint, Integer>{

	Optional<LearnerTotalPoint> findByUserId(String userId);

}
