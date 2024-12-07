package com.lara.c2c.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="bootcamp_questions")
public class LearnerQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="program_description")
	private String programDescription;
	
	@Column(name="program_content",length = 6000)
	private String programContent;
	
	@Column(name="program_solution",length = 10000)
	private String programSolution;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	LearnerTopic topic;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	LearnerLevel level;

}
