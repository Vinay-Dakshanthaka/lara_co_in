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
public class TotalPointsDto {
	
	private Double videoPoints = 0.0;
	private Date lastUpdatedVideoPoints;
	
	private Double examPoints = 0.0;
	private Date lastUpdatedExamPoints;
	
	private Double cumExamPoints = 0.0;
	private Date lastUpdatedCumExamPoints;
	private Double totalPoints = 0.0;
	
}
