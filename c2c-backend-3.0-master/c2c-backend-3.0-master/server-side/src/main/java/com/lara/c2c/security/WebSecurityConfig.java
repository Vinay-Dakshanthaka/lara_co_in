package com.lara.c2c.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.lara.c2c.security.jwt.JwtAuthEntryPoint;
import com.lara.c2c.security.jwt.JwtAuthTokenFilter;
import com.lara.c2c.service.LearnerDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LearnerDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
    	return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        web.httpFirewall(firewall);
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.formLogin()
    	.usernameParameter("email")
    	.permitAll()
    	.and()
    	.logout()
    	.permitAll();
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
//                .antMatchers("/**").permitAll()
//                //.antMatchers("/payment**").permitAll()
//                .antMatchers("/assets**").permitAll()
//                .antMatchers("/api/auth/**").permitAll()
//                //.antMatchers("/api/cPackage/**").permitAll()
//                .antMatchers("/api/user/**").permitAll()
//                //.antMatchers("/api/learner/**").permitAll()
//                //.antMatchers("/api/orderp/**").permitAll()
//                //.antMatchers("/api/ques/**").permitAll()
//                //.antMatchers("").
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);        
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}