package com.lara.c2c.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.lara.c2c.constants.ApplicationConfig;
import com.lara.c2c.dto.CoursePackageInfo;
import com.lara.c2c.dto.LoginDto;
import com.lara.c2c.dto.OtpData;
import com.lara.c2c.dto.UserAndCoursePackagesInfo;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.dto.exam.TotalScoreResponse;
import com.lara.c2c.dto.learner.AddLearnerResponse;
import com.lara.c2c.dto.learner.CourseStatsResponse;
import com.lara.c2c.dto.learner.LearnerAverageMarks;
import com.lara.c2c.dto.learner.LearnerCourseStats;
import com.lara.c2c.dto.learner.LearnerProfileReqResp;
import com.lara.c2c.dto.tpo.LearnerCoursePackDto;
import com.lara.c2c.dto.tpo.LearnerDetailForTpo;
import com.lara.c2c.dto.user.AuthenticateUserRequest;
import com.lara.c2c.dto.user.AuthenticateUserResponse;
import com.lara.c2c.dto.user.ClgUserRespForTpo;
import com.lara.c2c.dto.user.UserDetailDto;
import com.lara.c2c.message.request.SignUpForm;
import com.lara.c2c.model.Cart;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.ExceptionReport;
//import com.lara.c2c.dto.user.AuthenticateUserResponse;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.LearnerAddress;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.model.LearnerDetail;
import com.lara.c2c.model.LearnerEducation;
import com.lara.c2c.model.LearnerSkill;
import com.lara.c2c.model.LearnerWorkExperience;
import com.lara.c2c.model.Orders_1;
import com.lara.c2c.model.Orders_1_packages_prices;
import com.lara.c2c.model.Role;
import com.lara.c2c.model.RoleName;
import com.lara.c2c.model.TimeSpentOnVideo;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.LearnerSkillRepository;
import com.lara.c2c.repository.LearnerWorkExpRepository;
import com.lara.c2c.repository.LearnerWorkSkillRepository;
import com.lara.c2c.repository.OrderRepository;
import com.lara.c2c.repository.Orders_1Repository;
import com.lara.c2c.repository.RoleRepository;
import com.lara.c2c.repository.TimeSpentOnVideoRepository;
import com.lara.c2c.security.jwt.JwtProvider;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;
import com.lara.c2c.repository.AESEncryptDecrypt;
import com.lara.c2c.repository.CartRepository;
import com.lara.c2c.repository.CollegeRepository;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.CourseRepository;
import com.lara.c2c.repository.ExamCompRecordRepository;
import com.lara.c2c.repository.ExamRecordRepository;
import com.lara.c2c.repository.LearnerAddressRepository;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.repository.LearnerDetailRepository;
import com.lara.c2c.repository.LearnerEducationRepository;
//import com.lara.c2c.utility.ServiceUtils;

@Service
@Transactional
public class LearnerService
{
	
	@Autowired
	Sql2o sql2o;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	HttpServletRequest hsRequest;

	@Autowired
	private MailService mailService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private LearnerRepository learnerRepository;

	@Autowired
	private LearnerDetailRepository learnerDetailRepository;

	@Autowired
	private LearnerEducationRepository learnerEduRepo;

	@Autowired
	private LearnerSkillRepository learnerSkillRepo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AESEncryptDecrypt aesEncDec;

	@Autowired
	private LearnerAddressRepository learnerAddressRepo;

	@Autowired
	private LearnerWorkSkillRepository learnerWorkSkillRepo;

	@Autowired
	LearnerWorkExpRepository learnerWorkExpRepo;
	
	@Autowired
	private CollegeRepository collegeRepository;
	
	@Autowired
	private TimeSpentOnVideoRepository timeSpentOnVideoRepo;
	
	@Autowired
	private ExamRecordRepository examRecordRepo;
	
	@Autowired
	private ExamCompRecordRepository examCompRecordRepo;
	
	@Autowired
	private LearnerCourseRepository learnerCpRepo;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private OrderProcessingService orderProcessingService;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private CoursePackageRepository packageRepo;
	
	@Autowired
	private Orders_1Repository ordersRepo;
	
