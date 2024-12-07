package com.lara.c2c.dto.video;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishedMicroTopicIdsResponse extends BaseResponse{
	
	private List<String> microTopicIds = Collections.emptyList();
}
