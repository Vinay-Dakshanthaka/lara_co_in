package com.lara.c2c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.ParamsRequest;
import com.lara.c2c.dto.PhonePeParams;
import com.lara.c2c.service.PhonePeParamsService;

@RestController
@RequestMapping("api/phonePe/v1")
@CrossOrigin("*")
public class PhonePeParamsController {

	@Autowired
	private PhonePeParamsService phonePeService;

	@PostMapping("getParams")
	public ResponseEntity<PhonePeParams> getParams(@RequestBody ParamsRequest paramRequest) {
		return phonePeService.getParams(paramRequest);
	}
}
