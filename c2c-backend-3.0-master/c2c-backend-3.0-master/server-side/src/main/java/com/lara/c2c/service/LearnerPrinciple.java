package com.lara.c2c.service;

import com.lara.c2c.model.Learner;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor


public class LearnerPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String userId;

    private String firstName;

    private String mobileNo;

    private String email;
    
    private String otp;
    
    private int status;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    
    public LearnerPrinciple(String userId, 
    		String firstName, String email, String otp, String mobileNo, String password, int status,
    		Collection<? extends GrantedAuthority> authorities) {
			this.userId = userId;
			this.firstName = firstName;
			this.email = email;
			this.otp = otp;
			this.mobileNo = mobileNo;
			this.password = password;
			this.status = status;
			this.authorities = authorities;
			}
    public static LearnerPrinciple build(Learner learner) {
        List<GrantedAuthority> authorities = learner.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new LearnerPrinciple(
        		learner.getUserId(),
        		learner.getFirstName(),               
        		learner.getEmail(),
        		learner.getOtp(),
        		learner.getMobileNo(),
        		learner.getPassword(),
        		learner.getStatus(),
                authorities
        );
    }


    public String getFirstName() {
        return firstName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getOtp() {
        return otp;
    }
    
    public String getMobileNo() {
        return mobileNo;
    }    

    @Override
    public String getPassword() {
        return password;
    }
    
    public int getStatus() {
        return status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        LearnerPrinciple user = (LearnerPrinciple) o;
        return Objects.equals(userId, user.userId);
    }
	@Override
	public String getUsername(){		
		return userId+"::"+otp+"::"+firstName+"::"+mobileNo+"::"+email+ "::"+status;
	}
}