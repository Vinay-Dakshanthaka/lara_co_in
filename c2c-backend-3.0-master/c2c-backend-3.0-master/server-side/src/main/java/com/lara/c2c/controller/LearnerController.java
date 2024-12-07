package com.lara.c2c.controller;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lara.c2c.dto.course.CoursePackageResponse;
import com.lara.c2c.dto.learner.CourseStatsResponse;
import com.lara.c2c.dto.learner.FindLearnersResponse;
import com.lara.c2c.dto.learner.LearnerProfileReqResp;
import com.lara.c2c.dto.oncmp.ProgramSaveDTORequest;
import com.lara.c2c.dto.oncmp.SaveLearnerLogicDTO;
import com.lara.c2c.dto.tpo.LearnerDetailForTpo;
import com.lara.c2c.dto.user.ClgUserRespForTpo;
import com.lara.c2c.dto.user.UserDetailDto;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.LearnerProgram;
import com.lara.c2c.service.LearnerService;
import com.lara.c2c.service.LearnerServiceImpl;
import com.lara.c2c.utility.ApiStatus;
//import com.lara.c2c.service.MailService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;
import com.lara.c2c.utility.Utils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/learner")
public class LearnerController {	
	
	@Autowired  
    private LearnerService learnerService; 	
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private LearnerServiceImpl learnerServiceImpl;
	
