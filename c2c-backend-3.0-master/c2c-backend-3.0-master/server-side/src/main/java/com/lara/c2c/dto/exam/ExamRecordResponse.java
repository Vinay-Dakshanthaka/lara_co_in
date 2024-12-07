package com.lara.c2c.dto.exam;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class ExamRecordResponse extends BaseResponse{
	
	private List<ExamRecordDto> examRecords = Collections.emptyList();
}
