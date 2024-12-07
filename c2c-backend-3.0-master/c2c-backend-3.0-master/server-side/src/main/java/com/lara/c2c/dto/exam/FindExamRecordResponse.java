package com.lara.c2c.dto.exam;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FindExamRecordResponse{
	
	//private List<ExamRecord> examRecords = Collections.emptyList();
	private int coursePackageId;
	private String coursePackageName;
	private int courseId;
	private String courseName;
	private int examRecordId;
	private int topicId;
	private String topicName;
	private int subTopicId;
	private String subTopicName;
	private String microTopicId;	
	private String microTopicName;
	private Date dueDate;
	private int status;
}
