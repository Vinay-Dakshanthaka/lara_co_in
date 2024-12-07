package com.lara.c2c.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.LearnerSkill;

public interface LearnerSkillRepository extends JpaRepository<LearnerSkill, Integer>{

	List<LearnerSkill> findByUserId(String userId);

	@Modifying
    @Query("delete from LearnerSkill ls where ls.id = :skillId and ls.userId = :userId")
    @Transactional
	public void deleteLearnerSkill(Integer skillId, String userId);

}
