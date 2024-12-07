package com.lara.c2c.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.LearnerEducation;

public interface LearnerEducationRepository extends JpaRepository<LearnerEducation, Integer>{
	
	public List<LearnerEducation> findByUserId(String userId);

	@Modifying
    @Query("delete from LearnerEducation le where le.id = :eduId and le.userId = :userId")
    @Transactional
	public void deleteLearnerEducation(Integer eduId, String userId);
}
