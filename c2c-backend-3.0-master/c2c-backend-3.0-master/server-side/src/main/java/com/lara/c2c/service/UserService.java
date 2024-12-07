package com.lara.c2c.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.user.AuthPwdResetRequest;
import com.lara.c2c.dto.user.AuthPwdResetResponse;
import com.lara.c2c.dto.user.UserDetailDto;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.PasswordResetToken;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.PasswordTokenRepository;
import com.lara.c2c.utility.ServiceUtils;

@Service
public class UserService{
	
	@Autowired
	private LearnerRepository learnerRepo;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private PasswordTokenRepository passwordTokenRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	public UserDetailDto _fetchUserDetails(String userId) {
		return learnerRepo.findUserDetails(userId);		
	}
	
	public AuthPwdResetResponse _authentiactePwdReset(String email) {
		AuthPwdResetResponse response = new AuthPwdResetResponse();
		String token = generatePasswordResetToken();
		
		try {
			UserDetailDto userDetails = learnerRepo.findUserDetailsByEmail(email);
			if (userDetails != null){
				boolean isTokenExist = checkExistingTokenForUser(userDetails.getUserId());
				if(isTokenExist) {
					deletePasswordToken(userDetails.getUserId());
				}
				savePasswordResetToken(userDetails.getUserId(), token);
				String activationCode = token.split("::")[0];
				mailService.sendPwdResetRequestEmail(email, activationCode, userDetails.getUserId(), userDetails.getFirstName());
				response.setValidUser(true);
			}else {
				throw new Exception(email + " is not registered...");
			}
			
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex); 
			response.setExceptionReport(report);
		}
				
		return response;
	   
	}
	
	public String generatePasswordResetToken() {
		String tokenString = ServiceUtils.generateActivationNumber();
		Timestamp currentTime = ServiceUtils.getCurrTimeStamp();
		return tokenString+"::"+currentTime;
	}
	
	public void savePasswordResetToken(String userId, String token) {
		PasswordResetToken myToken = new PasswordResetToken();
		myToken.setUserId(userId);
		myToken.setToken(token);
		passwordTokenRepository.save(myToken).getUserId();
	}

	public AuthPwdResetResponse _changePassword(AuthPwdResetRequest request){
		AuthPwdResetResponse response = new AuthPwdResetResponse();
		String token = request.getActivationCode();
		String userId = request.getUserId();
		String password = request.getPassword();
		String cPassword = request.getCPassword();
		try {
			boolean isValidUser = checkValidUser(userId, token);
			if(isValidUser) {
				response.setValidUser(true);
				String encodedPassword = encoder.encode(password);
				int updatePwdStatus = updateUserPassword(userId, encodedPassword);
				if(updatePwdStatus == 1) {
					response.setPwdChanged(true);
					deletePasswordToken(userId);
				}
			}else {
				throw new Exception("This token is invalid or expired.");
			}
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		
		return response;
	}
	
	public boolean checkValidUser(String userId, String token) {
		PasswordResetToken pwdResetToken = passwordTokenRepository.findByUserId(userId);
		if(pwdResetToken != null) {
			String activationToken = pwdResetToken.getToken().split("::")[0];
			if(token.equals(activationToken)) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean checkExistingTokenForUser(String userId) {
		PasswordResetToken pwdResetToken = passwordTokenRepository.findByUserId(userId);
		if(pwdResetToken != null) {
			return true;
		}
		return false;
	}
	
	public int updateUserPassword(String userId, String encodedPassword) {
		return learnerRepo.updatePassword(userId, encodedPassword);
	}
	
	public void deletePasswordToken(String userId) {
		passwordTokenRepository.deleteTokenByUserId(userId);
	}

	public AuthPwdResetResponse _updatePasswordByUser(AuthPwdResetRequest request, String userId){
		AuthPwdResetResponse response = new AuthPwdResetResponse();
		Authentication authentication = null;
		UserDetailDto userDetails = learnerRepo.findUserDetails(userId);
		
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getEmail(), request.getCurrentPassword()));
			if (authentication.isAuthenticated()){
				response.setValidUser(true);
				String encodedPassword = encoder.encode(request.getNewPassword());
				int updatePwdStatus = updateUserPassword(userId, encodedPassword);
				if(updatePwdStatus == 1) {
					response.setPwdChanged(true);
				}
			}else {
				throw new Exception("Please enter correct current password");
			}
			
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		return response;
	}
}
