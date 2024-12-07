package com.lara.c2c.dto.video;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.TimeSpentOnVideo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TimeSpentOnVideoResponse extends BaseResponse{
	
	private List<TimeSpentOnVideo> timeSpentOnVideoData = Collections.emptyList();
}
