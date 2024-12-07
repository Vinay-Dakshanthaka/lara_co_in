package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="save_general_practice")
public class LearnerProgram {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="program_content",length = 6000)
	private String program;
	
	@Column(name="note_by_learner")
	private String note;
	
	@Column(name="title")
	private String title;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="date_time")
	private Date dateTime;
	
//	@OneToMany(cascade=CascadeType.PERSIST)
//	private Learner learner;
}
