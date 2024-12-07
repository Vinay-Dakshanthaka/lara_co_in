package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.course.CourseListResponse;
import com.lara.c2c.dto.course.FindCourseResponse;
import com.lara.c2c.model.Course;
import com.lara.c2c.model.CourseContent;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.repository.CourseContentRepository;
import com.lara.c2c.repository.CourseRepository;

@Service
public class CourseService{
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private CourseContentRepository courseCntRepo;

	public Course _getCourseByCourseId(Integer courseId){		
		return courseRepo.findByCourseId(courseId);
	}

	public List<Course> _getAllCourses(){
		List<Course> courseRecords = new ArrayList<Course>();
		courseRepo.findAll().forEach(courseRecords::add);
		return courseRecords;
	}

	public CourseContent _getCourseContentByCourseId(int courseId){	
		return courseCntRepo.findByCourseId(courseId);
	}
	
	public CourseListResponse _getAllCourseByCpkg(int cpackId){
		CourseListResponse response = new CourseListResponse();
		try {
			response.setCourseList(courseRepo.findAllCoursesByCpkgId(cpackId));
		}catch(Exception ex) {		
			response.setExceptionReport(new ExceptionReport(ex));
		}
		return response;
	}
}
