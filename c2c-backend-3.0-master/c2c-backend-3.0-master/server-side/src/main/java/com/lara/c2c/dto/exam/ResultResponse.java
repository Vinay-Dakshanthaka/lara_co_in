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
public class ResultResponse{	
	private String coursePackageName;
	private String courseName;
	private String topicName;
	private String subTopicName;
	private String microTopicName;
	private double totalMarks;
	private double marksObtained;
	private int totalQuestions;
	private String questionAnsData;	
}
