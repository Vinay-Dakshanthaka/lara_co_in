package com.lara.c2c.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cb_course_content")
public class CourseContent{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "course_content_id")
	private int courseContentId;
	
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "course_content_description")
	private String courseContentDescription;
	
	@Column(name = "course_content_url")
	private String courseContentUrl;
}
