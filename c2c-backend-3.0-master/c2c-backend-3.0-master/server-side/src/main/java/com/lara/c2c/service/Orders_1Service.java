package com.lara.c2c.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.c2c.dto.OrderState;
import com.lara.c2c.dto.PhonePeBody;
import com.lara.c2c.dto.UserAndCoursePackagesInfo;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.model.Cart;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.model.Orders_1;
import com.lara.c2c.model.Orders_1_packages_prices;
import com.lara.c2c.repository.CartRepository;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.Orders_1Repository;
import com.lara.c2c.utility.PhonePeUtils;

@Service
@Transactional
public class Orders_1Service {

	@Autowired
	private Orders_1Repository ordersRepo;

	@Autowired
	private CoursePackageRepository packageRepo;

	@Autowired
	private LearnerCourseRepository learnerCpRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private LearnerRepository learnerRepo;

	@Autowired
	private PhonePeUtils phonePeUtils;
	
	@Autowired
	private PhonePeParamsService phonePeParamsService;
	
	private ObjectMapper om = new ObjectMapper();

	public ResponseEntity<Long> initiateOrder(UserAndCoursePackagesInfo userCoursePakcagesInfo) {
		return ResponseEntity.ok().body(saveOrders(userCoursePakcagesInfo.getUserId(), userCoursePakcagesInfo));
	}

	public void confirmOrder(Long orderId, String merchantId, String xVerify, PhonePeBody request) {
	    
	    	System.out.println("from confirm order");

		// check for order
		Optional<Orders_1> orderOptional = ordersRepo.findById(orderId);
		if (!orderOptional.isPresent()) {
			return;
		}

		// get the order
		Orders_1 userCoursePakcagesInfo = orderOptional.get();

		// check if order closed
		if (userCoursePakcagesInfo.isOrderClosed()) {
			return;
		}

		// calculate the checksum
		String calculatedChecksum;
		try {
			calculatedChecksum = phonePeUtils.checksumShort(request.getResponse());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}

		// verify the checksum, if not abort, if matches make the flag true and store in
		// the order
		System.out.println(calculatedChecksum.equals(xVerify));
		if (!calculatedChecksum.equals(xVerify)) {
			userCoursePakcagesInfo.setChecksumMatch(false);
			return;
		}
		userCoursePakcagesInfo.setChecksumMatch(true);
		userCoursePakcagesInfo.setXVerfiy(calculatedChecksum);

		// decode the base64 payload
		byte[] bytes = Base64.getDecoder().decode(request.getResponse().getBytes());

		// convert to json object and read the values for success and store payment
		// method
		
		try {
			JsonNode jsonNode = om.readTree(new String(bytes));
			JsonNode data = jsonNode.get("data");
			JsonNode paymentInstrument = data.get("paymentInstrument");

			userCoursePakcagesInfo.setMop(paymentInstrument.get("type").asText());

			// perform the checks and update the flags
			if (data.get("state").asText().equals("COMPLETED")) {
				userCoursePakcagesInfo.setPaymentStatus(data.get("state").asText());
				if (LocalDateTime.now().isBefore(userCoursePakcagesInfo.getCreatedOn().plusMinutes(30L))) {
					userCoursePakcagesInfo.setOrderExpired(false);
					if (phonePeUtils.getMERCHANT_ID().equals(merchantId) && orderId.toString().equals(data.get("merchantTransactionId").asText())) {
						userCoursePakcagesInfo.setMerchantMatch(true);
						userCoursePakcagesInfo.setState("confirmed");
						subscribeLearner(orderId);
						userCoursePakcagesInfo.setOrderClosed(true);
					} else {
						userCoursePakcagesInfo.setMerchantMatch(false);
						userCoursePakcagesInfo.setOrderClosed(true);
					}
				} else {
					userCoursePakcagesInfo.setOrderExpired(true);
					userCoursePakcagesInfo.setOrderClosed(true);
				}
			} else {
				userCoursePakcagesInfo.setPaymentStatus(data.get("state").asText());
				userCoursePakcagesInfo.setOrderClosed(true);
			}
			userCoursePakcagesInfo.setUrlHit(true);
			userCoursePakcagesInfo.setUrlHitTimeStamp(LocalDateTime.now());
			userCoursePakcagesInfo.setJsonError(false);
		} catch (IOException e) {
			userCoursePakcagesInfo.setUrlHit(true);
			userCoursePakcagesInfo.setUrlHitTimeStamp(LocalDateTime.now());
			userCoursePakcagesInfo.setJsonError(true);
			userCoursePakcagesInfo.setBase64(request.getResponse());
			e.printStackTrace();
		}
		return;
	}

