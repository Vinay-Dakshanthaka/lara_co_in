package com.lara.c2c.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.exam.FindExamRecordResponse;
import com.lara.c2c.model.ExamRecord;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Integer>{

	List<ExamRecord> findByUserId(String userId);	
	
	@Query("select new com.lara.c2c.dto.exam.FindExamRecordResponse("
			+ "cpg.coursePackageId,"
			+ "cpg.coursePackageName,"
			+ "cr.courseId,"
			+ "cr.courseName,"
			+ "ex.examRecordId,"
			+ "tp.topicId,"
			+ "tp.topicName,"
			+ "stp.subTopicId,"
			+ "stp.subTopicName,"
			+ "mtp.microTopicId,"
			+ "mtp.microTopicName,"
			+ "ex.dueDate,"
			+ "ex.status) "			
			+ " from ExamRecord ex "
			+ " inner join MicroTopic mtp on (ex.microTopicId = mtp.microTopicId) "
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId) "
			+ " inner join Topic tp on(tp.topicId = stp.topicId and tp.courseId = stp.topicId) "
			+ " inner join Course cr on(cr.courseId = tp.courseId) "
			+ " inner join MapCourseUnderPackage mcpg on mcpg.courseId = cr.courseId "
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = ex.coursePackageId) "
			+ " inner join LearnerCourse lc on(lc.coursePackageId = ex.coursePackageId and lc.userId=ex.userId and lc.status = '1') "
			+ " where ex.status = '1' and ex.userId = :userId")
	List<FindExamRecordResponse> findLearnerExamRecords(String userId);

	@Modifying
	@Query("update ExamRecord exr set exr.status = (:status) where exr.userId = (:userId) and exr.examRecordId = (:examRecordId)")
	@Transactional
	void updateDueExamStatus(int status, int examRecordId, String userId);

	@Query("select count(examRecordId) from ExamRecord exr where exr.userId = :userId and exr.status = :status" )
	int findTotalDueExamsByUserId(String userId, int status);

	@Query("select microTopicId from ExamRecord exr where exr.examRecordId = :examRecordId")
	String getMicroTopicIdByExRecId(int examRecordId);

	@Query("select examRecordId from ExamRecord exr where exr.coursePackageId = :coursePackageId and exr.videoId = :videoId and exr.microTopicId = :microTopicId and exr.userId = :userId")
	int findExistingExamRecordId(int coursePackageId, String videoId, String microTopicId, String userId);
		
}
