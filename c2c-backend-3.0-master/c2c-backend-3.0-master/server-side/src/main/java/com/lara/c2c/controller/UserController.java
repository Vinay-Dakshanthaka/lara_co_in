package com.lara.c2c.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.FindHomeElementsResponse;
import com.lara.c2c.dto.FindRecruitersResponse;
import com.lara.c2c.dto.learner.FindLearnersResponse;
import com.lara.c2c.dto.user.AuthenticateUserRequest;
import com.lara.c2c.dto.user.AuthenticateUserResponse;
import com.lara.c2c.dto.user.LoggedInUserResponse;
import com.lara.c2c.service.LearnerService;
import com.lara.c2c.service.CoursePackageService;
import com.lara.c2c.service.HomeService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;
//import com.paytm.merchant.CheckSumServiceHelper;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private LearnerService learnerService;
	
	@Autowired
	private HttpServletRequest hsRequest;	
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	CoursePackageService coursePackService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody AuthenticateUserRequest request) throws Exception{
		AuthenticateUserResponse response = learnerService.authenticateUserDetails(request);
		
		if (Constants.isRequestFailed(response)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		UserInfo.setUserId(hsRequest, response.getUserId(), response.getUsername());	
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("signout")
	public ResponseEntity<Void> logOutUser(){
		hsRequest.getSession().invalidate();
		hsRequest.getSession().getServletContext().removeAttribute("useId");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("checkSession")
	public ResponseEntity<String> checkSession(){
		String result = hsRequest.getSession().isNew() ? "YES" : "YES";
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("getLoggedInUser")
	public ResponseEntity<LoggedInUserResponse> getLoggedInUserInfo(){
		LoggedInUserResponse response = UserInfo.getUserDetails((HttpServletRequest) hsRequest);	
		return ResponseEntity.ok(response);
	}
	
	/*@GetMapping("/findHomeElements")
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
		/*return ResponseEntity.ok().body(homeElResponse);	
	}*/
}
