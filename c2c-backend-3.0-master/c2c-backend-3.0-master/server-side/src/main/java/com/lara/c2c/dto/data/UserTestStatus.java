package com.lara.c2c.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTestStatus {
	private String message;
	
	private boolean status;
	
	private UserTestDetails userTestDetails;

	@Override
	public String toString() {
		return "UserTestStatus [message=" + message + ", status=" + status + ", userTestDetails=" + userTestDetails
				+ "]";
	}
	
	
}
