package com.lara.c2c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.Testimonial;
import com.lara.c2c.repository.TestimonialRepository;

@Service
public class TestimonialService {
	
	@Autowired
	private TestimonialRepository testimonialRepo;
	
	public Boolean _addTestimonial(Testimonial testimonial) {	
		if(testimonialRepo.save(testimonial) != null) {
			return true;
		}
		return false;
	}
	
	public List<Testimonial> _getTestimonials(){
		return (List<Testimonial>) testimonialRepo.findAll();
	}
	
	public List<Testimonial> _getActiveTestimonials(){
		return testimonialRepo.findByStatus(1);
	}
	
	public Testimonial _getTestimonialById(Integer testimonialId) {
		return testimonialRepo.findByTestimonialId(testimonialId);
	}
	
	public Boolean _deleteTestimonial(Integer testimonialId) {
		if(testimonialRepo.deleteByTestimonialId(testimonialId) == 1) {
			return true;
		}
		return false;
	}
	
	public void _deleteAllTestimonials() {
		testimonialRepo.deleteAll();
	}


	public List<Testimonial> _getLimitedTestimonials() {

		Pageable pageable = PageRequest.of(0, 5);
		Page<Testimonial> testimonials = testimonialRepo.findLimitedTestimonials(pageable);		
		return testimonials.getContent();
	}
}