	public ResponseEntity<OrderState> checkStatus(Long orderId) {
		Optional<Orders_1> orderOptional = ordersRepo.findById(orderId);
		if (!orderOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(OrderState.builder().status(false).message("No Such order").build());
		}
		
		if (
			!orderOptional.get().getState().equals("confirmed") && 
			orderOptional.get().getPaymentStatus() == null  && 
			!orderOptional.get().isOrderClosed()
		) {
		    	System.out.println("FROM IF");
		    	String response;
		    	try {
		    	    response = phonePeParamsService.checkStatusForPaymentComplete(orderId.toString());
			} catch (Exception e) {
			    return ResponseEntity.ok()
					.body(OrderState.builder().status(true).message("Fetched").state(orderOptional.get().getState())
							.paymentState(orderOptional.get().getPaymentStatus())
							.orderClosed(orderOptional.get().isOrderClosed()).build());
			}
			System.out.println(response);
			if (response == null) {
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(OrderState.builder().status(false).message("Something went while checking the status of your order. Please try again in sometime").build());
			}
			
			JsonNode jsonNode;
			try {
			    jsonNode = om.readTree(response);
			} catch (IOException e) {
			    e.printStackTrace();
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(OrderState.builder().status(false).message("Something went while checking the status of your order. Please try again in sometime").build());
			}
			
			String fetchedOrderId = jsonNode.get("data").get("merchantTransactionId").asText();
			String merchantId = jsonNode.get("data").get("merchantId").asText();
			Long amount = (jsonNode.get("data").get("amount").asLong())/100;
			String state = jsonNode.get("data").get("state").asText();
			String code = jsonNode.get("code").asText();
			
			if (merchantId.equals(phonePeUtils.getMERCHANT_ID()) && fetchedOrderId.equals(orderId.toString()) && amount == orderOptional.get().getTotalAmount()) {
			 
			    if(code.equals("PAYMENT_PENDING")) {
				orderOptional.get().setState("pending");
			    }
			    else if (code.equals("PAYMENT_ERROR") && state.equals("FAILED")) {
				orderOptional.get().setOrderClosed(true);
			    }
			    else if (code.equals("PAYMENT_SUCCESS") && state.equals("COMPLETED")) {
				orderOptional.get().setPaymentStatus(state);
				if (LocalDateTime.now().isBefore(orderOptional.get().getCreatedOn().plusMinutes(30L))) {
				    orderOptional.get().setOrderExpired(false);			    
				}
				else {
				    orderOptional.get().setOrderExpired(true);
				}
				orderOptional.get().setMerchantMatch(true);
				orderOptional.get().setState("confirmed");
				subscribeLearner(orderId);
				orderOptional.get().setOrderClosed(true);
			    }
			}
			
			ordersRepo.saveAndFlush(orderOptional.get());
		}
		
		return ResponseEntity.ok()
				.body(OrderState.builder().status(true).message("Fetched").state(orderOptional.get().getState())
						.paymentState(orderOptional.get().getPaymentStatus())
						.orderClosed(orderOptional.get().isOrderClosed()).build());
	}

	public Long saveOrders(String userId, UserAndCoursePackagesInfo userCoursePakcagesInfo) {
		// add orders
		Orders_1 order = ordersRepo.save(Orders_1.builder().userId(userId).gst(userCoursePakcagesInfo.getGst())
				.totalAmount(userCoursePakcagesInfo.getTotalAmount()).subTotal(userCoursePakcagesInfo.getSubTotal())
				.gst(userCoursePakcagesInfo.getGst()).state("initiated").build());

		order.setCoursesBought(userCoursePakcagesInfo.getCoursePackagesInfo().stream()
				.map(cpkg -> Orders_1_packages_prices.builder()
						.coursePakcage(packageRepo.findByCoursePackageId(cpkg.getCoursePackageId()))
						.name(cpkg.getCoursePackageName()).price(cpkg.getCoursePackagePrice()).orders_1Id(order)
						.build())
				.collect(Collectors.toList()));

		return order.getOrderId();
	}

	public ResponseEntity<SuccessStatus> subscribeLearner(Long orderId) {
		Optional<Orders_1> orderOptional = ordersRepo.findById(orderId);
		if (!orderOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(SuccessStatus.builder().status(false).message("No Such order").build());
		}
		Orders_1 userCoursePakcagesInfo = orderOptional.get();
		userCoursePakcagesInfo.setState("confirmed");

		// fetch all by ids
		String userId = userCoursePakcagesInfo.getUserId();
		List<LearnerCourse> fetchedOldLearnerCourse = learnerCpRepo.findAllByUserId(userId);

		System.out.println(fetchedOldLearnerCourse.size());
		// check if the course is bought previously
		if (fetchedOldLearnerCourse.size() == 0) {
			System.out.println("from if");
			userCoursePakcagesInfo.getCoursesBought().forEach(cpkg -> {
				learnerCpRepo.save(LearnerCourse.builder().coursePackageId(cpkg.getCoursePakcage().getCoursePackageId())
						.userId(userId).status(1).build());
			});
		} else {
			System.out.println("from else");
			List<CoursePackage> filteredCoursePacakgeInfo = userCoursePakcagesInfo.getCoursesBought().stream()
					.filter((cpkg) -> {
						for (LearnerCourse learnerCourse : fetchedOldLearnerCourse) {
							System.out.println(cpkg.getCoursePakcage().getCoursePackageId() + " "
									+ learnerCourse.getCoursePackageId());
							if (learnerCourse.getCoursePackageId() == cpkg.getCoursePakcage().getCoursePackageId())
								return false;
						}
						return true;
					}).map(cpkg -> cpkg.getCoursePakcage()).collect(Collectors.toList());
//			System.out.println(filteredCoursePacakgeIds);

			if (filteredCoursePacakgeInfo.size() > 0) {
				filteredCoursePacakgeInfo.forEach(cpkg -> {
					learnerCpRepo.save(LearnerCourse.builder().coursePackageId(cpkg.getCoursePackageId()).userId(userId)
							.status(1).build());
				});
			}
		}

		// clear cart
		Cart userCart = cartRepo.findByUser(learnerRepo.findById(userId).get()).get();
		userCart.setCoursePackages(new HashSet<CoursePackage>());
		return ResponseEntity.ok().body(SuccessStatus.builder().status(true).message("Course Assigned!").build());
	}
}
