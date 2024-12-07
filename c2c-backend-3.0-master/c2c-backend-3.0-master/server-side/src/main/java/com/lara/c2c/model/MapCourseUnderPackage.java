package com.lara.c2c.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "map_courses_under_package")
public class MapCourseUnderPackage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "map_course_package_id")
	private int mapCoursePackageId;
	
	@Column(name="course_package_id")
	private int coursePackageId;	
	
	@Column(name = "course_id")
	private int courseId;

	@OneToOne
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private Course courses;
	
}