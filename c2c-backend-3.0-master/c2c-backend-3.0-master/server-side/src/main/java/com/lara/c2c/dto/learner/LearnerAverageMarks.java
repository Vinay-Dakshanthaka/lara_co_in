package com.lara.c2c.dto.learner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LearnerAverageMarks {
	
	private double totalAverageMarks;
	private double totalMarks;
	private Integer courseId;
	private String courseName;
	private Integer coursePackageId;
	private String coursePackageName;
}
