package com.lara.c2c.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.lara.c2c.dto.user.LoggedInUserResponse;

/**
 * riteshk
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {
	
	/*@Value("${application.environment.live}")
	private static boolean liveEnvironment;*/
	
    public static void setUserId(HttpServletRequest request, String userId, String firstName) {
        // For SSO mode, userId = ""; Get userId from OHS request header
		/*
		 * if ("".equals(userId)) { userId =
		 * request.getHeader(Constants.OHS_REQUEST_HEADER); }
		 */
        // Persist userId in HttpSession  
    	System.out.println("sessionid:" + request.getSession().getId() + " setting userId:" + userId);
		request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("firstName", firstName);    	 
        request.getSession().setMaxInactiveInterval(60*90);
        /**
         * NOTE: Only when developing javascript using serve, comment the above
         * line and uncomment the line below. serve doesn't send session
         * information when making ws calls so the server treats each request as
         * a new session and userId is lost. As a workaround, we can store the
         * userId as an attribute on ServletContext, however, this is not
         * recommended and is potentially dangerous if deployed to
         * staging/production.
         * --------------------------------------------------------------------
         * Comment/un-comment return statement in the getUserId function below
         * according to method used to store userId in this function
         */    	
		//request.getSession().getServletContext().setAttribute("userId", userId);  
        //request.getSession().getServletContext().setAttribute("firstName", firstName);        
    }
    
    public static void setUserRole(HttpServletRequest request, String userRole) {    	
		request.getSession().setAttribute("userRole", userRole);    	
		//request.getSession().getServletContext().setAttribute("userRole", userRole);    	    	    
    }

    public static String getUserId(HttpServletRequest request) {
    	String userId =  (String) request.getSession().getAttribute("userId");
    	//System.out.println("sessionid:" + request.getSession().getId() + " reading userId:" + userId);
    	//String userId = ((String) request.getSession().getServletContext().getAttribute("userId")); 	
    	return userId;
    }
    
    public static LoggedInUserResponse getUserDetails(HttpServletRequest request) {  
    	String userId =  ((String) request.getSession().getAttribute("userId"));
    	String firstName =  (String) request.getSession().getAttribute("firstName");
    	//String userId = (String) request.getSession().getServletContext().getAttribute("userId");
    	//String firstName = (String) request.getSession().getServletContext().getAttribute("firstName");
    	LoggedInUserResponse response = new LoggedInUserResponse(userId, firstName);
    	return response;
    }
}







