package com.lara.c2c.dto.skill;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.Skill;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FindSkillsResponse extends BaseResponse{
	private List<Skill> skillsData = Collections.emptyList();
}
