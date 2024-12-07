package com.lara.c2c.dto.data;

import java.util.List;

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
public class UserTestRecordDto {
	
	private String email;
	private List<TestDto> userAnswers;

	@Override
	public String toString() {
		return "UserTestRecordDto [userAnswers=" + userAnswers + "]";
	}
	
}
