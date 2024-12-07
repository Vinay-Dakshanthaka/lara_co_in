package com.lara.c2c.dto;

import java.util.Collections;
import java.util.List;

import com.lara.c2c.model.Banner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FindBannerResponse extends BaseResponse{
	
	private List<Banner> bannersData = Collections.emptyList();
}
