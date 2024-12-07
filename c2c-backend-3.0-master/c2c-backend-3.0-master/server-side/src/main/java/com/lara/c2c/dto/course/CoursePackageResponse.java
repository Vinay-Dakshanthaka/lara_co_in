package com.lara.c2c.dto.course;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.CoursePackage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class CoursePackageResponse extends BaseResponse{
	
	private List<CoursePackage> coursePackageList = Collections.emptyList();
}
