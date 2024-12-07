package com.lara.c2c.utility;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiStatus {

	private String status;
	private String message;
	public ApiStatus(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public ApiStatus() {
		super();
	}
	
	
}
