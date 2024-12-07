package com.lara.c2c.dto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindHomeElementsResponse extends BaseResponse{	
	
	private Map<String, List> homeElements = Collections.emptyMap();
}
