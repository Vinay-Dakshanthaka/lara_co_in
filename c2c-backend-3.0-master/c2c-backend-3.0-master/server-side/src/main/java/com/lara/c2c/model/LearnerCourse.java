package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.lara.c2c.utility.ServiceUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cb_learner_courses")
public class LearnerCourse{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="course_package_id")
	private int coursePackageId;
	
	@Column(name="status")
	private int status = 1;
	
	@Column(name = "enrollment_date")
	@CreationTimestamp
	private Date enrollmentDate = ServiceUtils.getCurrentDateTime();
}
