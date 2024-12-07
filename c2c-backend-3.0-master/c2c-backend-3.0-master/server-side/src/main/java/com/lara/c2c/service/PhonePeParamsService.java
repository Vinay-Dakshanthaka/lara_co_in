package com.lara.c2c.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.c2c.dto.ParamsRequest;
import com.lara.c2c.dto.PhonePeParams;
import com.lara.c2c.model.Orders_1;
import com.lara.c2c.repository.Orders_1Repository;
import com.lara.c2c.utility.PhonePeUtils;

@Service
@Transactional
public class PhonePeParamsService {

	@Autowired
	private PhonePeUtils phonePeUtils;

	@Autowired
	private Orders_1Repository orderRepo;
	
	@Autowired
	private ObjectMapper om;
	
	private RestTemplate restTemplate = new RestTemplate();
//	@Autowired
//	private PhonePeStandardCheckout phonePeStandardCheckout;
	
	public ResponseEntity<PhonePeParams> getParams(ParamsRequest paramsRequest) {

		// check for null
		if (paramsRequest.getAmount() == null || paramsRequest.getUserId() == null
				|| paramsRequest.getOrderId() == null) {
			return ResponseEntity.badRequest().body(PhonePeParams.builder().status(false).message("payload is null").build());
		}

		// check for order
		Optional<Orders_1> orderOptional = orderRepo.findById(Long.valueOf(paramsRequest.getOrderId()));
		if (!orderOptional.isPresent()) {
			return ResponseEntity.badRequest()
					.body(PhonePeParams.builder().status(false).message("Order Doesn't Exist.").build());
		}

		// create object map
		Map<String, Object> objectMap;
		try {
			objectMap = phonePeUtils.createPhonePeObjectMap(paramsRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(
					PhonePeParams.builder().status(false).message("An Error Occurred In Proccessing Gateway.").build());
		}

		// createJsonNode
		ObjectMapper om = new ObjectMapper();
		JsonNode jsonNode = phonePeUtils.createPhonePeRequestJsonNode(objectMap, om);

		// create Json String
		String payloadString;
		try {
			payloadString = om.writeValueAsString(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(
					PhonePeParams.builder().status(false).message("An Error Occurred In Proccessing Gateway.").build());
		}

		System.out.println(payloadString);
		// calculate base64
		String base64 = phonePeUtils.base64(payloadString);

		// calculate checksum
		String checksum;
		try {
			checksum = phonePeUtils.checksum(base64);
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.ok().body(
					PhonePeParams.builder().status(false).message("An Error Occurred In Proccessing Gateway.").build());
		}

		String payPageUrl = getPayPageUrl(checksum, base64, paramsRequest.getOrderId());
		
		if (payPageUrl == null) {
		    return ResponseEntity.ok().body(
				PhonePeParams.builder().status(false).message("An Error Occurred In Proccessing Gateway.").build());
		}
		else {
		    return ResponseEntity.ok().body(
				PhonePeParams.builder().status(true).message("Success").response(payPageUrl).build());
		}
	}
	
	public String getPayPageUrl(String xVerify, String base64, String orderId) {
//	    String url = "https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay";
	    String url = "https://api.phonepe.com/apis/hermes/pg/v1/pay";
	    // create headers
	    HttpHeaders headers = new HttpHeaders();
	    
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    
	    // set `content-type` header
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    // set custom header
	    headers.set("X-VERIFY", xVerify);
	    
	    // create a map for post parameters
	    Map<String, String> map = new HashMap<>();
	    map.put("request", base64);

	    // build the request
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

	    // use `exchange` method for HTTP call
	    ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
	    System.out.println(response.getStatusCode());
	    if(response.getStatusCode() == HttpStatus.OK) {
		//get url from json String
		JsonNode body;
		try {
		    body = om.readTree(response.getBody());
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
		
		String payPageUrl = body.get("data").get("instrumentResponse").get("redirectInfo").get("url").asText();
		
	        return payPageUrl;
	    } else {
	        return null;
	    }
	}
	
	public String checkStatusForPaymentComplete(String orderId) {
	    
//	    String url = "https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/status/" + phonePeUtils.getMERCHANT_ID() + "/" 
//		    		+ orderId;
	    String url = "https://api.phonepe.com/apis/hermes/pg/v1/status/" + phonePeUtils.getMERCHANT_ID() + "/" 
		    + orderId;
	    
	    String checksum = "";
	    try {
		checksum = phonePeUtils.checksumCheckStatus(orderId);
	    } catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    HttpHeaders headers = new HttpHeaders();
	    
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    
	    // set `content-type` header
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    // set custom header
	    headers.set("X-VERIFY", checksum);
	    
	    //set merchant id
	    headers.set("X-MERCHANT-ID", phonePeUtils.getMERCHANT_ID());
	    
	    HttpEntity request = new HttpEntity(headers);
	    
	    ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, request, String.class, 1);
	    
	    if(response.getStatusCode() == HttpStatus.OK) {
	        System.out.println(response.getBody());
	        return response.getBody();
	    } else {
	        return null;
	    }
	    
	}

}
