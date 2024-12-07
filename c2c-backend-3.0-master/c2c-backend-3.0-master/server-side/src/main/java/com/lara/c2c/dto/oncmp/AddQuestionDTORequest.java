package com.lara.c2c.dto.oncmp;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Setter
@Getter
@ToString
public class AddQuestionDTORequest {

	private String questions;
	private String description;
	private Integer topicId;
	private Integer levelId;
}
