package com.lara.c2c.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="cb_courses")
public class Course{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name="course_name")
	private String courseName;	
	
	@Column(name="course_description1", columnDefinition="TEXT")
	private String courseDesc1;	
	
	@Column(name="course_description2", columnDefinition="TEXT")
	private String courseDesc2;	
	
	@Column(name="course_duration")
	private String courseDuration;
	
	@Column(name="no_of_articles")
	private int noOfArticles;
	
	@Column(name="course_thumbnail")
	private String courseThumbnail;
	
	@Column(name="is_active")
	private String isActive;
	
	@Column(name="course_material")
	private String courseMaterial;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)	
	@JoinColumn(name = "course_id", insertable=false,updatable=false)
    private Set<CourseRequirement> courseRequirements;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "course_id")
    private CourseContent courseContent;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)	
	@JoinColumn(name = "course_id", insertable=false,updatable=false)
    private Set<CourseKeyPoint> courseKeyPoints;
}
