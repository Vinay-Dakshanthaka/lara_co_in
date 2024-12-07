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
public class MultipleOptions {
	private Integer optionId;
    private String  optionText;
    
	@Override
	public String toString() {
		return "MultipleOptions [optionId=" + optionId + ", optionText=" + optionText + "]";
	}
 
}
