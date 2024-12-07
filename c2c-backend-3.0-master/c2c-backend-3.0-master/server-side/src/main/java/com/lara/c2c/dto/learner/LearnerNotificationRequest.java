package com.lara.c2c.dto.learner;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LearnerNotificationRequest {
	private Integer id;
	private Date dateAndTime;
	private Integer[] coursePackageIds;	
	private String note;
}
