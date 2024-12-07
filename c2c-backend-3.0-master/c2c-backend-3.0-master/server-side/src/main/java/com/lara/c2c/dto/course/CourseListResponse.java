package com.lara.c2c.dto.course;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseListResponse extends BaseResponse{	
	
	private List<CourseListDto> courseList = Collections.emptyList();
}