	@GetMapping("/findAll")
	public ResponseEntity<FindLearnersResponse> retrieveAllLearnerData() {	
		
		FindLearnersResponse learnersResponse = new FindLearnersResponse();
		learnersResponse.setLearnersData(learnerService.getAllLearners());
		
		if (Constants.isRequestFailed(learnersResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(learnersResponse);
		}
		return ResponseEntity.ok().body(learnersResponse);	
	}
	
	
	/*@PostMapping("/addLearner")
	public ResponseEntity<String> registerLearner( @RequestBody Learner learnerRequest) throws Exception {					
		
		AddLearnerResponse learnerResponse = new AddLearnerResponse();
		String activationCode =  ""+ServiceUtils.generateActivationNumber();
		learnerRequest.setActivationCode(activationCode);
		String userId = learnerService.addLearner(learnerRequest);
		learnerResponse.setUserId(userId);

		if (Constants.isRequestFailed(learnerResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.buildResponseErrorMessage(learnerResponse));
		}
		/*try {
			mailService.sendEmail(learnerRequest.getEmail(), activationCode, learnerId);
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
		}*/
		
		/*return ResponseEntity.ok().build();	
		
	}*/
	
	/*@PutMapping("/edit/{id}")
	public ResponseEntity<Void> updateLearner(@PathVariable String id, @RequestBody Learner learnerRequest){
		Optional<Learner> learnerOptional = learnerService.getLearner(id);
		if(learnerOptional == null) {
			return ResponseEntity.notFound().build();
		}		
		String userId = learnerService.updateLearnerRecord(learnerRequest);
		if(userId == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();		
	}*/
	
	@PutMapping("/updateProfile/{userId}")
	public ResponseEntity<Void> updateLearnerProfile(@PathVariable String userId, @RequestBody LearnerProfileReqResp learnerProfileRequest){
		Optional<Learner> learnerOptional = learnerService.getLearner(userId);
		if(learnerOptional == null) {
			return ResponseEntity.notFound().build();
		}		
		/*String userId = learnerService.updateLearnerRecord(learnerRequest);
		if(userId == null) {
			return ResponseEntity.notFound().build();
		}*/
		learnerService.updateLearnerProfileRecord(learnerProfileRequest);
		return ResponseEntity.ok().build();		
	}
	
	/*@GetMapping("/get/{id}")
	public Optional<Learner> retrieveLearnerData(@PathVariable String id) {			
		return learnerService.getLearner(id);
	}*/
	
	@GetMapping("/profile/{userId}")
	public List<LearnerProfileReqResp> retrieveLearnerProfile(@PathVariable String userId){
		return learnerService.getLearnerProfile(userId);
	} 
	
	/*@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteLearnerRecord(@PathVariable String id){
		learnerService.deleteLearner(id);
		return ResponseEntity.ok().build();
	}*/


	/**		 
	 * find learner's record by learnerId
	 * @param learnerId
	 * @return learners record
	 */
	/*@GetMapping("/cust/{id}")
	public Learner findOne(@PathVariable("id") String id){
		
		Learner learner = learnerService.getOneRecord(id);
		return learner;
	}*/
	

	@GetMapping("/curltest")
	public static void x(String[] args) {

		String stringUrl = "https://api-alerts.kaleyra.com/v4/?api_key=A776ee59ee5f35743d9d1b1a2e620f474&method=sms&message=test otp&to=7899815050&sender=mylara";
		//stringUrl = "https://api-alerts.kaleyra.com/v4/?api_key=A75bXXXXXXXXXXXXXX&method=sms&message=hello&to=7899815050&sender=KALERA";
		RestTemplate rest = new RestTemplate();
		String obj = rest.getForObject(stringUrl, String.class);
		System.out.println(obj);
	}	
	
	@PreAuthorize("hasRole('ROLE_TPO')")
	@GetMapping("/getLearnersListByClgId")
	public ResponseEntity<ClgUserRespForTpo> findLearnersListByClgId(){		
		String userId = UserInfo.getUserId(hsRequest);
		ClgUserRespForTpo response = learnerService.findLearnersListByClgId(userId);
		return ResponseEntity.ok().body(response);
	} 
	
	@PreAuthorize("hasRole('ROLE_TPO')")
	@GetMapping("getLrDetailForTpo/{userId}")
	public ResponseEntity<LearnerDetailForTpo> getLearnerDetailForTpo(@PathVariable String userId){
		LearnerDetailForTpo response = learnerService._getLearnerDetailForTpo(userId);
		return ResponseEntity.ok().body(response);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getAllLearners")
	public ResponseEntity<List<UserDetailDto>> getAllLearnersData(){
		List<UserDetailDto> response = learnerService._getAllLearnersData();
		return ResponseEntity.ok().body(response);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getUserSubscriptionDetail/{userId}")
	public ResponseEntity<List<UserDetailDto>> getUserSubscriptionDetail(@PathVariable String userId){
		List<UserDetailDto> response = learnerService._getUserSubscriptionDetail(userId);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/findUserDetailByEmail/{email}")
	public ResponseEntity<UserDetailDto> getUserDetailByEmail(@PathVariable String email){
		UserDetailDto response = learnerService._getUserDetailByEmail(email);
		return ResponseEntity.ok().body(response);
	}
	
	/* conline compiler */
	// saving learner program
	@PostMapping("save")
	public ResponseEntity<?> saveLearnerProgram(@RequestBody ProgramSaveDTORequest prog) {
			System.out.println("save:" + prog);
		List<LearnerProgram> list = learnerServiceImpl.saveLearnerProgram(prog);
		System.out.println("programs:" + list.size());
		return (list.isEmpty() || list == null) ?
				ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Utils.errorStatus(Constants.SAVE_ERROR)):
				ResponseEntity.status(HttpStatus.OK).body(list);
	}
	

	@PostMapping("saveLogic")
	public ResponseEntity<?> saveLearnerLogic(@RequestBody SaveLearnerLogicDTO prog) {
			System.out.println("saveLogic:" + prog.getQuestionId());
		//List<LearnerSolution> list = learnerServiceImpl.saveLearnerLogic(prog);
		//System.out.println("programs:" + list.size());
		return null;
	}
	
	
	
	
	// updating learner program by taking program id as an input
	
	@PostMapping("update/{programId}")
	public ResponseEntity<?> updateLearnerProgram(@RequestBody ProgramSaveDTORequest prog, @PathVariable("programId") Integer id) {

		List<LearnerProgram> list = learnerServiceImpl.updateLearnerProgram(prog,id);
		System.out.println(list);
		return (list.isEmpty() || list == null) ?
				ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Utils.errorStatus(Constants.RECORD_NOT_FOUND)):
				ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// fetching learner program taking learner id as input
	@GetMapping("programs")
	public ResponseEntity<?> fetchAllSaveProgram() {
		String userId = UserInfo.getUserId(hsRequest);
		System.err.println(userId);
		List<LearnerProgram> list = learnerServiceImpl.getLearnerById(userId);

		return (list.isEmpty() || list == null)
				? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(new ApiStatus(Constants.ERROR, Constants.RECORD_NOT_FOUND))
				: ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// For handling exception we are using exception handler
	@ExceptionHandler
	public ResponseEntity<ApiStatus> exceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ApiStatus(Constants.ERROR, Constants.RECORD_NOT_FOUND));
	}	
	@PreAuthorize("hasRole('ROLE_TPO')")
	@GetMapping("/findLearnerCourseStats/{learnerId}")
	public ResponseEntity<CourseStatsResponse> getLearnerCourseStats(@PathVariable String learnerId){
		CourseStatsResponse response = learnerService._getLearnerCourseStats(learnerId);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("unsubscribedCourses/{userId}")
	public ResponseEntity<CoursePackageResponse> getUnsubscribedCourses(@PathVariable String userId){
		System.out.println(userId);
		CoursePackageResponse cPackResponse = new CoursePackageResponse();
		
		List<CoursePackage> fetchedList = null; 
		try {
			fetchedList = learnerService.getUnsubscribedCoursesForUser(userId);
		} catch (Exception e) {
			cPackResponse.setExceptionReport(new ExceptionReport(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cPackResponse);
		}
		
		cPackResponse.setCoursePackageList(fetchedList);
		return ResponseEntity.ok().body(cPackResponse);
	}
	
}
