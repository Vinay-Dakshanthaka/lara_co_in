package com.lara.c2c.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lara.c2c.utility.ServiceUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "map_time_spent_on_video")
public class TimeSpentOnVideo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "course_package_id")
	private int coursePackageId;
	
	@Column(name = "video_id")
	private String videoId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "time_spent")
	private double timeSpent;
	
	@Column(name = "frequency")
	private int frequency;	
	
	@Column(name = "last_viewed")
	private Date date = ServiceUtils.getCurrentDateTime();
}