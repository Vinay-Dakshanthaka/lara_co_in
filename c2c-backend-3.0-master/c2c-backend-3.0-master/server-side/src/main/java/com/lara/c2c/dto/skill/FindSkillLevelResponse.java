package com.lara.c2c.dto.skill;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.dto.BaseResponse;
import com.lara.c2c.model.SkillLevel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class FindSkillLevelResponse extends BaseResponse{
	private List<SkillLevel> skillLevelData = Collections.emptyList();
}
