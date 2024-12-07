package com.lara.c2c.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.learner.LiveClassDTO;
import com.lara.c2c.dto.learner.LiveClassRequest;
import com.lara.c2c.model.LiveClass;
import com.lara.c2c.service.LiveClassService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/learner")
public class LiveClassController {
	@Autowired
	private LiveClassService liveClassService;
	
	
	@RequestMapping(path="/saveLiveClass", method=RequestMethod.POST)
	public LiveClassRequest save(@RequestBody LiveClassRequest liveClassRequest) {	
		LiveClass liveClass;
		for(Integer id : liveClassRequest.getCoursePackageIds()) {
			liveClass = new LiveClass();
			liveClass.setCoursePackageId(id);
			liveClass.setDateAndTime(liveClassRequest.getDateAndTime());
			liveClass.setDependents(liveClassRequest.getDependents());
			liveClass.setIsPublic(liveClassRequest.getIsPublic());
			liveClass.setLiveUrl(liveClassRequest.getLiveUrl());
			liveClass.setNote(liveClassRequest.getNote());
			liveClass.setPassword(liveClassRequest.getPassword());
			liveClass.setTopic(liveClassRequest.getTopic());
			liveClassService.save(liveClass);	
		}
			
		return liveClassRequest;
	}	
	@RequestMapping(path="/readAllLiveClasses", method=RequestMethod.GET)
	public List<LiveClassDTO> readAll() {		
		return liveClassService.readAll();
	}	
	
	@RequestMapping(path="/readAllLiveClassesForCurrentUser", method=RequestMethod.GET)
	public List<LiveClassDTO> readAllForOneUser() {		
		return liveClassService.readAllForOneUser();
	}	

}


/*
{
  	"id": 4041,
	"dateAndTime": "2014-09-27T10:30:00.000",
	"coursePackageId":1,
	"liveUrl":"url1",
	"password": "abc",
	"topic":"ahdsh",
	"dependents": "yoiu",
	"isPublic": "true"
}
 */

//insert into live_class values(101, '2020-09-27T10:30:00.000', 1, 'url1', 'abc', 'hello1', 'hello2', 'true')
/*
 
 insert into live_class values(101, '2020-09-27T10:30:00.000', 1, 'url1', 'abc', 'hello1', 'hello2', true);
 
 */





