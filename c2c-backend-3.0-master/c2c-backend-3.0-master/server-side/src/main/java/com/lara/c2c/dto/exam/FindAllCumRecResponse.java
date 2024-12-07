package com.lara.c2c.dto.exam;

import java.util.Date;

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
public class FindAllCumRecResponse extends BaseResponse{
	
	private int coursePackageId;
	private String coursePackageName;
	private int courseId;
	private String courseName;
	private int cumExamRecordId;
	private double totalMarks;
	private double marksObtained;	
	private int totalQuestions;
	private String questionAnsData;
	private Date completedDate;
}
