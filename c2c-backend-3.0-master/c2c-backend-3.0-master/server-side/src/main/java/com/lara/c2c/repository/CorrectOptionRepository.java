package com.lara.c2c.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.CorrectOption;

public interface CorrectOptionRepository extends JpaRepository<CorrectOption, Integer>{
	
	/*@Query("select co.id, co.answerKey from CorrectOption co where co.id in (:questionIdsString)")
	public List getActualQuestionAnsData(@Param("questionIdsString") String questionIdsString);*/
	
	List<CorrectOption> findByIdIn(Set<Integer> set);
}
