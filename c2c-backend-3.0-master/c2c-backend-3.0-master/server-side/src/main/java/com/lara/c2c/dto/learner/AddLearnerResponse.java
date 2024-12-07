package com.lara.c2c.dto.learner;

import com.lara.c2c.dto.BaseResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString(callSuper = true)
public class AddLearnerResponse extends BaseResponse{
	private String userId;
	private String email;
	private String activationCode;
}
