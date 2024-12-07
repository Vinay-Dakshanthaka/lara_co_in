package com.lara.c2c.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.OtpData;
import com.lara.c2c.dto.learner.AddLearnerResponse;
import com.lara.c2c.dto.learner.LiveClassDTO;
import com.lara.c2c.dto.user.ActivateUserRequest;
import com.lara.c2c.dto.user.ActivateUserResponse;
import com.lara.c2c.dto.user.AuthPwdResetRequest;
import com.lara.c2c.dto.user.AuthPwdResetResponse;
import com.lara.c2c.dto.user.AuthenticateUserRequest;
import com.lara.c2c.dto.user.AuthenticateUserResponse;
import com.lara.c2c.dto.user.SendEmailResponse;
import com.lara.c2c.dto.user.SubscribeUserRequest;
import com.lara.c2c.dto.user.SubscribeUserResponse;
import com.lara.c2c.dto.user.UnsubscribeUserRequest;
import com.lara.c2c.dto.user.UnsubscribeUserResponse;
import com.lara.c2c.message.request.SignUpForm;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.NewBatch;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.RoleRepository;
import com.lara.c2c.security.jwt.JwtProvider;
import com.lara.c2c.service.CoursePackageService;
import com.lara.c2c.service.LearnerService;
import com.lara.c2c.service.LiveClassService;
import com.lara.c2c.service.MailService;
import com.lara.c2c.service.NewBatchService;
import com.lara.c2c.service.UserService;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    HttpServletRequest hsRequest;
    
    @Autowired
    MailService mailService;

    @Autowired
    LearnerService learnerService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    LearnerRepository learnerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    NewBatchService newBatchService;
    
    @Autowired
    private LiveClassService liveClassService;
    
    @Autowired
    private CoursePackageService coursePackageService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateUserRequest request) throws Exception {
    	System.out.println("signin entered");
    	AuthenticateUserResponse response = learnerService.authenticateUserDetails(request);
       
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        //String jwt = jwtProvider.generateJwtToken(authentication);
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/tpoSignIn")
    public ResponseEntity<?> authenticateTpoUser(@Valid @RequestBody AuthenticateUserRequest request) throws Exception {
    	AuthenticateUserResponse response = learnerService.authenticateUserDetails(request);
       
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        //String jwt = jwtProvider.generateJwtToken(authentication);
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AddLearnerResponse> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {
    	System.out.println("learner registerUser");
    	AddLearnerResponse response = learnerService.addLearner(signUpRequest); 
    	System.out.println("before email:");
    	System.out.println("response.getUserId():" + response.getUserId());
    	if(response.getUserId() != null) {
    		response.setUserId(response.getUserId());
    		try {
    			mailService.sendEmail(response.getEmail(), response.getActivationCode(), response.getUserId(), signUpRequest.getFirstName());
    		}catch(Exception ex) {
    			System.out.println(ex);
    		}
    	}
        return ResponseEntity.ok().body(response);
    }
    
    
    @PostMapping("/validateOtp")
    public ResponseEntity<OtpData> validateUserOtp(@RequestBody OtpData otpData) throws Exception {
    	OtpData respOtpData = learnerService._validateUserOtp(otpData);
    	return ResponseEntity.ok().body(respOtpData);
    }
    
    @PostMapping("/activate")
	public ResponseEntity<ActivateUserResponse> activateLearner(@RequestBody ActivateUserRequest request){
    	ActivateUserResponse response = new ActivateUserResponse();
		int status = learnerService.activate(request.getUserId(), request.getActivationCode());
		if(status == 1) {
			response.setUserActivated(true);
		}else {
			response.setUserActivated(false);
		}
		return ResponseEntity.ok().body(response);
	}	
    
    @GetMapping("/logout")
    public ResponseEntity<Void> signOut(){
		hsRequest.getSession().invalidate();
		System.out.println("Session Destroyed");
		return ResponseEntity.ok().build();
	} 
    
    @PostMapping("/resetPwdAuth")
    public ResponseEntity<AuthPwdResetResponse> authintactePwdReset(@RequestBody AuthPwdResetRequest request){
    	AuthPwdResetResponse response = userService._authentiactePwdReset(request.getEmail());
    	return ResponseEntity.ok().body(response);
    } 
    
    @PostMapping("/resetPassword")
    public ResponseEntity<AuthPwdResetResponse> changePassword(@RequestBody AuthPwdResetRequest request){
    	AuthPwdResetResponse response = userService._changePassword(request);
    	return ResponseEntity.ok().body(response);
    } 
    
    @PostMapping("/updatePassword")
    public ResponseEntity<AuthPwdResetResponse> updatePasswordByUser(@RequestBody AuthPwdResetRequest request){
    	String userId = UserInfo.getUserId(hsRequest);
    	AuthPwdResetResponse response = userService._updatePasswordByUser(request, userId);
    	return ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/subscribe")
	public ResponseEntity<SubscribeUserResponse> subscribeUser(@RequestBody SubscribeUserRequest request){
    	SubscribeUserResponse response = new SubscribeUserResponse();
		int status = learnerService._subscribeUser(request.getEmail(), request.getSubscriptionCode());
		if(status == 1) {
			response.setUserSubscribed(true);
		}else {
			response.setUserSubscribed(false);
		}
		return ResponseEntity.ok().body(response);
	}
    
    @PostMapping("/unsubscribe")
	public ResponseEntity<UnsubscribeUserResponse> unsubscribeUser(@RequestBody UnsubscribeUserRequest request){
    	UnsubscribeUserResponse response = new UnsubscribeUserResponse();
		int status = learnerService._unsubscribeUser(request.getEmail(), request.getUnsubscriptionCode());
		if(status == 1) {
			response.setUserUnsubscribed(true);
		}else {
			response.setUserUnsubscribed(false);
		}
		return ResponseEntity.ok().body(response);
	}	
    
    @PostMapping("sendUnsubscriptionMail")
    public ResponseEntity<SendEmailResponse> sendUnsubscriptionInfo(@RequestBody UnsubscribeUserRequest request){
    	SendEmailResponse response = mailService._sendUnsubscriptionInfo(request.getEmail(), request.getUnsubscriptionCode());
    	return ResponseEntity.ok().body(response);
    }
    @GetMapping("/findAllNewBatches")
	public ResponseEntity<List<NewBatch>> getAllNewBatches()
	{
		List<NewBatch> newBatchResponse = newBatchService._getAllNewBatches();
		return ResponseEntity.ok().body(newBatchResponse);
	}    ///    to access from the client     === > http://localhost:8080/c2c/api/auth/findAllNewBatches
    
    @GetMapping("/readAllLiveClassesForHomePage")
	public ResponseEntity<List<LiveClassDTO>> readAllLiveClassesForHomePage()
	{
		List<LiveClassDTO> liveClasses = liveClassService.readAllLiveClassesForHomePage();
		return ResponseEntity.ok().body(liveClasses);
	} 
    
    @GetMapping("/readCoursePackage/{id}")
    public ResponseEntity<CoursePackage> readCoursePackage(@PathVariable Integer id){
    	CoursePackage coursePackage = null;
    	coursePackage = coursePackageService.getCoursePackageById(id);
    	return ResponseEntity.ok().body(coursePackage);
    }
    
}