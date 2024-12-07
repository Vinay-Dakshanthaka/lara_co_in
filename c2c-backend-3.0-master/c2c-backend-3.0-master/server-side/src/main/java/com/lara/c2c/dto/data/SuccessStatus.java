package com.lara.c2c.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SuccessStatus {
	private String message;
	private boolean status;
	@Override
	public String toString() {
		return "SuccessStatus [message=" + message + ", status=" + status + "]";
	}
	
}
