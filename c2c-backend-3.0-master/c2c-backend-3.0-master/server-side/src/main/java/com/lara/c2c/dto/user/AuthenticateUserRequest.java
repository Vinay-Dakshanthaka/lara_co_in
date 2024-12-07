package com.lara.c2c.dto.user;


import com.lara.c2c.dto.BaseRequest;

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
public class AuthenticateUserRequest extends BaseRequest{
	
	private String email;
	private String password;
	private String otp;		
	private String role;
}
