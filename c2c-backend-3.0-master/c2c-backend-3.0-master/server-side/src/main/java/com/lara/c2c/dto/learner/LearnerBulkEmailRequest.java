package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.List;

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
public class LearnerBulkEmailRequest {
	
	private List<String> userEmailsList = Collections.emptyList();	
	private String mailSubject;
	private String mailContent;
}
