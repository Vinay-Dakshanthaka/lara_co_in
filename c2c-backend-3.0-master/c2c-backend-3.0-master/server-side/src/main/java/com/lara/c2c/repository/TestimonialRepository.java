package com.lara.c2c.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lara.c2c.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Integer>{

	List<Testimonial> findByStatus(Integer status);

	Testimonial findByTestimonialId(Integer testimonialId);
	
	@Modifying
    @Query("delete from Testimonial t where t.testimonialId = :testimonialId")
    @Transactional
	Integer deleteByTestimonialId(Integer testimonialId);
	
	@Query("select new com.lara.c2c.model.Testimonial(t.testimonialId as testimonialId, "
			+ "t.testimonialType as testimonialType, "
			+ "t.testimonialText as testimonialText, "
			+ "t.testimonialDesc as testimonialDesc, "
			+ "t.testimonialVideoUrl as testimonialVideoUrl, "
			+ "t.rating as rating, "
			+ "t.testimonialThubmPath as testimonialThubmPath, "
			+ "t.status as status) from Testimonial t")
	Page<Testimonial> findLimitedTestimonials(Pageable pageRequest);
	
}
