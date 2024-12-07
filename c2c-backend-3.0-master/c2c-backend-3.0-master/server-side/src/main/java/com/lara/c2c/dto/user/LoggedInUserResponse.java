package com.lara.c2c.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoggedInUserResponse {
	
	private String userId;
	private String firstName;
}
