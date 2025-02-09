package com.lara.c2c.dto.video;

import java.util.Date;

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
public class VideoPointDto {
	
	private Double videoPoint;
	private Date videoWatchedDate;
}
