package com.lara.c2c.dto.exam;

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
public class CumExamResultResponse{	
	private String coursePackageName;
	private String courseName;	
	private double totalMarks;
	private double marksObtained;
	private int totalQuestions;
	private String questionAnsData;	
}
