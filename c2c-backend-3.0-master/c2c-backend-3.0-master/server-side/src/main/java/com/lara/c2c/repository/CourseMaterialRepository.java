package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lara.c2c.model.CourseMaterial;


public interface CourseMaterialRepository extends CrudRepository<CourseMaterial, Integer>{

	
	@Query("select m.materialFileName from CourseMaterial m where m.courseId = :courseId")
	public Iterable<String> readAllMaterialsForOneCourse(Integer courseId);
}
