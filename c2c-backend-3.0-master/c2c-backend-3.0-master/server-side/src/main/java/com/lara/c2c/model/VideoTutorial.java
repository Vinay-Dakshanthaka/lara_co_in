package com.lara.c2c.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lara_video_tutorials")
public class VideoTutorial {

	@Id	
	@Column(name = "video_id")
	private String videoId;
	
	@Column(name="course_id")
	private int courseId;
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "src")
	private String src;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "title")
	private String title;
}
