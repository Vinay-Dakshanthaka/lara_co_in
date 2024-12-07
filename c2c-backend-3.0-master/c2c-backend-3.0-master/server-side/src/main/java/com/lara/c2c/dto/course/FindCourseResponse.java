package com.lara.c2c.dto.course;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindCourseResponse{
	
	private List<Course> courseList = Collections.emptyList();
}
