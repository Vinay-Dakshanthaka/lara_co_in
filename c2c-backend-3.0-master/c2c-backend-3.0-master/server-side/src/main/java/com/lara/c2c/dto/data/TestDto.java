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
public class TestDto {
	private Integer  questionId;
    private List<MultipleOptions>  selectedOptions;
	@Override
	public String toString() {
		return "TestDto [questionId=" + questionId + ", selectedOptions=" + selectedOptions + "]";
	}
}
