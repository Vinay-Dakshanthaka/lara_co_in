package com.lara.c2c.dto.data;

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
public class UserDto {
	private String firstName;
	private String lastName;
	private String email;
	private Long phoneNumber; 
	private Integer chooseQuestions;
}
