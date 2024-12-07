package com.lara.c2c.dto.exam;

import java.util.Date;

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
public class ExamCompDetailResponse{
	
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
	private double marksObtained;
	private double totalMarks;
	private String questionAnsData;
	private int examCompRecordId;
	private Date completedDate;
	private int examRecordId;
}
