package com.lara.c2c.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.LearnerWorkExperience;

public interface LearnerWorkExpRepository extends JpaRepository<LearnerWorkExperience, Integer>{
	
	public List<LearnerWorkExperience> findByUserId(String userId);
	
	@Modifying
    @Query("delete from LearnerWorkExperience lw where lw.workSessionId = :workExpId and lw.userId = :userId")
    @Transactional
	public void deleteLearnerWorkExp(Integer workExpId, String userId);
}
