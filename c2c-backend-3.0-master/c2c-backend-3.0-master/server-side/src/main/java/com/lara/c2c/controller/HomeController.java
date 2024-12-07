package com.lara.c2c.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.FindHomeElementsResponse;
import com.lara.c2c.dto.FindTestimonialResponse;
import com.lara.c2c.dto.college.FindCollegeResponse;
import com.lara.c2c.dto.course.CoursePackageResponse;
import com.lara.c2c.dto.course.FindBannersResponse;
import com.lara.c2c.model.BusinessPromoter;
import com.lara.c2c.model.Enquery;
import com.lara.c2c.service.CollegeService;
import com.lara.c2c.service.CoursePackageService;
import com.lara.c2c.service.HomeService;
import com.lara.c2c.service.TestimonialService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {
	
//	@GetMapping(value = "/**/{[path:[^\\.]*}")
//	public String goToHome(){
//	return "index.html";
//}
//@RequestMapping(value = "/{path:[^\\.]+}/**")
//public String redirect() {
//  return "forward:/";
//}
	@Autowired
	private HomeService homeService;
	
	@Autowired 
	private CoursePackageService coursePackService;
	
	@Autowired
	private CollegeService collegeService;
	
	@Autowired 
	private TestimonialService testimonialSrvice;
	
	@GetMapping("/findHomeElements")
	public ResponseEntity<FindHomeElementsResponse> retrieveAllRecruitersData() {	
		FindHomeElementsResponse homeElResponse = new FindHomeElementsResponse();
		Map<String, List> responseMap = new LinkedHashMap<String, List>();
		
		responseMap.put("recruitersData", homeService.getRecruiterRecords());
		responseMap.put("coursePackageList", coursePackService.getCoursePackages());
		responseMap.put("bannersData", homeService.getBannerRecords());
		
		homeElResponse.setHomeElements(responseMap);
		
		/*if (Constants.isRequestFailed(recruitersResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(homeElResponse);
		}*/						
		return ResponseEntity.ok().body(homeElResponse);	
	}
	
	
	@GetMapping("/findCoursePackageList")
	public ResponseEntity<CoursePackageResponse> getCoursePackageList() {	
		CoursePackageResponse response = new CoursePackageResponse();
		response.setCoursePackageList(coursePackService.getCoursePackages());							
		return ResponseEntity.ok().body(response);	
	}
	
	@GetMapping("/findBannersList")
	public ResponseEntity<FindBannersResponse> getBannersList() {	
		FindBannersResponse response = new FindBannersResponse();
		response.setBannersList(homeService.getBannerRecords());							
		return ResponseEntity.ok().body(response);	
	}
	
	@GetMapping("/findCollegeList")
	public ResponseEntity<FindCollegeResponse> getCollegeList() {
		FindCollegeResponse response = collegeService._getCollegeList();
		return ResponseEntity.ok().body(response);
	}	
	
	
	@GetMapping("/findTestimonials")
	public ResponseEntity<FindTestimonialResponse> getActiveTestimonials() {	
		FindTestimonialResponse response = new FindTestimonialResponse();
		response.setTestimonialsData(testimonialSrvice._getActiveTestimonials());						
		return ResponseEntity.ok().body(response);	
	}
	
	@GetMapping("findTestimonialsLmt")
	public ResponseEntity<FindTestimonialResponse> getLimitedTestimonials() {	
		FindTestimonialResponse response = new FindTestimonialResponse();
		response.setTestimonialsData(testimonialSrvice._getLimitedTestimonials());						
		return ResponseEntity.ok().body(response);	
	}
	
	@PostMapping("/enqury")
	public Enquery saveEnquery(@RequestBody Enquery enquery) {
		enquery = homeService.saveEnquery(enquery);
		return enquery;
	}
	
	
	
	
	@PostMapping("/businessPromoter")
	public BusinessPromoter saveBusinessPromoter(@RequestBody BusinessPromoter businessPromoter) {
		businessPromoter = homeService.saveBusinessPromoter(businessPromoter);
		return businessPromoter;
	}
}


