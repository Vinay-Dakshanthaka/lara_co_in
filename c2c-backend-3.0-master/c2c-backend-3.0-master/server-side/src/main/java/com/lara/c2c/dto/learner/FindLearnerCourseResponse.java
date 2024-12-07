package com.lara.c2c.dto.learner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindLearnerCourseResponse{
	
	private int coursePackageId;
	private String coursePackageName;
	private String coursePackageDesc;
	private int courseId;
	private String courseName;
	private String courseThumbnail;
	private String isActive;
}
