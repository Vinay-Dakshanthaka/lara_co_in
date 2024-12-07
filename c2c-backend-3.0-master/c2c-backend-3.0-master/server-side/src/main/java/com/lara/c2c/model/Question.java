package com.lara.c2c.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

@Entity
@Table(name="question")
public class Question {
	
	@Id
	@Column(name="question_id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int questionId;
	
	@Column(name = "micro_topic_id") 
	private String microTopicId;
	
	@Column(name = "level_id")
	private int levelId;
	
	@Column(name = "video_id")
	private String videoId;
	
	@Column(name = "questionDesc")
	@Type(type="text")
	private String questionDesc;
	
	@Column(name = "no_of_marks_allocated")
	private int marksAllocated; 
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="question_id")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Option> options;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="question_id")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CorrectOption> correctOptions;
	
}