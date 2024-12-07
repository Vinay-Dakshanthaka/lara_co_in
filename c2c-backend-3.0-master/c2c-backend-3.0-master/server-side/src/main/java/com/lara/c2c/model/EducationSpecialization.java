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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "lov_education_specialization")
public class EducationSpecialization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="education_specialization_id")
	private int eduSpecId;
	
	@Column(name="education_id")
	private int educationId;
	
	@Column(name = "education_specialization_name")
	private String eduSpecName;
	
	@Column(name = "education_specialization_desc")
	private String eduSpecDesc;
	
}