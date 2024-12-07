package com.lara.c2c.dto.topic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubTopicDto{
	
	int subtopicId;
	String subTopicName;
	List<MicroTopicDto> microTopics;
}

