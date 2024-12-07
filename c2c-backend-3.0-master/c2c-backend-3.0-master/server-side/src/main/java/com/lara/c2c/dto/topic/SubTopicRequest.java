package com.lara.c2c.dto.topic;

import com.lara.c2c.dto.BaseRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubTopicRequest extends BaseRequest{
	
	private int subTopicId;
	private int topicId;
	private String subTopicName;
}
