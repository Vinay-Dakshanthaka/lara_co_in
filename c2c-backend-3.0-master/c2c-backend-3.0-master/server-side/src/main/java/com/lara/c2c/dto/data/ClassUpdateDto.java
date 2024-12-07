package com.lara.c2c.dto.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassUpdateDto {
    private Integer id;
	
	private String date;
	
	private String startTime;
	
	private String endTime;
	
	private String batchName;
	
	private String instructor;
	
	private String classType;
	
	private String topic;
	
	private String place;

     
}
