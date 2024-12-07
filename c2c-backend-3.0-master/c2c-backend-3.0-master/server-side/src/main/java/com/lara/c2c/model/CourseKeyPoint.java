package com.lara.c2c.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cb_course_key_points")
public class CourseKeyPoint{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_key_point_id")
	private int courseKeyPointId;
	
	@Column(name = "course_key_point_description")
	private String courseKeyPointDescription;
}
