package com.lara.c2c.dto;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.Recruiter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindRecruitersResponse extends BaseResponse{
	
	private List<Recruiter> recruitersData = Collections.emptyList();
}
