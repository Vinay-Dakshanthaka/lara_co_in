package com.lara.c2c.dto.tpo;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;

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
public class LearnerDetailForTpo extends BaseResponse{
	
	private String userId;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String email;
	private int totalWatchedVideos;
	private int totalCoveredMcts;
	private int totalDueExams;
	private int totalScore;
	private String overallPerformance;
	
	private List<LearnerCoursePackDto> learnerCoursePackList = Collections.emptyList();
}
