package com.lara.c2c.dto.topic;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.Topic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class FindTopicsResponse extends BaseResponse{
	
	private List<Topic> topicsData = Collections.emptyList();
}
