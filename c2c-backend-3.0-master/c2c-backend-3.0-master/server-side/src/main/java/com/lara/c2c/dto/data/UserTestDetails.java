package com.lara.c2c.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTestDetails {
    private Integer totalNumberQuestions;
	
	private Integer totalNumberCorrectQuestions;
	
	private double percentage;
	
	private String isStatus;
	
	@Override
	public String toString() {
		return "UserTestDetails [totalNumberQuestions=" + totalNumberQuestions + ", totalNumberCorrectQuestions="
				+ totalNumberCorrectQuestions + ", percentage=" + percentage + ", isStatus=" + isStatus + "]";
	}
	
}
