package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.topic.MicroTopicDto;
import com.lara.c2c.model.MicroTopic;

public interface MicroTopicRepository extends JpaRepository<MicroTopic, String>{

	@Query("select NEW com.lara.c2c.dto.topic.MicroTopicDto("
			+ "mct.microTopicId,"
			+ "mct.microTopicName)"
			+ " from MicroTopic mct "					
			+ " where mct.microTopicId in :microTopicIds")
	List<MicroTopicDto> findMicroTopicsByMitIds(List<String> microTopicIds);
	
	@Query("select microTopicDuration from MicroTopic mt where mt.microTopicId = :microTopicId")
	String findVideoLengthByMctId(String microTopicId);
	

	@Query("select microTopicId from MicroTopic mt where mt.subTopicId in (select subTopicId from SubTopic st where st.topicId in (select topicId from Topic t where t.courseId = :courseId))")
	List<String> findMicroTopicIds(int courseId);

	
	//MicroTopicDTO

}
