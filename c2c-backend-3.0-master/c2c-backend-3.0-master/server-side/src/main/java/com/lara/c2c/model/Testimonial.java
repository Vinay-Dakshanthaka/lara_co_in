package com.lara.c2c.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cb_testimonials")
public class Testimonial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="testimonial_id")
    private Integer testimonialId;
	
	@Column(name="testimonial_type")
	private String testimonialType;
	
	@Column(name="testimonial_text")
	private String testimonialText;
	
	@Column(name="testimonial_desc", columnDefinition="TEXT")
	private String testimonialDesc;
	
	@Column(name="testimonial_video_url")
	private String testimonialVideoUrl;
	
	@Column(name="rating")
	private Float rating;
	
	@Column(name="testimonial_thumb_path")
	private String testimonialThubmPath;
	
	@Column(name="status")
	private Integer status = 1;
		
}
