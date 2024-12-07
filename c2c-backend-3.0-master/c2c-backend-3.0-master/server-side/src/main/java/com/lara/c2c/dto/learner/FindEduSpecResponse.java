package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.EducationSpecialization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindEduSpecResponse{
	
	private List<EducationSpecialization> eduSpecList = Collections.emptyList();
}
