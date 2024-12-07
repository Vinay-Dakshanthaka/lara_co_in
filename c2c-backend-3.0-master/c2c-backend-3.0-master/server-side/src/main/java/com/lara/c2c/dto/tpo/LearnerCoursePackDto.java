package com.lara.c2c.dto.tpo;

import java.util.Date;

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

public class LearnerCoursePackDto{
	
	private int coursePackageId;
	private String coursePackageName;
	private Date enrollmentDate;
	private int status;
}
