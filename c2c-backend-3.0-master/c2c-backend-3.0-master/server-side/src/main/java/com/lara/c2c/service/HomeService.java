package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.Banner;
import com.lara.c2c.model.BusinessPromoter;
import com.lara.c2c.model.Enquery;
import com.lara.c2c.model.Recruiter;
import com.lara.c2c.repository.BannerRepository;
import com.lara.c2c.repository.BusinessPromoterRepository;
import com.lara.c2c.repository.EnqueryRepository;
import com.lara.c2c.repository.RecruiterRepository;

@Service
public class HomeService {
	
	@Autowired
	private RecruiterRepository recruiterRepo;
	
	@Autowired
	private EnqueryRepository enqueryRepository;
	
	@Autowired
	private BannerRepository bannerRepo;
	
	@Autowired
	private BusinessPromoterRepository businessPromoterRepository;
	
	public List<Recruiter> getRecruiterRecords(){  
		try {
			List<Recruiter> recruiterRecords = new ArrayList<>();  
		    recruiterRepo.findAll().forEach(recruiterRecords::add);  
		    return recruiterRecords;
		}catch(Exception ex) {
			System.out.println(ex);;
		}	      	    	 
		return null;
	} 
	
	public List<Banner> getBannerRecords(){  
		try {
			List<Banner> bannerRecords = new ArrayList<>();  
		    bannerRepo.findAll().forEach(bannerRecords::add);  
		    return bannerRecords;
		}catch(Exception ex) {
			System.out.println(ex);;
		}	      	    	 
		return null;
	}
	
	public Enquery saveEnquery(Enquery enquery) {
		enqueryRepository.save(enquery);
		return enquery;
	}
	
	public BusinessPromoter saveBusinessPromoter(BusinessPromoter businessPromoter) {
		businessPromoterRepository.save(businessPromoter);
		return businessPromoter;
	}
	
	
}
