package com.lara.c2c.dto.exam;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CumExamPointDto {
	
	private Double cumExamPoint;
	private String examRelatedInfo;
	private Date completedDate;
}
