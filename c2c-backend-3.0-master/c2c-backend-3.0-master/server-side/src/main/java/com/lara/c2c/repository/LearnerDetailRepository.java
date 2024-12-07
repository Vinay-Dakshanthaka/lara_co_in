package com.lara.c2c.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lara.c2c.model.LearnerDetail;

public interface LearnerDetailRepository extends JpaRepository<LearnerDetail, Integer>{

	@Modifying
	@Query("update LearnerDetail ld set ld.lastName = (:lastName), ld.gender = (:gender), ld.dateOfBirth= (:dateOfBirth) where ld.userId=(:userId)")
	@Transactional
	public void updateLearnerProfileDetail(@Param("userId") String userId, @Param("lastName") String lastName, @Param("gender") String gender, @Param("dateOfBirth") Date dateOfBirth);
	
}
