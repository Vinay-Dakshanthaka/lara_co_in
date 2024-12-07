package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.LearnerWorkSkill;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public interface LearnerWorkSkillRepository extends JpaRepository<LearnerWorkSkill, Integer>{

	List<LearnerWorkSkill> findByUserId(String userId);

}
