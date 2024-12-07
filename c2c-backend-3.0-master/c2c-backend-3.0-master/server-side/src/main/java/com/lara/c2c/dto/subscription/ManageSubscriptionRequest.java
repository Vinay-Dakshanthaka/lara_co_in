package com.lara.c2c.dto.subscription;

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
public class ManageSubscriptionRequest {
		
		private String userId;
		private List<Integer> actCoursePkgList = Collections.emptyList();
		private List<Integer> deActCoursePkgList = Collections.emptyList();

}
