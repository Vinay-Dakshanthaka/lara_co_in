package com.lara.c2c.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="cb_exam_records")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExamRecord{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "exam_record_id")
	private int examRecordId;
	
	@Column(name = "course_package_id")
	private int coursePackageId;
	
	
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "video_id")
	private String videoId;
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "due_date")
	private Date dueDate;
	
	
	@Column(name="status")
	private int status;
	
	@OneToOne(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    @JoinColumn(name = "micro_topic_id", insertable=false, updatable=false)
    private MicroTopic microTopic;
		
}
