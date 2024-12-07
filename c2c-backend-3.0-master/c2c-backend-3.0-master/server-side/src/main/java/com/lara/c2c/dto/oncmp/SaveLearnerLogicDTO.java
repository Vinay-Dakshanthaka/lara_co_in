package com.lara.c2c.dto.oncmp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SaveLearnerLogicDTO {

	private String learnerId;
	private Integer questionId;
	private String solution;

}
