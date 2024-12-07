package com.lara.c2c.dto;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.WatchedVideoNotification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString

public class FindNotificationResponse extends BaseResponse{
	
	private List<WatchedVideoNotification> notificationData = Collections.emptyList();
}
