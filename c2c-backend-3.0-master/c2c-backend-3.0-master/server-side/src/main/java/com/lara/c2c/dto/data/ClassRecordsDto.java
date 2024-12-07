package com.lara.c2c.dto.data;

import java.util.ArrayList;
import java.util.List;

import com.lara.c2c.model.ClassRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRecordsDto {
	
	private String message;
	private boolean status;
	private String currentDate;
	private List<ClassRecord> classList = new ArrayList<>();
}
