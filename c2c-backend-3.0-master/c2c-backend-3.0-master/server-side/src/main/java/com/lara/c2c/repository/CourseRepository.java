package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.Course;
import com.lara.c2c.dto.course.CourseListDto;

public interface CourseRepository extends JpaRepository<Course, Integer>{

	public Course findByCourseId(Integer courseId);
	
	@Query("select new com.lara.c2c.dto.course.CourseListDto("
			+ "cr.courseId,"
			+ "cr.courseName) "			
			+ " from MapCourseUnderPackage mcpg "
			+ " inner join Course cr on(cr.courseId =  mcpg.courseId) "			
			+ " where mcpg.coursePackageId = :cpkgId ")
	public List<CourseListDto> findAllCoursesByCpkgId(Integer cpkgId);

}
