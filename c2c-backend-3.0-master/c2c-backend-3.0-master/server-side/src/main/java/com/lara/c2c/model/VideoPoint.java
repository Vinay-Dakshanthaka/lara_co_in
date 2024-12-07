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
@Table(name = "cb_video_points")
public class VideoPoint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="video_point_id")
	private int videoPointId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "video_point")
	private double videoPoint;
	
	@Column(name = "video_watched_date")
	private Date videoWatchedDate = ServiceUtils.getCurrentDateTime();
}
