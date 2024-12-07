package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lara.c2c.utility.ServiceUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cb_cum_exam_records")
@Getter
@Setter
@ToString

public class CumulativeExamRecord{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cum_exam_record_id")
	private int cumExamRecordId;
	
	@Column(name = "user_id")
	private String userId;	
	
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "course_package_id")
	private int coursePackageId;
	
	@Column(name = "total_questions")
	private int totalQuestions;
	
	@Column(name = "question_ans_data", columnDefinition="TEXT")
	private String questionAnsData;
	
	@Column(name = "total_marks")
	private double totalMarks;
	
	@Column(name = "marks_obtained")
	private double marksObtained;
	
	@Column(name = "percent_marks")
	private double percentMarks;	
	
	@Column(name = "status")
	private int status = 0;
	
	@Column(name="cum_exam_point")
	private double cumExamPoint;
	
	@Column(name = "exam_related_info")
	private String examRelatedInfo;
	
	@Column(name = "completed_date")
	private Date completedDate = ServiceUtils.getCurrentDateTime();
}
