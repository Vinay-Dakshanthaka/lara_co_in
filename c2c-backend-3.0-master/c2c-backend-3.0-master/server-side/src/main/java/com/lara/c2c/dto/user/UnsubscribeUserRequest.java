package com.lara.c2c.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UnsubscribeUserRequest {
	
	private String email;
	private String unsubscriptionCode;
}
