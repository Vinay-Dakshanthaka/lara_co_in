package com.lara.c2c.dto.college;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.College;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindCollegeResponse extends BaseResponse{
	
	private List<College> collegeList = Collections.emptyList();
}
