package com.lara.c2c.dto.oncmp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProgramSaveDTORequest {

	private String learnerId;
	private String program;
	private String notes;
	private String title;
}
