package com.lara.c2c.dto.exam;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamRecordDto {
	
	private int examRecordId;
	private int topicId;
	private String topicName;
	private int subTopicId;
	private String subTopicName;
	private String microTopicId;
	private String microTopicName;
	private String userId;
	private double totalMarks;
	private double marksObtained;
	private Date Created;
	
}
