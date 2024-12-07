package com.lara.c2c.message.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
	
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;      
    
  
    private String mobileNo;      
    
    private int collegeId;
    
    @NotBlank
    @Size(min = 6)
    private String password;   
    
    private Set<String> role;

   
}