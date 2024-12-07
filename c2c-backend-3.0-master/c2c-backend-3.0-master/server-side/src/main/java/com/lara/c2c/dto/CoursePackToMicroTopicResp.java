package com.lara.c2c.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoursePackToMicroTopicResp{
	
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
}
