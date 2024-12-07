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
public class TopicDto{
	
	int topicId;
	String topicName;
	List<SubTopicDto> subTopics;
}
