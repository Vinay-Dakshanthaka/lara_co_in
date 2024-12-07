package com.lara.c2c.model;

import javax.persistence.Column;

public class BulkSubscriptionCoursePackages {
	private String email;
	private String activatedCoursePackageIds;
	private String deactivatedCoursePackageIds;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActivatedCoursePackageIds() {
		return activatedCoursePackageIds;
	}
	public void setActivatedCoursePackageIds(String activatedCoursePackageIds) {
		this.activatedCoursePackageIds = activatedCoursePackageIds;
	}
	public String getDeactivatedCoursePackageIds() {
		return deactivatedCoursePackageIds;
	}
	public void setDeactivatedCoursePackageIds(String deactivatedCoursePackageIds) {
		this.deactivatedCoursePackageIds = deactivatedCoursePackageIds;
	}
	@Override
	public String toString() {
		return "BulkSubscriptionCoursePackages [email=" + email + ", activatedCoursePackageIds="
				+ activatedCoursePackageIds + ", deactivatedCoursePackageIds=" + deactivatedCoursePackageIds + "]";
	}	
	
	
}
