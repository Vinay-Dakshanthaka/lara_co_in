package com.lara.c2c.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lara.c2c.utility.UserInfo;


@Component
//@Order(1)
public class SessionFilter implements Filter {

    // public service names, that should work without authorization
    private static final List<String> IGNORE_PATTERNS;

    // pattern and matcher for extracting service name based on call url
    private static final Pattern URL_PATTERN = Pattern.compile("\\/api\\/(.*)\\/");
    private static final Matcher m = URL_PATTERN.matcher("");

    static {
        IGNORE_PATTERNS = new ArrayList<>(5);

        IGNORE_PATTERNS.add("user");
        IGNORE_PATTERNS.add("home");
        //IGNORE_PATTERNS.add("learner");
        IGNORE_PATTERNS.add("assets");
        //IGNORE_PATTERNS.add("orderp");
        IGNORE_PATTERNS.add("auth");
        //IGNORE_PATTERNS.add("cPackage");  
        IGNORE_PATTERNS.add("public");
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("SessionFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	System.out.println("I am in doFilter:" + ((HttpServletRequest) servletRequest).getRequestURI());// + ":" +  (servletRequest instanceof HttpServletRequest) + ":" + ((HttpServletRequest) servletRequest).getCookies().length);
    	//System.out.println("servletRequest instanceof HttpServletRequest:" + (servletRequest instanceof HttpServletRequest));
        // should be all the time
//    	for(Cookie c1 : ((HttpServletRequest) servletRequest).getCookies()) {
//    		System.out.println(c1.getName() + ":" + c1.getValue());
//    	}
//        if (servletRequest instanceof HttpServletRequest) {
//        	String userName1 = UserInfo.getUserId((HttpServletRequest) servletRequest);
//            // get the request path
//            String path = ((HttpServletRequest) servletRequest).getRequestURI();
//            m.reset(StringUtils.lowerCase(path));
//
//            // if its a service call - should be all the time
//            if (m.find()) {
//                // get the service name
//                String serviceName = m.group(1);
//                // if its not in the public service list
//                if (!IGNORE_PATTERNS.contains(StringUtils.lowerCase(serviceName))) {
//                    // get current user name from the session info
//                    String userName = UserInfo.getUserId((HttpServletRequest) servletRequest);
//                    System.out.println(userName);
//                    // if the user name is empty or null -> no session is associated, send response back
//                    if (StringUtils.isBlank(userName)) {
//                        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
//                        return;
//                    }
//                }
//            }
//        }

        // continue as normal
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("SessionFilter destroyed.");
    }
}