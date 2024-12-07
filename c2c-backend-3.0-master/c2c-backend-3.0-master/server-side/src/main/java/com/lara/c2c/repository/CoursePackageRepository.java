package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.CoursePackToMicroTopicResp;
import com.lara.c2c.dto.learner.FindLearnerCourseResponse;
import com.lara.c2c.model.CoursePackage;

public interface CoursePackageRepository extends JpaRepository<CoursePackage, Integer>{
	
	CoursePackage findByCoursePackageId(Integer coursePackageId);
	
	@Query("select new com.lara.c2c.dto.learner.FindLearnerCourseResponse("
			+ "lc.coursePackageId, "
			+ "cp.coursePackageName, "
			+ "cp.coursePackageDesc, "
			+ "mcp.courseId, "
			+ "c.courseName,"
			+ "c.courseThumbnail,"
			+ "c.isActive) "
			+ " from LearnerCourse lc "
			+ "inner join CoursePackage cp on cp.coursePackageId = lc.coursePackageId " 
			+ "inner join MapCourseUnderPackage mcp on mcp.coursePackageId = lc.coursePackageId "
			+ "inner join Course c on c.courseId = mcp.courseId "
			+ "where lc.userId = :userId and lc.status = '1'")			
	public List<FindLearnerCourseResponse> findCpackDetailsForLearnerId(String userId);

	@Query("select cp.coursePackagePrice from CoursePackage cp where cp.coursePackageId = :cPackId")
	double findCpackPrice(int cPackId);

	@Query("select new com.lara.c2c.dto.CoursePackToMicroTopicResp("
			+ "cp.coursePackageId,"
			+ "cp.coursePackageName,"
			+ "cs.courseId,"
			+ "cs.courseName,"
			+ "tp.topicId,"
			+ "tp.topicName,"
			+ "stp.subTopicId,"
			+ "stp.subTopicName,"
			+ "mtp.microTopicId,"
			+ "mtp.microTopicName) "
			+ " from MicroTopic mtp"
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId and mtp.microTopicId = :microTopicId)"
			+ " inner join Topic tp on tp.topicId = stp.topicId "
			+ " inner join Course cs on cs.courseId = tp.courseId "
			+ " inner join MapCourseUnderPackage mcp on mcp.courseId = cs.courseId "
			+ " inner join CoursePackage cp on cp.coursePackageId = mcp.coursePackageId ")
	public List<CoursePackToMicroTopicResp> findDetailsByMicroTopicId(String microTopicId);
	
//	public List<CoursePackage> findAllById()
	
	public List<CoursePackage> findAllByCoursePackageIdNotIn(List<Integer> coursePakcageIds);
	
	
}
