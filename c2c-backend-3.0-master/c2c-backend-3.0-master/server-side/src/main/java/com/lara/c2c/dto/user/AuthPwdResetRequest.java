package com.lara.c2c.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthPwdResetRequest
{
	private String email;
	private String userId;
	private String password;
	private String cPassword;
	private String activationCode;
	private String currentPassword;
	private String newPassword;
}
