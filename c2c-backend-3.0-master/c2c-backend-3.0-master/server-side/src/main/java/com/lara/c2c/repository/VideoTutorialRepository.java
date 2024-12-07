package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.video.VideoPlayListResponse;
import com.lara.c2c.model.VideoTutorial;

public interface VideoTutorialRepository extends JpaRepository<VideoTutorial, String>{

	@Query("select new com.lara.c2c.dto.video.VideoPlayListResponse("
			+ "vt.videoId,"
			+ "vt.courseId,"
			+ "tp.topicId,"
			+ "tp.topicName,"
			+ "stp.subTopicId,"
			+ "stp.subTopicName,"
			+ "vt.microTopicId,"
			+ "mtp.microTopicName, "
			+ "vt.src,"
			+ "vt.title,"
			+ "vt.type) "
			+ " from VideoTutorial vt "
			+ " inner join MicroTopic mtp on (mtp.microTopicId = vt.microTopicId) "
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId) "
			+ " inner join Topic tp on(tp.courseId = vt.courseId and stp.topicId = tp.topicId) ")
	List<VideoPlayListResponse> getAllVideosByCourseId(int courseId);

}
