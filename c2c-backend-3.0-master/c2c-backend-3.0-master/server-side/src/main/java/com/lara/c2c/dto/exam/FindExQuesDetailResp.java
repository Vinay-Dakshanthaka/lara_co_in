package com.lara.c2c.dto.exam;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.dto.CoursePackToMicroTopicResp;
import com.lara.c2c.model.Question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindExQuesDetailResp extends BaseResponse{
	
	private CoursePackToMicroTopicResp coursePackDetails;
	private ResultResponse examResultData; 
	private CumExamResultResponse cumExamResultData;
	private List<Question> questionsList = Collections.emptyList();

}
