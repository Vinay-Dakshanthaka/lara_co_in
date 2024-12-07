package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.Learner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class FindLearnersResponse extends BaseResponse{
	
	private List<Learner> learnersData = Collections.emptyList();
}
