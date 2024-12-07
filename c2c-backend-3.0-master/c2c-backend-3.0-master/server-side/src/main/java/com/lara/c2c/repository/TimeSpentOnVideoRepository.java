package com.lara.c2c.repository;



import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lara.c2c.model.TimeSpentOnVideo;


public interface TimeSpentOnVideoRepository extends JpaRepository<TimeSpentOnVideo, Integer>{

	//TimeSpentOnVideo findByVideoId(Integer videoId);
	
	@Modifying
	@Query("update TimeSpentOnVideo tsov set tsov.frequency = (:frequency) where tsov.videoId=(:videoId) and tsov.userId=(:userId)")
	@Transactional
	public int updateTimeSpentVideoRecord(@Param("videoId") String videoId, @Param("userId") String userId, @Param("frequency") Integer frequency);
	
	/*@Query("select tsov.microTopicId as microTopicId from TimeSpentOnVideo tsov where tsov.userId=(:userId)")	
	List<Integer> findMicroTopicIdsbyUserId(String userId);*/
	
	public List<TimeSpentOnVideo> findByUserId(String userId);

	TimeSpentOnVideo findByVideoIdAndUserIdAndCoursePackageId(String videoId, String userId, Integer coursePackageId);

	@Query("select tsov.microTopicId from TimeSpentOnVideo tsov where tsov.coursePackageId = :coursePackageId and tsov.userId = :userId")
	public List<String> findWatchedMtpsByCpkgIdAndUserId(int coursePackageId, String userId);
	
	@Query("select count(microTopicId) from TimeSpentOnVideo tsov where tsov.userId = :userId")
	public int findTotalCoveredMctsByUserId(String userId);
}
