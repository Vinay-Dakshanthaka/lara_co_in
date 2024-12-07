package com.lara.c2c.dto.user;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.lara.c2c.dto.BaseResponse;


public class AuthenticateUserResponse extends BaseResponse{		

	private String userId;
	private boolean isValidUser;
	private boolean OtpValidated;
	private String username;

	private String token;
    private String type = "Bearer";
    private Collection<? extends GrantedAuthority> authorities;
    
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public boolean isValidUser(){
		return isValidUser;
	}
	public void setValidUser(boolean isValidUser){
		this.isValidUser = isValidUser;
	}
	public boolean isOtpValidated(){
		return OtpValidated;
	}
	public void setOtpValidated(boolean otpValidated){
		OtpValidated = otpValidated;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getAccessToken() {
		return token;
	}
	
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
    
    public String getTokenType() {
    	return type;
    }
    
    public void setTokenType(String tokenType) {
    	this.type = tokenType;
    }
    
    
    public Collection<? extends GrantedAuthority> getAuthorities(){
    	return authorities;
    }
    
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    	this.authorities = authorities;
    }
    public AuthenticateUserResponse() {
    	
    }
    public AuthenticateUserResponse(String accessToken, Collection<? extends GrantedAuthority> authorities) {
    	this.token = accessToken;
    	this.authorities = authorities;
    }
}
