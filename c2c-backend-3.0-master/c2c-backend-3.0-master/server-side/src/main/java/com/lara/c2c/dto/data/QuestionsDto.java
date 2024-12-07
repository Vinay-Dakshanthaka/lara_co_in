package com.lara.c2c.dto.data;
import java.util.List;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsDto {
	private String questionText;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String optionFive;
	private String correctAnswer;
	private String description;
	@Override
	public String toString() {
		return "QuestionDto [questionText=" + questionText + ", optionOne=" + optionOne + ", optionTwo=" + optionTwo
				+ ", optionThree=" + optionThree + ", optionFour=" + optionFour + ", correctAnswer=" + correctAnswer
				+ ", description=" + description + "]";
	}
}
