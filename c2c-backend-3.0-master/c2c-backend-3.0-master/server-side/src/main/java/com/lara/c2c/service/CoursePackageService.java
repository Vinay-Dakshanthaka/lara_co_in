package com.lara.c2c.service;

import java.util.List;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.CoursePackToMicroTopicResp;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.dto.learner.FindLearnerCourseResponse;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.model.MapCourseUnderPackage;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.repository.MapCoursePackageRepository;

@Service
@Transactional
public class CoursePackageService {

	@Autowired
	private CoursePackageRepository coursePackageRepository;

	@Autowired
	private LearnerCourseRepository learnerCourseRepo;

	@Autowired
	private MapCoursePackageRepository mapCpRepo;

	@Transient
	@Value("${tax.gst.percentage}")
	private Integer gstPercentage;

	public void addCoursePackage(CoursePackage coursePackage) {
		coursePackageRepository.save(coursePackage);
	}

	public List<MapCourseUnderPackage> getSkillPackages() {
		return mapCpRepo.findAll();
	}

	public List<CoursePackage> getCoursePackages() {
		return coursePackageRepository.findAll();
	}

	public CoursePackage getCoursePackageById(Integer coursePackageId) {
		CoursePackage cousePackage = coursePackageRepository.findByCoursePackageId(coursePackageId);
		cousePackage.setGstPercentage(gstPercentage);
		return coursePackageRepository.findByCoursePackageId(coursePackageId);
	}

	public List<FindLearnerCourseResponse> _getAllCpackForLearner(String userId) {
		List<FindLearnerCourseResponse> learnerCpackDetails = null;
		try {
			learnerCpackDetails = coursePackageRepository.findCpackDetailsForLearnerId(userId);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return learnerCpackDetails;
	}

	public double getCpackPriceByCpackId(int cPackId) {
		return coursePackageRepository.findCpackPrice(cPackId);
	}

	public boolean _checkCPackSubscription(int cpackId, String userId) {
		LearnerCourse lrc = learnerCourseRepo.findByCoursePackageIdAndUserId(cpackId, userId);
		if (lrc != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<CoursePackToMicroTopicResp> _getDetailsByMicroTopicId(String microTopicId) {
		List<CoursePackToMicroTopicResp> coursePackResponse = coursePackageRepository
				.findDetailsByMicroTopicId(microTopicId);
		return coursePackResponse;
	}

	public List<CoursePackage> readAllCoursePackages() {
		return (List<CoursePackage>) coursePackageRepository.findAll();
	}

	public List<CoursePackage> getCoursePackageByIds(List<Integer> packageIds) {
		return coursePackageRepository.findAllById(packageIds);
	}

	public ResponseEntity<SuccessStatus> updatePackageStatus(CoursePackage coursePack) {

		CoursePackage cPack = coursePackageRepository.findById(coursePack.getCoursePackageId()).get();

		cPack.setCoursePackageDesc(coursePack.getCoursePackageDesc());
		cPack.setCoursePackageDuration(coursePack.getCoursePackageDuration());
		cPack.setCoursePackageName(coursePack.getCoursePackageName());
		cPack.setCoursePackagePrice(coursePack.getCoursePackagePrice());
		cPack.setCoursePackageOriginalPrice(coursePack.getCoursePackageOriginalPrice());
		cPack.setCoursePackageOfferText(coursePack.getCoursePackageOfferText());
		cPack.setIsActive(coursePack.getIsActive());

		return ResponseEntity.ok().body(SuccessStatus.builder().message("Status Updated.").status(true).build());
	}
}
