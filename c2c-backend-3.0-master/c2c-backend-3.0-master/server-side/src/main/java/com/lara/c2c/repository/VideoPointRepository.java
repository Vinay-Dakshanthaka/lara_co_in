package com.lara.c2c.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.video.VideoPointDto;
import com.lara.c2c.model.VideoPoint;

public interface VideoPointRepository extends JpaRepository<VideoPoint, Integer>{
	
	@Query("select NEW com.lara.c2c.dto.PointsResponse("
			+ "sum(videoPoint) as point, "
			+ "max(videoWatchedDate) as lastUpdated) from VideoPoint vp where vp.userId = :userId")
	PointsResponse getTotalVideoPoints(String userId);
	
	@Query("select NEW com.lara.c2c.dto.video.VideoPointDto("
			+ " vp.videoPoint,"
			+ " max(vp.videoWatchedDate) as videoWatchedDate) "
			+ " from  VideoPoint vp where vp.microTopicId = :microTopicId and vp.userId = :userId")
	VideoPointDto findVideoPointRecord(@Param("microTopicId") String mciroTopicId, String userId);

	Optional<List<VideoPoint>> findByUserId(String userId);
}
