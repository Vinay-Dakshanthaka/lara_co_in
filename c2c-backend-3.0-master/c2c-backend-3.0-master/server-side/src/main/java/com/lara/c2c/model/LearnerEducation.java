package com.lara.c2c.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "cb_learner_education")
public class LearnerEducation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
		
	@Column(name="user_id")
    private String userId;
	
	@Column(name = "education_id")
	private int educationId;
	
	@Column(name = "edu_specialization_id")
	private int eduSpecId;
	
	@Column(name = "percentage")
	private Integer percentage;
	
	@Column(name = "year_of_pass_out")
	private String yearOfPassOut;
	
	@Column(name = "board_name")
	private String boardName;			
	
	/*@OneToOne
	@JoinColumn(name="education_id", referencedColumnName="education_id", insertable=false, updatable=false)
	private Education education;
	
	@OneToOne
	@JoinColumn(name="edu_specialization_id", referencedColumnName="education_specialization_id", insertable=false, updatable=false)
	private EducationSpecialization educationSpecialization;*/

}
