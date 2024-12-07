package com.lara.c2c.dto.video;

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
public class VideoPlayListResponse{
	
	private String videoId;
	private int courseId;
	private int topicId;
	private String topicName;
	private int subTopicId;
	private String subTopicName;	
	private String microTopicId;
	private String microTopicName;	
	private String src;
	private String title;
	private String type;	

}
