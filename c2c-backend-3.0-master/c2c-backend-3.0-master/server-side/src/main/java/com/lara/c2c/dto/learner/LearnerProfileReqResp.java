package com.lara.c2c.dto.learner;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.lara.c2c.model.LearnerAddress;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.LearnerWorkExperience;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LearnerProfileReqResp{
	
	String userId;
	String firstName;
	String email;
	String mobileNo;
	
	String lastName;
	String gender;
	Date dateOfBirth;
	
	public LearnerProfileReqResp(String userId, String firstName, String email, String mobileNo, String lastName, String gender, Date dateOfBirth) {
		this.userId = userId;
		this.firstName = firstName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
	List<LearnerAddress> learnerAddress = Collections.emptyList();
	List<LearnerEducation> learnerEducation = Collections.emptyList();
	List<LearnerSkill> learnerSkill = Collections.emptyList();
	List<LearnerWorkExperience> learnerWorkExp = Collections.emptyList();
}
