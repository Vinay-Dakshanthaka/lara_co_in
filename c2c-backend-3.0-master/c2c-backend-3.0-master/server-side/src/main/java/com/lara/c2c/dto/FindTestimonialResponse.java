package com.lara.c2c.dto;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.Testimonial;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindTestimonialResponse extends BaseResponse{
	
	private List<Testimonial> testimonialsData = Collections.emptyList();

}
