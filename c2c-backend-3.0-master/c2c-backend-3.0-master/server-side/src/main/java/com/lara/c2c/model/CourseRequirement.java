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
@Table(name="cb_course_requirements")

public class CourseRequirement{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_requirement_id")
	private int courseRequirementId;	
	
	@Column(name="course_requirement_desc")
	private String courseRequirementDesc;
}
