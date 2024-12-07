package com.lara.c2c.dto.user;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;

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
public class ClgUserRespForTpo extends BaseResponse{
	
	private String collegeName;
	private List<UserDetailDto> userDetails = Collections.emptyList();
}
