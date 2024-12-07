package com.lara.c2c.dto;

public class MicroTopicDTO {
	private String microTopicId;
	private Long questionsCount;
	public MicroTopicDTO(String microTopicId, Long questionsCount) {
		super();
		this.microTopicId = microTopicId;
		this.questionsCount = questionsCount;
	}
	public String getMicroTopicId() {
		return microTopicId;
	}
	public void setMicroTopicId(String microTopicId) {
		this.microTopicId = microTopicId;
	}
	public Long getQuestionsCount() {
		return questionsCount;
	}
	public void setQuestionsCount(Long questionsCount) {
		this.questionsCount = questionsCount;
	}

	
	
	
}
