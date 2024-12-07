package com.lara.c2c.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cb_course_packages")
public class CoursePackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_package_id")
	private int coursePackageId;

	@Column(name = "course_package_name")
	private String coursePackageName;

	@Column(name = "course_package_desc", columnDefinition = "TEXT")
	private String coursePackageDesc;

	@Column(name = "course_package_price")
	private double coursePackagePrice;

	@Column(name = "course_package_duration")
	private String coursePackageDuration;

	private double coursePackageOriginalPrice;

	private String coursePackageOfferText;

	@Column(name = "bg_class")
	private String bgClass;

	@Column(name = "is_active")
	private int isActive = 0;

	@OneToMany
	@JoinColumn(name = "course_package_id")
	private List<MapCourseUnderPackage> coursesUnderPackageList;

	@ManyToMany(mappedBy = "coursePackages")
	private Set<Cart> cartList;

	@Transient
	@Value("${tax.gst.percentage}")
	private Integer gstPercentage;

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((CoursePackage) obj).getCoursePackageId() == this.coursePackageId;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.coursePackageId;
	}

}
