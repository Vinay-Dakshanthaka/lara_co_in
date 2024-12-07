package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.Education;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindEducationResponse{
	
	private List<Education> educationResp = Collections.emptyList();
}
