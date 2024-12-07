	package com.lara.c2c.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="cb_learners", uniqueConstraints = {
		@UniqueConstraint(columnNames = "user_id"),
		@UniqueConstraint(columnNames = "email")})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "userId")
public class Learner{
    @Id
    @Column(name="user_id")
    private String userId;
    
    @NotBlank
    @Column(name="first_name")
	private String firstName;	
    
    
	@Column(name="email")
	private String email;
	
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="user_type")
	private String userType = "LEARNER";
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private int status;
	
	@Column(name="activation_code")
	private String activationCode;
	
	@Column(name="otp")
	private String otp;

	@Column(name="created")
	private Date created;
	
	@Column(name="modified")
	private Date modified;	
	
	@Column(name="college_id")
	private int collegeId;
	
	@Column(name="mail_subscription")
	private Integer mailSubscription = 1;
	
	
	/* online compiler */
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	List<LearnerProgram> learnerProgram;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	List<LearnerSolution> learnerSolution;
	
	
	
//	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//	@JoinColumn(name = "college_id", insertable=false,updatable=false)
//    private College college;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "user_id")
    private LearnerDetail learnerDetail;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	@Fetch(value = FetchMode.SUBSELECT)
    private List<LearnerAddress> learnerAddress;
	
	@OneToOne(mappedBy="user")
	private Cart cart;
	
	/*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)	
	@JoinColumn(name = "user_id", insertable=false,updatable=false)
    private List<LearnerEducation> learnerEducation;*/
	
	/*@OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable=false,updatable=false)
    private Set<LearnerSkill> learnerSkill;*/
	
	/*@OneToMany
	@JoinColumn(name="user_id", insertable=false,updatable=false)
	private List<LearnerWorkExperience> learnerWorkExperience;*/
	
	/*@OneToMany
	@JoinColumn(name="user_id", insertable=false,updatable=false)
	private List<LearnerWorkSkill> learnerWorkSkill;*/
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();
	

//	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private UserTest userTest;

	
	public Learner(String firstName, String email, String mobileNo, int collegeId, String password) {
		this.firstName = firstName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.collegeId = collegeId;
		this.password = password;
	}
	
	public Learner(String firstName, String email, String password) {
		this.firstName = firstName;
		this.email = email;		
		this.password = password;
	}
	public Learner(String userId, String firstName, String email, String password, Integer status, Role role) {
		this.userId = userId;
		this.firstName = firstName;
		this.email = email;		
		this.password = password;
		this.status = status;
		this.roles.add(role);
	}
	

   }