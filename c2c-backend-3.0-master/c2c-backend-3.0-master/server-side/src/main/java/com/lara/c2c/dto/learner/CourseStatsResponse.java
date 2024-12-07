package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseStatsResponse extends BaseResponse{
	
	private List<LearnerAverageMarks> learnerAvgMarksList  = Collections.emptyList();
	private List<LearnerCourseStats> learnerCourseStatsList = Collections.emptyList();
	
}
