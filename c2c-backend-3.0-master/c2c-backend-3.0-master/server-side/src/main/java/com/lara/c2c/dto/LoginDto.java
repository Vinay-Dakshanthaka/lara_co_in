package com.lara.c2c.dto;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import com.lara.c2c.model.Learner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto implements HttpSessionBindingListener {
	  
	  private String accountId;
	  private boolean alreadyLoggedIn;
	  
	  //setters and getters of all your User dto including accountId...
	  private static Map<LoginDto, HttpSession> logins = new HashMap<LoginDto, HttpSession>();
	  @Override
	  public boolean equals(Object other) {
	    return (other instanceof LoginDto) && (getAccountId() != null) ? 
	     getAccountId().equals(((LoginDto) other).getAccountId()) : (other == this);
	  }
	  @Override
	  public int hashCode() {
	    return (getAccountId() != null) ? 
	     (this.getClass().hashCode() + getAccountId().hashCode()) : super.hashCode();
	  }
	  @Override
	  public void valueBound(HttpSessionBindingEvent event){
	    HttpSession oldSession = logins.get(this);
	    if (oldSession != null) {
	      alreadyLoggedIn = true;
	    } else {
	      logins.put(this, event.getSession());
	    }
	    //Note: you can comment above code and remove comments from below code. removing comments from 
	 //below code will remove old session of user and let the user log-in from new session.
	    //HttpSession session = logins.remove(this);
	    //if (session != null) {
	    //  session.invalidate();
	   //}
	   //logins.put(this, event.getSession());  
	  }
	  @Override
	  public void valueUnbound(HttpSessionBindingEvent event) {
	    logins.remove(this);
	  }
	}//end of class LoginDto