package com.lara.c2c.dto.user;

import com.lara.c2c.dto.BaseResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UnsubscribeUserResponse extends BaseResponse	{
	
	private boolean  userUnsubscribed;
}
