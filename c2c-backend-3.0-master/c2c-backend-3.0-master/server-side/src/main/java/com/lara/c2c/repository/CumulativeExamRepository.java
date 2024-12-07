package com.lara.c2c.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.exam.CumExamPointDto;
import com.lara.c2c.dto.exam.CumExamResultResponse;
import com.lara.c2c.dto.exam.FindAllCumRecResponse;
import com.lara.c2c.model.CumulativeExamRecord;

public interface CumulativeExamRepository extends JpaRepository<CumulativeExamRecord, Integer>{

	CumulativeExamRecord findByUserIdAndStatus(String userId, int i);

	List<CumulativeExamRecord> findByCumExamRecordIdAndUserId(int cumExamRecId, String userId);
	
	@Query("select NEW com.lara.c2c.dto.exam.CumExamResultResponse("
			+ "cpg.coursePackageName,"
			+ "cr.courseName,"
			+ "cer.totalMarks, "
			+ "cer.marksObtained,"
			+ "cer.totalQuestions,"
			+ "cer.questionAnsData)"
			+ " from CumulativeExamRecord cer "		
			+ " inner join Course cr on(cr.courseId = cer.courseId) "
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = cer.coursePackageId) "
			+ " where cer.cumExamRecordId= :cumExamRecordId and cer.userId = :userId")
	CumExamResultResponse findCumQuesAnsData(int cumExamRecordId, String userId);
	
	@Query("select NEW com.lara.c2c.dto.exam.FindAllCumRecResponse("
			+"cer.coursePackageId,"
			+ "cpg.coursePackageName,"
			+ "cer.courseId,"
			+ "cr.courseName,"
			+ "cer.cumExamRecordId, "
			+ "cer.totalMarks, "
			+ "cer.marksObtained,"
			+ "cer.totalQuestions,"
			+ "cer.questionAnsData,"
			+ "cer.completedDate)"
			+ " from CumulativeExamRecord cer "		
			+ " inner join Course cr on(cr.courseId = cer.courseId) "
			+ " inner join CoursePackage cpg on(cpg.coursePackageId = cer.coursePackageId) "
			+ " inner join LearnerCourse lc on(lc.coursePackageId = cer.coursePackageId and lc.userId = :userId and lc.status = '1') "
			+ " where cer.userId = :userId and cer.status = :status")
	List<FindAllCumRecResponse> getAllCumExRecordsByUserId(String userId, int status);

	CumulativeExamRecord findByCumExamRecordId(int cumExamRecordId);

	@Query("select NEW com.lara.c2c.dto.PointsResponse("
			+ "sum(cumExamPoint) as point, "
			+ "max(completedDate) as lastUpdated)"
			+ " from CumulativeExamRecord ecr where ecr.userId = :userId")
	PointsResponse getTotalCumExamPoints(String userId);
	
	Optional<List<CumulativeExamRecord>> findByUserId(String userId);
	
	@Query("select NEW com.lara.c2c.dto.exam.CumExamPointDto("
			+ " cer.cumExamPoint,"
			+ " cer.examRelatedInfo as examRelatedInfo, "
			+ " max(cer.completedDate) as completedDate) "
			+ " from  CumulativeExamRecord cer where cer.userId = :userId")
	CumExamPointDto getLastCumExamPointRecord(String userId);

}
