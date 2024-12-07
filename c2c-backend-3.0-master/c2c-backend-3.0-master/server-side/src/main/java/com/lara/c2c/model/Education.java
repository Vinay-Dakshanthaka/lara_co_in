package com.lara.c2c.model;

import java.util.List;
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
@Table(name = "lov_education")
public class Education {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="education_id")
	private int educationId;
	
	@Column(name = "education_name")
	private String educationName;
	
	@Column(name = "education_desc")
	private String educationDesc;
	
	@OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	@JoinColumn(name = "education_id", insertable=false,updatable=false)
    private List<EducationSpecialization> educationSpecializations;
	
}
