package com.lara.c2c.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cb_new_batches")
@Getter
@Setter
@ToString
public class NewBatch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="batch_title")
	private String batchTitle;
	@Column(name="batch_desc")
	private String batchDescr;
	@Column(name="batch_start_date")
	private String batchStartDate;
	
	@Column(name="batch_duration")
	private String batchDuration;
	@Column(name="batch_skills")
	private String batchSkills;
	@Column(name="batch_course_content_link")
	private String batchCourseContentLink;
	@Column(name="batch_timing")
	private String batchTiming;
	@Column(name="batch_prerequisites")
	private String batchPreRequisites;
	
	@Column(name="css_class")
	private String cssClass;
	
	@Column(name="batch_is_active")
	private Integer batchIsActive = new Integer(1);

}


