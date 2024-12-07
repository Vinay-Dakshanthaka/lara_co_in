package com.lara.c2c.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OtpData extends BaseResponse{
	private String email;
	private String otp;
	private boolean isOtpValidated;
	
	public OtpData(String email, String otp) {
		this.email = email;
		this.otp = otp;
	}
}
