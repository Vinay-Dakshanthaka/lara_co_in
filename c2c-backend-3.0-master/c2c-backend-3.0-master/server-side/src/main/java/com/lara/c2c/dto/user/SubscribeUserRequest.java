package com.lara.c2c.dto.user;

import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class SubscribeUserRequest {
	
	private String email;
	private String subscriptionCode;
}
