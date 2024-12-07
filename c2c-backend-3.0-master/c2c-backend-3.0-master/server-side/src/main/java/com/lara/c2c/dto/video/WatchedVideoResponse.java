package com.lara.c2c.dto.video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WatchedVideoResponse{
	
	private boolean isWatchedEarlier;
	public int examRecordId;
}
