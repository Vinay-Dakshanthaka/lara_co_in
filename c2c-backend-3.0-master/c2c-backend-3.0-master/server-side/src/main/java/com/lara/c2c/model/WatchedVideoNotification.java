package com.lara.c2c.model;

import java.util.Date;

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
import lombok.ToString;



@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "watched_video_notifications")
public class WatchedVideoNotification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notification_id")
	private int notificationId;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "video_id")
	private String videoId;
	
	@Column(name = "micro_topic_id")
	private String microTopicId;
	
	@Column(name = "notification_description")
	private String notificationDesc;
	
	@Column(name = "action_status")
	private int actionStatus;
	
	private Date created;
	
}
