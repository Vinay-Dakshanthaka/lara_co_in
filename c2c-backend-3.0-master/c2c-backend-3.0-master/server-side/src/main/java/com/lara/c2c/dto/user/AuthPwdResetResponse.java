package com.lara.c2c.dto.user;

import com.lara.c2c.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AuthPwdResetResponse extends BaseResponse{
	
	private boolean isValidUser;
	private boolean pwdChanged;
	
}