	public List<Learner> getAllLearners()
	{
		List<Learner> learnerRecords = new ArrayList<>();
		learnerRepository.findAll().forEach(learnerRecords::add);
		return learnerRecords;
	}

	// public String addLearner(Learner learner) throws Exception {
	public AddLearnerResponse addLearner(SignUpForm signUpRequest) throws Exception
	{
		System.out.println("service - addLearner");
		AddLearnerResponse response = new AddLearnerResponse();
		Learner learner = new Learner(signUpRequest.getFirstName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		try
		{
			if (learnerRepository.existsByEmail(signUpRequest.getEmail()))
			{
				throw new Exception(signUpRequest.getEmail() + " is already registered.");
			}
			
			String activationCode = "" + ServiceUtils.generateActivationNumber();
			Set<Role> roles = new HashSet<>();

			Role userRole = roleRepository.findByName(RoleName.ROLE_LEARNER)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			roles.add(userRole);
			learner.setRoles(roles);
			learner.setActivationCode(activationCode);
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
			String datetime = ft.format(dNow);
			String userId = "LR" + datetime.toString();
			learner.setUserId(userId);
			java.util.Date date = new java.util.Date();
			long t = date.getTime();
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
			learner.setCreated(sqlTimestamp);
			learner.setModified(sqlTimestamp);

			LearnerDetail learnerDetail = new LearnerDetail();
			learnerDetail.setUserId(userId);
			learner.setLearnerDetail(learnerDetail);

			LearnerAddress learnerAddressCurrent = new LearnerAddress();
			LearnerAddress learnerAddressPermanent = new LearnerAddress();
			learnerAddressCurrent.setUserId(userId);
			learnerAddressCurrent.setAddressType("Current");
			learnerAddressPermanent.setUserId(userId);
			learnerAddressPermanent.setAddressType("Permanent");
			List<LearnerAddress> learnerAddress = new ArrayList<LearnerAddress>();
			learnerAddress.add(learnerAddressPermanent);
			learnerAddress.add(learnerAddressCurrent);
			learner.setLearnerAddress(learnerAddress);

			//Demo courses allocation
			orderProcessingService.updateLearnerWithFreeDemoPackage(userId);

			
			//Free course allocation
			//orderProcessingService.updateLearnerWithFreeCoursePackages(userId);
			learnerRepository.save(learner);
			System.out.println("learner is saved");
			response.setUserId(learner.getUserId());
			response.setEmail(learner.getEmail());
			response.setActivationCode(learner.getActivationCode());
			return response;
		} catch (Exception ex){
			System.out.println(ex.getMessage());		
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
			return response;
		}

		

		/*
		 * try { mailService.sendEmail(learner.getEmail(), activationCode, userId);
		 * }catch(Exception ex) { System.out.println(ex); }
		 */
	}

	public String updateLearnerRecord(Learner learner)
	{
		return (learnerRepository.save(learner)).getUserId();
	}

	public Optional<Learner> getLearner(String id)
	{
		return learnerRepository.findByUserId(id);
	}

	public void deleteLearner(String id)
	{
		learnerRepository.deleteById(id);
	}

	public Learner getOneRecord(String id)
	{
		return learnerRepository.findLearnerRecordById(id);
	}

	public int activate(String id, String activationCode)
	{
		return learnerRepository.activateLearnerStatus(id, activationCode);
	}

	/*public AuthenticateUserResponse authenticateUserDetailsOld(AuthenticateUserRequest request) throws Exception
	{

		AuthenticateUserResponse response = new AuthenticateUserResponse();
		try
		{
			String userDetail[] = learnerRepository.getUserDetailsForAuth(request.getEmail());
			if (userDetail.length == 1)
			{
				String details[] = userDetail[0].split(",");
				if (request.getPassword().equals(aesEncDec.decrypt(details[1])))
				{
					response.setValidUser(true);
					if (request.getOtp() != null)
					{
						String responseOtpArr[] = details[2].split("::");
						String responseOtp = responseOtpArr[0];
						Timestamp otpTimeStamp = Timestamp.valueOf(responseOtpArr[1]);
						Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
						long timeDiff = currentTimeStamp.getTime() - otpTimeStamp.getTime();
						System.out.println(timeDiff);
						/*
						 * if(timeDiff > 180000) { throw new Exception("Otp expired"); }
						 */
						/*if (request.getOtp().equals(responseOtp))
						{
							response.setUserId(details[0]);
							response.setOtpValidated(true);
							response.setUsername(details[4]);
						} else
						{
							throw new Exception("Please enter correct otp");
						}
					} else
					{
						String otp = ServiceUtils.generateOtp();
						System.out.println(otp);
						String otpTimeStamp = otp + "::" + ServiceUtils.getCurrTimeStamp();
						// uncomment to send otp
						//Object otpResponseObj = sendOtp(otp, details[3]);
						learnerRepository.updateOtp(details[0], otpTimeStamp);
					}
				} else
				{
					throw new Exception("Please enter correct password");
				}
			} else
			{
				throw new Exception("Please enter correct email");
			}
		} catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
			return response;
		}
		return response;
	}*/

	public AuthenticateUserResponse authenticateUserDetails(AuthenticateUserRequest request) throws Exception
	{

		AuthenticateUserResponse response = new AuthenticateUserResponse();
		Authentication authentication = null;
		UserDetails userDetails = null;

		try
		{
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			if (authentication.isAuthenticated())
			{
				response.setValidUser(true);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				userDetails = (UserDetails) authentication.getPrincipal();		
				System.out.println(userDetails.getUsername());
				String userDStrArr[] = userDetails.getUsername().split("::");
				System.out.println(Arrays.toString(userDStrArr));
				if(Integer.parseInt(userDStrArr[userDStrArr.length - 1]) != 2) {
					throw new Exception("Your email id is not activated. Please go to your mail box and serach for a mail from \n" + 
							"donotreply@lara.co.in and click activate. Even search in the spam folder also");
				}
				response.setUserId(userDStrArr[0]);
				response.setUsername(userDStrArr[userDStrArr.length - 2]);
				String jwt = jwtProvider.generateJwtToken(authentication);
				response.setAccessToken(jwt);
				response.setAuthorities(userDetails.getAuthorities());
				
				List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
				List<String> roles = new ArrayList();
				listAuthorities.addAll(userDetails.getAuthorities());
				System.out.println(authentication);
				System.out.println(userDetails);
				System.out.println(userDetails.getAuthorities());
				System.out.println(listAuthorities);
				for(int i=0; i< listAuthorities.size(); i++) {
					System.out.println(listAuthorities.get(i).getAuthority());
					roles.add(listAuthorities.get(i).getAuthority());
				}
				System.out.println("login data ==>" + request.getEmail() + ":" + request.getPassword() + ":" + request.getRole());
				if(!roles.contains("ROLE_ADMIN") && !roles.contains("PG_TESTER")) {
					if(!roles.contains(request.getRole())) {
						throw new Exception("Unauthorized Access.");
					}
				}
			
				UserInfo.setUserId(hsRequest, userDStrArr[0], userDStrArr[3]);
				
				/*LoginDto dto = new LoginDto();
				dto.setAccountId(userDStrArr[0]);
				hsRequest.getSession().setAttribute("UserContext", dto); //this will invoke valueBound method of LoginDto.
			    if(dto.isAlreadyLoggedIn()){
			      hsRequest.removeAttribute("userId");			      
			      hsRequest.removeAttribute("UserContext"); //this will invoke valueUnbound method of LoginDto
			      throw new Exception("You are already logged in from a different session. Please logout first.");
			    }else {
			    	
			    }*/
				
				//List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
				//List<Object> principals = sessionRegistry.getAllPrincipals();
				//System.out.println(sessionRegistry.getAllPrincipals());
				//System.out.println(sessionRegistry.getAllSessions(authentication.getPrincipal(), false));
					
				/*for (Object principal: principals) {
				    if (principal instanceof LearnerPrinciple) {
				    	System.out.println(((LearnerPrinciple) principal).getUserId());
				    }
				}*/
				/*if(StringUtils.isBlank(existingUserId)) {
					UserInfo.setUserId(hsRequest, userDStrArr[0], userDStrArr[3]);
				}else {
					throw new Exception("You are already logged in");
				}*/
				
				/*String authorities = authentication.getAuthorities().stream()
				        .map(GrantedAuthority::getAuthority)
				        .collect(Collectors.joining(","));
				UserInfo.setUserRole(hsRequest, authorities);*/
				/*if (request.getOtp() != null)
				{

					String responseOtp = userDStrArr[1];
					Timestamp otpTimeStamp = Timestamp.valueOf(userDStrArr[2]);
					Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
					long timeDiff = currentTimeStamp.getTime() - otpTimeStamp.getTime();
					//System.out.println(timeDiff);
					if (timeDiff > 180000)
					{
						throw new Exception("Otp expired");
					}
					if (request.getOtp().equals(responseOtp))
					{
						response.setUserId(userDStrArr[0]);
						response.setOtpValidated(true);
						response.setUsername(userDStrArr[3]);
						String jwt = jwtProvider.generateJwtToken(authentication);
						response.setAccessToken(jwt);
						response.setAuthorities(userDetails.getAuthorities());
						UserInfo.setUserId(hsRequest, userDStrArr[0], userDStrArr[3]);

					} else
					{
						throw new Exception("Please enter correct otp");
					}
				} else
				{
					String otp = ServiceUtils.generateOtp();
					System.out.println("this is otp============"+otp);
					String otpTimeStamp = otp + "::" + ServiceUtils.getCurrTimeStamp();
					// uncomment to send otp
					//Object otpResponseObj = sendOtp(otp, userDStrArr[4]);
					learnerRepository.updateOtp(userDStrArr[0], otpTimeStamp);
				}*/
			} else
			{
				throw new Exception("Please enter correct credentials");
			}
		} catch (Exception ex)
		{
			System.out.println("exception while login");
			System.out.println(ex);
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
			return response;
		}

		return response;
	}

	public Learner updateLearnerProfile(Learner learner)
	{
		// int learnerUpdateStatus = learnerRepository.updateLearnerDetail(learner);
		return learner;
	}

	public Object sendOtp(String otp, String mobileNumber)
	{

		StringBuilder restOtpUrl = new StringBuilder();
		restOtpUrl.append(ApplicationConfig.OTPAPIURL);
		restOtpUrl.append("api_key=" + ApplicationConfig.OTPAPIKEY);
		restOtpUrl.append("&method=" + ApplicationConfig.OTPMETHOD);
		restOtpUrl.append("&message=" + ApplicationConfig.OTPMSGTEMPLATE + otp);
		restOtpUrl.append("&to=" + mobileNumber);
		restOtpUrl.append("&sender=" + ApplicationConfig.OTPSENDERID);

		RestTemplate restOtpTemplate = new RestTemplate();
		System.out.println(restOtpUrl);
		Object otpResponseObj = restOtpTemplate.getForObject(restOtpUrl.toString(), Object.class);
		System.out.println(otpResponseObj);
		return otpResponseObj;
	}

	public List<LearnerProfileReqResp> getLearnerProfile(String userId)
	{
		List<LearnerProfileReqResp> learnerProfileResp = learnerRepository.findLearnerByUserId(userId);
		List<LearnerAddress> learnerAddressList = learnerAddressRepo.findByUserId(userId);
		List<LearnerEducation> learnerEduList = learnerEduRepo.findByUserId(userId);
		List<LearnerSkill> learnerSkillList = learnerSkillRepo.findByUserId(userId);
		List<LearnerWorkExperience> learnerWorkExp = learnerWorkExpRepo.findByUserId(userId);
		learnerProfileResp.get(0).setLearnerAddress(learnerAddressList);
		learnerProfileResp.get(0).setLearnerEducation(learnerEduList);
		learnerProfileResp.get(0).setLearnerSkill(learnerSkillList);
		learnerProfileResp.get(0).setLearnerWorkExp(learnerWorkExp);
		return learnerProfileResp;
	}

	public void updateLearnerProfileRecord(LearnerProfileReqResp learnerProfileRequest)
	{
		try
		{  
			learnerRepository.updateLearnerDetail(learnerProfileRequest.getUserId(),
					learnerProfileRequest.getFirstName(), learnerProfileRequest.getMobileNo());
			learnerDetailRepository.updateLearnerProfileDetail(learnerProfileRequest.getUserId(),
					learnerProfileRequest.getLastName(), learnerProfileRequest.getGender(),
					learnerProfileRequest.getDateOfBirth());
			for (LearnerAddress lrAddress : learnerProfileRequest.getLearnerAddress())
			{
				learnerAddressRepo.save(lrAddress);
			}
		} catch (Exception ex)
		{
			System.out.println(ex);
		}

	}

	public OtpData _validateUserOtp(OtpData otpDataRequest) throws Exception
	{
		String userInputOtp = otpDataRequest.getOtp();
		OtpData otpDataResponse = learnerRepository.findByEmailForOtp(otpDataRequest.getEmail());
		String otpDetails [] = otpDataResponse.getOtp().split("::");
		String responseOtp = otpDetails[0];
		Timestamp otpTimeStamp = Timestamp.valueOf(otpDetails[1]);
		Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
		long timeDiff = currentTimeStamp.getTime() - otpTimeStamp.getTime();
		//System.out.println(timeDiff);
//		if (timeDiff > 180000)
//		{
//			throw new Exception("Otp expired");
//		}
		try {
			if (userInputOtp.equals(responseOtp)){
				otpDataResponse.setOtpValidated(true);
				learnerRepository.updateUserStatus(otpDataRequest.getEmail());				
			} else{
				throw new Exception("Please enter correct otp");
			}			
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			otpDataResponse.setExceptionReport(report);
			return otpDataResponse;
		}		
		return otpDataResponse;
	}

	public ClgUserRespForTpo findLearnersListByClgId(String userId) {
		ClgUserRespForTpo response = new ClgUserRespForTpo();
		int collegeId = learnerRepository.findCollegeIdByUserId(userId);
		response.setCollegeName(collegeRepository.getCollegeNameByCollegeId(collegeId)); 
		response.setUserDetails(learnerRepository.findLearnersByCollegeId(collegeId));		
		return response;
	}

	public LearnerDetailForTpo _getLearnerDetailForTpo(String userId) {
		LearnerDetailForTpo response = new LearnerDetailForTpo();
		
		try {
			UserDetailDto userDetail = learnerRepository.findUserDetails(userId);
			response.setUserId(userId);
			response.setFirstName(userDetail.getFirstName());
			response.setMobileNo(userDetail.getMobileNo());
			response.setEmail(userDetail.getEmail());
			int totalWatchedVideos = timeSpentOnVideoRepo.findTotalCoveredMctsByUserId(userId);
			response.setTotalWatchedVideos(totalWatchedVideos);
			response.setTotalCoveredMcts(totalWatchedVideos);
			int totalDueExams = examRecordRepo.findTotalDueExamsByUserId(userId, 0);
			response.setTotalDueExams(totalDueExams);
			/*TotalScoreResponse scoreDetails = examCompRecordRepo.findMarksAndTotalQues(userId);	
			int marksObtained = (int) scoreDetails.getMarksObtained();
			int totalMarks = (int) scoreDetails.getTotalMarks();
			int totalScore = ((marksObtained*100)/totalMarks);
			response.setTotalScore(totalScore);*/
			List<LearnerCoursePackDto> lrCoursePackList = learnerCpRepo.findAllCoursePackByUserId(userId);
			response.setLearnerCoursePackList(lrCoursePackList);
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		
		return response;
	}

	public List<UserDetailDto> _getAllLearnersData() {		
		String userType = "LEARNER";
		List<UserDetailDto> response = learnerRepository.findAllLearners(userType);
		return response;
	}
	
	public UserDetailDto _getUserDetailByEmail(String email) {		
		UserDetailDto userDetail = learnerRepository.findUserDetailsByEmail(email);
		return userDetail;
	}

	public List<UserDetailDto> _getUserSubscriptionDetail(String userId) {
		List<UserDetailDto> response = learnerRepository.findUserSubscriptionDetail(userId);
		return response;
	}

	public int _subscribeUser(String email, String subscriptionCode) {
		return learnerRepository.subscribeUserSunbscription(email, subscriptionCode);
	}	
	
	public int _unsubscribeUser(String email, String unsubscriptionCode){
		return learnerRepository.unsubscribeUserSunbscription(email, unsubscriptionCode);
	}
	
	
	public CourseStatsResponse _getLearnerCourseStats(String learnerId) {
		CourseStatsResponse response = new CourseStatsResponse();
	
		try(Connection con = sql2o.open()){
			final String query = "SELECT SUM(totalAverageMarks) AS totalAverageMarks, sum(totalMarks) AS totalMarks, courseId, courseName, coursePackageId, coursePackageName\n" + 
					"FROM\n" + 
					"(\n" + 
					"   SELECT  AVG(cecr.marks_obtained) AS totalAverageMarks,\n" + 
					"         cecr.total_marks as totalMarks,\n" + 
					"         cc.course_id as courseId,\n" + 
					"         cc.course_name as courseName,\n" + 
					"         cpg.course_package_id as coursePackageId,\n" + 
					"         cpg.course_package_name as coursePackageName\n" + 
					"   FROM map_time_spent_on_video mtsv\n" + 
					" left join cb_exam_records cer on (cer.micro_topic_id = mtsv.micro_topic_id and cer.course_package_id = mtsv.course_package_id and cer.status = 0)\n" + 
					" left join cb_exam_completed_records cecr on (cecr.exam_record_id = cer.exam_record_id)\n" + 
					" inner join lov_micro_topics lmt on (lmt.micro_topic_id = mtsv.micro_topic_id)\n" + 
					" inner join lov_sub_topics lst on (lst.sub_topic_id = lmt.sub_topic_id)\n" + 
					" inner join lov_topics lt on (lt.topic_id = lst.topic_id)\n" + 
					" inner join cb_courses cc on (cc.course_id = lt.course_id)\n" + 
					" inner join map_courses_under_package mcup on (mcup.course_id = cc.course_id)\n" + 
					" inner join cb_course_packages cpg on (cpg.course_package_id = mcup.course_package_id)\n" + 
					"   where mtsv.user_id = :learnerId\n" + 
					"   GROUP BY cecr.micro_topic_id\n" + 
					")AS T group by courseId;";			
			
			List<LearnerAverageMarks> learnerAvgMarksList = con.createQuery(query)
			        .addParameter("learnerId", learnerId)
			        .executeAndFetch(LearnerAverageMarks.class);
			response.setLearnerAvgMarksList(learnerAvgMarksList);
			
			final String query2 = "select count(mtsv.micro_topic_id) as totalVideosWatched,\n" + 
					"count(cer.micro_topic_id) as totalExamsAttended,\n" + 
					"count(cerr.micro_topic_id) as totalExamsDue,\n" + 
					"cc.course_id as courseId,\n" + 
					"cc.course_name as courseName,\n" + 
					"ccp.course_package_id as coursePackageId,\n" + 
					"ccp.course_package_name as coursePackageName\n" + 
					" from map_time_spent_on_video mtsv\n" + 
					" left join cb_exam_records cer on (cer.micro_topic_id = mtsv.micro_topic_id and cer.course_package_id = mtsv.course_package_id and cer.status = 0)\n" + 
					" left join cb_exam_records cerr on (cerr.micro_topic_id = mtsv.micro_topic_id and cerr.course_package_id = mtsv.course_package_id and cerr.status = 1)\n" + 
					" inner join lov_micro_topics lmt on (lmt.micro_topic_id = mtsv.micro_topic_id)\n" + 
					" inner join lov_sub_topics lst on (lst.sub_topic_id = lmt.sub_topic_id)\n" + 
					" inner join lov_topics lt on (lt.topic_id = lst.topic_id)\n" + 
					" inner join cb_courses cc on (cc.course_id = lt.course_id)\n" + 
					" inner join map_courses_under_package mcup on (mcup.course_id = cc.course_id and mcup.course_package_id = mtsv.course_package_id)\n" + 
					" inner join cb_course_packages ccp on (ccp.course_package_id = mtsv.course_package_id)\n" + 
					" where mtsv.user_id = :learnerId"+
					" group by cc.course_id;";
			List<LearnerCourseStats> learnerCourseStatsList = con.createQuery(query2)
			        .addParameter("learnerId", learnerId)
			        .executeAndFetch(LearnerCourseStats.class);
			response.setLearnerCourseStatsList(learnerCourseStatsList);
		}catch(Exception ex) {
			ExceptionReport report = new ExceptionReport(ex);
			response.setExceptionReport(report);
		}
		return response;
	}
	
	public String findUserId(String email) {
		return learnerRepository.findUserId(email);
	}
	
	public ResponseEntity<SuccessStatus> subscribeLearner(UserAndCoursePackagesInfo userCoursePakcagesInfo) {
		//fetch all by ids
		String userId = userCoursePakcagesInfo.getUserId();
		List<LearnerCourse> fetchedOldLearnerCourse = learnerCpRepo.findAllByUserId(userId);
		
		System.out.println(fetchedOldLearnerCourse.size());
		if (fetchedOldLearnerCourse.size() == 0) {
			System.out.println("from if");
			userCoursePakcagesInfo.getCoursePackagesInfo().forEach(cpkg -> {
				learnerCpRepo.save(LearnerCourse.builder().coursePackageId(cpkg.getCoursePackageId()).userId(userId).status(1).build());
			});
			saveOrders(userId, userCoursePakcagesInfo);
		}
		else {
			System.out.println("from else");
			List<CoursePackageInfo> filteredCoursePacakgeInfo = userCoursePakcagesInfo.getCoursePackagesInfo().stream()
				.filter((cpkg) -> {
						for (LearnerCourse learnerCourse : fetchedOldLearnerCourse) {
							System.out.println(cpkg.getCoursePackageId() + " " + learnerCourse.getCoursePackageId());
							if (learnerCourse.getCoursePackageId() == cpkg.getCoursePackageId()) return false;
						}
						return true;
					})
				.collect(Collectors.toList());
//			System.out.println(filteredCoursePacakgeIds);
			
			if (filteredCoursePacakgeInfo.size() > 0) {
				filteredCoursePacakgeInfo.forEach(cpkg -> {
					learnerCpRepo.save(LearnerCourse.builder().coursePackageId(cpkg.getCoursePackageId()).userId(userId).status(1).build());
				});
				
				userCoursePakcagesInfo.setCoursePackagesInfo(filteredCoursePacakgeInfo);
				saveOrders(userId, userCoursePakcagesInfo);
			}
			
			
		}
		
		
		//clear cart
		Cart userCart = cartRepo.findByUser(learnerRepository.findById(userId).get()).get();
		userCart.setCoursePackages(new HashSet<CoursePackage>());
		
		return ResponseEntity.ok().body(SuccessStatus.builder().status(true).message("added all").build());
	}
	
	public void saveOrders(String userId, UserAndCoursePackagesInfo userCoursePakcagesInfo) {
		//add orders
				Orders_1 order = ordersRepo.save(Orders_1.builder()
							.userId(userId)
							.gst(userCoursePakcagesInfo.getGst())
							.totalAmount(userCoursePakcagesInfo.getTotalAmount())
							.subTotal(userCoursePakcagesInfo.getSubTotal())
							.gst(userCoursePakcagesInfo.getGst())
							.build()
						);
				
				order.setCoursesBought(
						userCoursePakcagesInfo.getCoursePackagesInfo().stream()
						.map(
							cpkg -> Orders_1_packages_prices.builder().
								coursePakcage(packageRepo.findByCoursePackageId(cpkg.getCoursePackageId()))
								.name(cpkg.getCoursePackageName())
								.price(cpkg.getCoursePackagePrice())
								.orders_1Id(order)
								.build()
						)
						.collect(Collectors.toList())
						);
	}
	
	public List<CoursePackage> getUnsubscribedCoursesForUser(String userId){
		//fetch old subscribed courses
		List<LearnerCourse> fetchedOldLearnerCourse = learnerCpRepo.findAllByUserId(userId);
		
		List<Integer> subscribedCoursePackageIds = fetchedOldLearnerCourse.stream()
													.map(lrCourse -> lrCourse.getCoursePackageId())
													.collect(Collectors.toList());
		
		return packageRepo.findAllByCoursePackageIdNotIn(subscribedCoursePackageIds);
	}
	
	
	
}
