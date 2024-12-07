package com.lara.c2c.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="college_admin")
public class CollegeAdmin{
		
	@Id
    @Column(name="user_id")
    private String userId;    
    
	@Column(name="email")
	private String email;	
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="password")
	private String password;
	
	@Column(name="college_id")
	private int college_id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "college_id", insertable=false,updatable=false)
    private College college;
		
}
