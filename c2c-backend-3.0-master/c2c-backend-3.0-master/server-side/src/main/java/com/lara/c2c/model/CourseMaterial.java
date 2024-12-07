package com.lara.c2c.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CourseMaterial {
	@Id
	private Integer id;
	//@ManyToOne
	private Integer courseId;
	private String materialFileName;
}
