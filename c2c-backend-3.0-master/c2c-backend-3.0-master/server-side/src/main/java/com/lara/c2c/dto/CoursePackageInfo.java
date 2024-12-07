package com.lara.c2c.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoursePackageInfo {
	private Integer coursePackageId;
	private String coursePackageName;
	private Double coursePackagePrice;
	
	@Override
	public String toString() {
		return "coursePackageId: " + coursePackageId;
	}
}
