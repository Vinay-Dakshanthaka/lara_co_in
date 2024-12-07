package com.lara.c2c.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.c2c.dto.UserAndCoursePackagesInfo;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.service.LearnerService;

@RestController
@RequestMapping("/api/coursebuyout")
@CrossOrigin("*")
public class CourseBuyoutController {
	@Autowired
	private LearnerService learnerService;
	
	@PutMapping("subscribelearner")
	public ResponseEntity<SuccessStatus> subscribeLearner(@RequestBody UserAndCoursePackagesInfo userCoursePakcagesInfo){
//		ObjectMapper obj = new ObjectMapper();
//		List<Integer> coursePackageIds = null;
//		try {
//			coursePackageIds = obj.readValue(coursePackageIdsString, new ArrayList<Integer>().getClass());
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(coursePackageIds);
		System.out.println(userCoursePakcagesInfo);
//		return ResponseEntity.ok().body(null);
		
		return learnerService.subscribeLearner(userCoursePakcagesInfo);
	}
}
