package com.lara.c2c.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lara.c2c.dto.tpo.LearnerCoursePackDto;
import com.lara.c2c.model.LearnerCourse;


public interface LearnerCourseRepository extends JpaRepository<LearnerCourse, Integer>{

	LearnerCourse findByCoursePackageIdAndUserId(int cpackId, String userId);
	
	@Query("select NEW com.lara.c2c.dto.tpo.LearnerCoursePackDto("	
			+ "lc.coursePackageId as coursePackageId, "
			+ "cpg.coursePackageName as coursePackageName, "
			+ "lc.enrollmentDate as enrollmentDate, "
			+ "lc.status as status) "			
			+ " from LearnerCourse lc"
			+ " inner join CoursePackage cpg on (cpg.coursePackageId = lc.coursePackageId) "			
			+ " where lc.userId = :userId ")
	List<LearnerCoursePackDto> findAllCoursePackByUserId(String userId);

	Optional<LearnerCourse> findByUserIdAndCoursePackageId(String userId, Integer coursePackageId);
	
	@Query("select coursePackageId from LearnerCourse lc where lc.userId = :userId and lc.status = :status")
	List<Integer> findSubscribedCoursePackageId(String userId, Integer status);
	
	@Modifying
    @Query("update LearnerCourse lc set lc.status = '1' where lc.userId = :userId and lc.coursePackageId = :coursePackageId")
    @Transactional
	void activateLearnerCoursePackage(String userId, Integer coursePackageId);
	
	@Modifying
    @Query("update LearnerCourse lc set lc.status = '0' where lc.userId = :userId and lc.coursePackageId = :coursePackageId")
    @Transactional
	void deActivateLearnerCoursePackage(String userId, Integer coursePackageId);
	
	public List<LearnerCourse> findAllByCoursePackageIdIn(List<Integer> coursePackageIds);
	
	public List<LearnerCourse> findAllByUserId(String userId);	
	

}
