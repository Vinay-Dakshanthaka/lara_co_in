package com.lara.c2c.model;

import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cb_learner_work_experiences")
public class LearnerWorkExperience {
	
	@Id
	@Column(name="work_session_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int workSessionId;	
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "date_of_joining")
	private Date dateOfJoining;
	
	@Column(name = "date_of_exit")
	private Date dateOfExit;
	
	@Column(name = "joining_salary")
	private Double joiningSalary;
	
	@Column(name = "exit_salary")
	private Double exitSalary;

}
