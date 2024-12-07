package com.lara.c2c.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.course.CourseListResponse;
import com.lara.c2c.dto.course.CoursePackageResponse;
import com.lara.c2c.dto.course.FindCourseResponse;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.dto.learner.FindLearnerCourseResponse;
import com.lara.c2c.model.Course;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.MapCourseUnderPackage;
import com.lara.c2c.service.CoursePackageService;
import com.lara.c2c.service.CourseService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cPackage")
public class CoursePackageController {
	
	@Autowired
	private CoursePackageService coursePackageService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@PostMapping("/addNotify")
	public ResponseEntity<Void> addCoursePackageData(@RequestBody CoursePackage coursePackageRequest){		
		coursePackageService.addCoursePackage(coursePackageRequest);
		return ResponseEntity.ok().build();		
	}
	
	@GetMapping("/findAll")
	public List<MapCourseUnderPackage> findAllSkillPackages(){		
		return coursePackageService.getSkillPackages();
	}
	
	@GetMapping("/findAllC")
	public ResponseEntity<CoursePackageResponse> findAllCoursePackages(){	
		CoursePackageResponse cPackResponse = new CoursePackageResponse();
		cPackResponse.setCoursePackageList(coursePackageService.getCoursePackages());
		if (Constants.isRequestFailed(cPackResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cPackResponse);
		}
		return ResponseEntity.ok().body(cPackResponse);	
	}
	
	@GetMapping("/findCoursePackage/{coursePackageId}")
	public CoursePackage findCoursePackageById(@PathVariable Integer coursePackageId){		
		return coursePackageService.getCoursePackageById(coursePackageId);
	}	
	
	@GetMapping("/findAllCourses")
	public ResponseEntity<FindCourseResponse> getAllCourses(){
		FindCourseResponse courseResponse = new FindCourseResponse();
		courseResponse.setCourseList(courseService._getAllCourses());
		return ResponseEntity.ok().body(courseResponse);
	}
	
	@GetMapping("/findCourse/{courseId}")
	public ResponseEntity<Course> getCourseByCourseId(@PathVariable Integer courseId){
		return ResponseEntity.ok().body(courseService._getCourseByCourseId(courseId));			
	}
	
	@GetMapping("/findAllCpackForLearner")
	public ResponseEntity<List<FindLearnerCourseResponse>> getAllCpackForLearner(){
		String userId = UserInfo.getUserId(hsRequest);
		List<FindLearnerCourseResponse> learnerCourseResponse = coursePackageService._getAllCpackForLearner(userId);				
		return ResponseEntity.ok().body(learnerCourseResponse);		
	}
	
	@GetMapping("/checkPSubscription/{cpackId}")
	public ResponseEntity<Boolean> checkCPackSubscription(@PathVariable int cpackId){
		String userId = UserInfo.getUserId(hsRequest);
		boolean isSubscribed = coursePackageService._checkCPackSubscription(cpackId, userId);
		return ResponseEntity.ok(isSubscribed);
	}
	
	@GetMapping("/findAllCourseByCpkg/{cpackId}")
	public ResponseEntity<CourseListResponse> getAllCourseByCpkg(@PathVariable int cpackId){
		CourseListResponse response = courseService._getAllCourseByCpkg(cpackId);
		return ResponseEntity.ok().body(response);	
	}
	
	@GetMapping("/readAllCoursePackages")
	public List<CoursePackage> readAllCoursePackages(){
		return (List<CoursePackage>) coursePackageService.readAllCoursePackages();
	}
	
	@PostMapping("getAllCoursesByIds")
	public ResponseEntity<List<CoursePackage>> getAllCoursesByIds(@RequestParam List<Integer> packageIds){
		return ResponseEntity.ok().body(coursePackageService.getCoursePackageByIds(packageIds));
	}
	
	@PutMapping("updatePackage")
	public ResponseEntity<SuccessStatus> updatePackageStatus(@RequestBody CoursePackage coursePack){
		return coursePackageService.updatePackageStatus(coursePack);
	}
}
