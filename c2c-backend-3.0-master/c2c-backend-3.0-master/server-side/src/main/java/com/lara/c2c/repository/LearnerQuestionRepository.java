package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lara.c2c.model.LearnerQuestion;




public interface LearnerQuestionRepository extends JpaRepository<LearnerQuestion, Integer> {

	List<LearnerQuestion> findByTopicIdAndLevelId(Integer topicId,Integer levelId);
}
