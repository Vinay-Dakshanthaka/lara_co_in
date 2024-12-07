package com.lara.c2c.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
@Entity
@Table(name = "question_options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "option_id")
	private int optionId;
	
	@Column(name = "option_abbreviation")
	private char optionAbbr;
	
	@Column(name = "option_description", columnDefinition="TEXT")
	private String optionDesc;
	
    //private int question_id;	

}
