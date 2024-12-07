package com.lara.c2c.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class ExamCompRecordResponse{
	
	private int coursePackageId;
	private String coursePackageName;
	private int courseId;
	private String courseName;
	private int topicId;
	private String topicName;
	private int subTopicId;
	private String subTopicName;
	private String microTopicId;
	private String microTopicName;
	private long totalAttempts;
	private double marksObtained;
	private double totalMarks;
	private int examRecordId;

}
