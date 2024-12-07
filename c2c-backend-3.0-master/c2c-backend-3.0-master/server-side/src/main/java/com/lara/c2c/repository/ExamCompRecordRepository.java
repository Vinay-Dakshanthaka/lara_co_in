package com.lara.c2c.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.exam.ExamCompDetailResponse;
import com.lara.c2c.dto.exam.ExamCompRecordResponse;
import com.lara.c2c.dto.exam.ExamPointDto;
import com.lara.c2c.dto.exam.FindExamRecordResponse;
import com.lara.c2c.dto.exam.ResultResponse;
import com.lara.c2c.dto.exam.TotalScoreResponse;
import com.lara.c2c.model.ExamCompletedRecord;

public interface ExamCompRecordRepository extends JpaRepository<ExamCompletedRecord, Integer>{

	List<ExamCompletedRecord> findByExamRecordId(int examRecordId);

	@Query("select NEW com.lara.c2c.dto.exam.ExamCompRecordResponse("	
			+ "cpg.coursePackageId, "
			+ "cpg.coursePackageName, "
			+ "cr.courseId, "
			+ "cr.courseName, "
			+ "tp.topicId,"
			+ "tp.topicName,"
			+ "stp.subTopicId,"
			+ "stp.subTopicName,"
			+ "mtp.microTopicId,"
			+ "mtp.microTopicName,"
			+ "count(ecr.examRecordId) as totalAttempts, "
			+ "sum(ecr.marksObtained) as marksObtained,"
			+ "sum(ecr.totalMarks) as totalMarks, "
			+ "ecr.examRecordId) "			
			+ " from ExamCompletedRecord ecr "
			+ " inner join ExamRecord ex on (ex.examRecordId = ecr.examRecordId) "
			+ " inner join MicroTopic mtp on (ex.microTopicId = mtp.microTopicId and ecr.userId = :userId) "
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId) "
			+ " inner join Topic tp on(tp.topicId = stp.topicId) "
			+ " inner join Course cr on(cr.courseId = tp.courseId) "
			+ " inner join MapCourseUnderPackage mcpg on mcpg.courseId = cr.courseId and mcpg.coursePackageId = ex.coursePackageId"
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = ex.coursePackageId) "
			+ " inner join LearnerCourse lc on(lc.coursePackageId = ex.coursePackageId and lc.userId=ecr.userId and lc.status = '1') "
			+ "group by ecr.examRecordId")	
			List<ExamCompRecordResponse> findGrpExCompRecByLrId(String userId);
	
	@Query("select NEW com.lara.c2c.dto.exam.ExamCompDetailResponse("	
			+ "cpg.coursePackageId, "
			+ "cpg.coursePackageName, "
			+ "cr.courseId, "
			+ "cr.courseName, "
			+ "tp.topicId,"
			+ "tp.topicName,"
			+ "stp.subTopicId,"
			+ "stp.subTopicName,"
			+ "mtp.microTopicId,"
			+ "mtp.microTopicName,"			
			+ "ecr.marksObtained,"
			+ "ecr.totalMarks, "
			+ "ecr.questionAnsData, "
			+ "ecr.examCompRecordId,"
			+ "ecr.completedDate, "
			+ "ecr.examRecordId) "			
			+ " from ExamCompletedRecord ecr "
			+ " inner join ExamRecord ex on (ex.examRecordId = ecr.examRecordId) "
			+ " inner join MicroTopic mtp on (ex.microTopicId = mtp.microTopicId ) "
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId) "
			+ " inner join Topic tp on(tp.topicId = stp.topicId) "
			+ " inner join Course cr on(cr.courseId = tp.courseId) "
			+ " inner join MapCourseUnderPackage mcpg on mcpg.courseId = cr.courseId "
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = ex.coursePackageId) "
			+ " where ecr.examRecordId = :exRecordId and ecr.userId = :userId ")
	List<ExamCompDetailResponse> findAllExCompRecByLrId(Integer exRecordId, String userId);
	
	@Query("select NEW com.lara.c2c.dto.exam.ResultResponse("
			+ "cpg.coursePackageName,"
			+ "cr.courseName,"
			+ "tp.topicName,"
			+ "stp.subTopicName,"
			+ "mtp.microTopicName,"
			+ "ecr.totalMarks, "
			+ "ecr.marksObtained,"
			+ "ecr.totalQuestions,"
			+ "ecr.questionAnsData)"
			+ " from ExamCompletedRecord ecr "
			+ " inner join ExamRecord ex on (ex.examRecordId = ecr.examRecordId) "
			+ " inner join MicroTopic mtp on (ex.microTopicId = mtp.microTopicId ) "
			+ " inner join SubTopic stp on (stp.subTopicId = mtp.subTopicId) "
			+ " inner join Topic tp on(tp.topicId = stp.topicId) "
			+ " inner join Course cr on(cr.courseId = tp.courseId) "
			+ " inner join MapCourseUnderPackage mcpg on (mcpg.courseId = cr.courseId and mcpg.coursePackageId = ex.coursePackageId) "
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = ex.coursePackageId) "
			+ " where ecr.examCompRecordId= :cexId and ecr.userId = :userId")
	ResultResponse findQuesAnsData(int cexId, String userId);

	@Query("select NEW com.lara.c2c.dto.exam.TotalScoreResponse("
			+ "sum(marksObtained) as marksObtained, "
			+" sum(totalMarks) as totalMarks)"
			+" from ExamCompletedRecord ecr where ecr.userId = :userId")
	TotalScoreResponse findMarksAndTotalQues(String userId);
	
	@Query("select NEW com.lara.c2c.dto.PointsResponse("
			+ "sum(examPoint) as point, "
			+ "max(completedDate) as lastUpdated)"
			+ " from ExamCompletedRecord ecr where ecr.userId = :userId")
	PointsResponse getTotalExamPoints(String userId);
     
	@Query("select NEW com.lara.c2c.dto.exam.ExamPointDto("
			+ " ecr.examPoint,"
			+ " min(ecr.completedDate) as completedDate) "
			+ " from ExamCompletedRecord ecr where ecr.microTopicId = :microTopicId and ecr.userId = :userId")
	ExamPointDto findByMicroTopicIdAndUserId(String microTopicId, String userId);

	Optional<List<ExamCompletedRecord>> findByUserId(String userId);
	
	
}
