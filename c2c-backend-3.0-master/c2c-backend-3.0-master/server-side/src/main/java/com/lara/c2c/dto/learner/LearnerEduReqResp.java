package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.LearnerEducation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LearnerEduReqResp{
	
	private List<LearnerEducation> learnerEduRecords = Collections.emptyList();
}
