package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lara.c2c.dto.learner.LiveClassDTO;
import com.lara.c2c.model.LiveClass;

public interface LiveClassRepository extends CrudRepository<LiveClass, Integer>{
	
	
	@Query("select new com.lara.c2c.dto.learner.LiveClassDTO(liveClass.id, liveClass.dateAndTime, liveClass.coursePackageId, "
			+ "liveClass.liveUrl, liveClass.password, " +
		   "liveClass.topic, liveClass.dependents, liveClass.isPublic, liveClass.note) "
		   + "from LiveClass liveClass where liveClass.dateAndTime >= CURRENT_DATE")
	public List<LiveClassDTO> readAllLiveClasses();
	
	
	@Query("select new com.lara.c2c.dto.learner.LiveClassDTO(liveClass.id, liveClass.dateAndTime, liveClass.coursePackageId, "
			+ "liveClass.liveUrl, liveClass.password, " +
		   "liveClass.topic, liveClass.dependents, liveClass.isPublic, liveClass.note) "
		   + "from LiveClass liveClass inner join LearnerCourse learnerCourse on (liveClass.coursePackageId = learnerCourse.coursePackageId)  "
		   + "inner join Learner learner on (learner.userId = learnerCourse.userId) "
		   + "where learner.userId = :userId and learnerCourse.status != 0 and "
		   + "liveClass.dateAndTime >= CURRENT_DATE")
	public List<LiveClassDTO> readAllLiveClassesForOneLearner(String userId);
	
	
	
}
