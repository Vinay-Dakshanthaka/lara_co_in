package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.model.Cart;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.Learner;
import com.lara.c2c.repository.CartRepository;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.LearnerRepository;


@Service
@Transactional
public class CartService {
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private LearnerRepository learnerRepo;
	
	@Autowired
	private CoursePackageRepository packageRepo;
	
	public ResponseEntity<SuccessStatus> addToCart(String userId, Integer packageId){
		Optional<Learner> user = learnerRepo.findById(userId);
		if (!user.isPresent()) {
			
		}
		
		Optional<CoursePackage> coursePackage = packageRepo.findById(packageId);
		if (!coursePackage.isPresent()) {
			
		}
		
		if (!cartRepo.findByUser(user.get()).isPresent()) {
			Set<CoursePackage> userCoursePackages = new HashSet<>();
			userCoursePackages.add(coursePackage.get());
			cartRepo.save(Cart.builder().user(user.get()).coursePackages(userCoursePackages).build());
		}
		
		Set<CoursePackage> userCoursePackages = cartRepo.findByUser(user.get()).get().getCoursePackages();
		userCoursePackages.add(coursePackage.get());
		cartRepo.findByUser(user.get()).get().setCoursePackages(userCoursePackages);
		
		return ResponseEntity.ok().body(SuccessStatus.builder().message("Successfully Added !").status(true).build());
	}
	
	public ResponseEntity<Integer> getCartNoOfItems(String userId){
		Optional<Learner> user = learnerRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.ok().body(0);
		}
		
		Optional<Cart> userCart = cartRepo.findByUser(user.get());
		if (!userCart.isPresent()) {
			return ResponseEntity.ok().body(0);
		}
		
		return ResponseEntity.ok().body(userCart.get().getCoursePackages().size());
	}
	
	public ResponseEntity<Set<CoursePackage>> getUserCart(String userId){
		Optional<Learner> user = learnerRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.ok().body(new HashSet<>());
		}
		
		Optional<Cart> userCart = cartRepo.findByUser(user.get());
		
		if (!userCart.isPresent()) {
			return ResponseEntity.ok().body(new HashSet<>());
		}
		if (userCart.get().getCoursePackages().size() == 0) {
			return ResponseEntity.ok().body(new HashSet<>());
		}
		
		return ResponseEntity.ok().body(userCart.get().getCoursePackages());
	}
	
	public ResponseEntity<SuccessStatus> deleteFromCart(String userId, Integer packageId){
		Optional<Learner> user = learnerRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.ok().body(SuccessStatus.builder().message("please login to continue !").status(false).build());
		}
		
		Optional<Cart> userCart = cartRepo.findByUser(user.get());
		if (!userCart.isPresent()) {
			
		}
		
		Optional<CoursePackage> coursePackage = packageRepo.findById(packageId);
		if (!coursePackage.isPresent()) {
			
		}
		
		Set<CoursePackage> coursePackages = userCart.get().getCoursePackages();
		coursePackages.remove(coursePackage.get());
		
		userCart.get().setCoursePackages(coursePackages);
		
		return ResponseEntity.ok().body(SuccessStatus.builder().message("Successfully deleted !").status(true).build());
	}
}

