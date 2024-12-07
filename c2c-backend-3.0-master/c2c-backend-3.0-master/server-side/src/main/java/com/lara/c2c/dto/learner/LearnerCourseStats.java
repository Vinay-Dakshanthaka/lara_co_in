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
public class LearnerCourseStats {
	
	private Integer totalVideosWatched;
	private Integer totalExamsAttended;
	private Integer totalExamsDue;
	private Integer courseId;
	private String courseName;
	private Integer coursePackageId;
	private String coursePackageName;
	
}
