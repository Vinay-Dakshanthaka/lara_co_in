package com.lara.c2c.dto;

import com.lara.c2c.dto.data.SuccessStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class OrderState extends SuccessStatus {

	public String state;
	public String paymentState;
	public boolean orderClosed;
}
