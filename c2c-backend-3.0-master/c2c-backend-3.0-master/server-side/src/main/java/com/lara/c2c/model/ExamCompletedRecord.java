package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="cb_exam_completed_records")
@Getter
@Setter
@ToString

public class ExamCompletedRecord{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_comp_record_id")
	private int examCompRecordId;
	
	@Column(name = "user_id")
	private String userId;	
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "exam_point")
	private double examPoint;
	
	@Column(name = "exam_record_id")
	private int examRecordId;
	
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
	
	@Column(name = "completed_date")
	private Date completedDate;	
	
//	@OneToOne(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
//    @JoinColumn(name = "micro_topic_id", insertable=false, updatable=false)
//    private MicroTopic microTopic;
	
}
