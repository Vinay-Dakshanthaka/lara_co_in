package com.lara.c2c.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.learner.LiveClassDTO;
import com.lara.c2c.model.LiveClass;
import com.lara.c2c.repository.LiveClassRepository;
import com.lara.c2c.utility.UserInfo;

@Service
public class LiveClassService {

	@Autowired
	HttpServletRequest hsRequest;
	
	@Autowired
	private LiveClassRepository liveClassRepository;
	
	public LiveClass save(LiveClass liveClass) {		
		liveClassRepository.save(liveClass);		
		return liveClass;
	}	
	public List<LiveClassDTO> readAll() {		
		return liveClassRepository.readAllLiveClasses();
	}	
	
	public List<LiveClassDTO> readAllForOneUser() {		
		String userId = UserInfo.getUserId(hsRequest);
		List<LiveClassDTO> liveClasses = liveClassRepository.readAllLiveClassesForOneLearner(userId);
		liveClasses = new ArrayList<LiveClassDTO>(new HashSet<LiveClassDTO>(liveClasses));
		Collections.sort(liveClasses, (c1, c2) -> c1.getDateAndTime().compareTo(c2.getDateAndTime()));
		return liveClasses;
	}	
	
	
	public List<LiveClassDTO> readAllLiveClassesForHomePage() {		
		List<LiveClassDTO> liveClasses = liveClassRepository.readAllLiveClasses();
		System.out.println("liveclasses:" + liveClasses.size());
		liveClasses = new ArrayList<LiveClassDTO>(new HashSet<LiveClassDTO>(liveClasses));
		Collections.sort(liveClasses, (c1, c2) -> c1.getDateAndTime().compareTo(c2.getDateAndTime()));
		return liveClasses;
	}	

}
