package com.lara.c2c.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAndCoursePackagesInfo {
	
	private String userId;
	private List<CoursePackageInfo> coursePackagesInfo;
	private Long totalAmount;
	private double subTotal;
	private double gst;
	
	@Override
	public String toString() {
		return "userId: " + userId + " " + coursePackagesInfo;
	}
}
