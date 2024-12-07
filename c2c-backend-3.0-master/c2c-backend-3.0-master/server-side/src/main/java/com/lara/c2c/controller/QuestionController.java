package com.lara.c2c.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lara.c2c.utility.ApiStatus;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;
import com.lara.c2c.utility.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.dto.exam.FindExQuesDetailResp;
import com.lara.c2c.dto.oncmp.AddQuestionDTORequest;
import com.lara.c2c.dto.oncmp.SaveLearnerLogicDTO;
import com.lara.c2c.model.LearnerLevel;
import com.lara.c2c.model.LearnerQuestion;
import com.lara.c2c.model.LearnerTopic;
import com.lara.c2c.model.Question;
import com.lara.c2c.service.QuestionService;
import com.lara.c2c.service.QuestionServiceimpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ques")
public class QuestionController {	
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private QuestionServiceimpl questionServiceImpl;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/upload") 
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) {
		
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.TEMP_FILE_UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            questionService.insertQuestionRecords(path.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }
	
	@GetMapping("/findAll")
	List<Question> findAllQ(){
		List<Question> questionsList =  questionService.getAllQuestions();
		return questionsList;
		
	}
	
//	@GetMapping("/findQues/{videoId}/{microTopicId}")
//	List<Question> findQuestionForExamv1(@PathVariable int videoId, @PathVariable int microTopicId){
//		List<Question> questionList = questionService.getQuestionForExam(videoId, microTopicId);
//		return questionList;
//	}
	
	@GetMapping("/findQues/{videoId}/{microTopicId}")
	public ResponseEntity<FindExQuesDetailResp> findQuestionForExam(@PathVariable String videoId, @PathVariable String microTopicId){
		FindExQuesDetailResp response = questionService.getQuestionDetailsForExam(videoId, microTopicId);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/findCumulativeQues/{microTopicIdsStr}/{totalQuestions}")
	public ResponseEntity<FindExQuesDetailResp> findCumQuestionForExam(@PathVariable String microTopicIdsStr, @PathVariable int totalQuestions){
		FindExQuesDetailResp response = questionService.getCumQuestionDetailsForExam(microTopicIdsStr, totalQuestions);
		return ResponseEntity.ok().body(response);
	}
	
	
	
	@GetMapping("/findQuesAnsByCexId/{cexRecId}")
	public ResponseEntity<FindExQuesDetailResp> getQuesAnsByQIds(@PathVariable int cexRecId){
		FindExQuesDetailResp response = questionService._getQuesAnsByQIds(cexRecId);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/findCumQuesAnsByCexId/{cumExamRecordId}")
	public ResponseEntity<FindExQuesDetailResp> getCumQuesAnsByQIds(@PathVariable int cumExamRecordId){
		String userId = UserInfo.getUserId(hsRequest);
		FindExQuesDetailResp response = questionService._getCumQuesAnsByQIds(cumExamRecordId, userId);
		return ResponseEntity.ok().body(response);
	}
	/* online compiler */
	
	
	// Fetching list of levels
	@GetMapping("level")
	public ResponseEntity<?> getAllLevel() {
		List<LearnerLevel> levelList = questionServiceImpl.getAllLevel();
		return (levelList.isEmpty())
				? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(Utils.errorStatus(Constants.RECORD_NOT_FOUND))
				: ResponseEntity.status(HttpStatus.OK).body(levelList);
	}

	// Fetching list of topics
	@GetMapping("topic")
	public ResponseEntity<?> getAllTopics() {
		List<LearnerTopic> topicList = questionServiceImpl.getAllTopic();
		return (topicList.isEmpty())
				? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(Utils.errorStatus(Constants.RECORD_NOT_FOUND))
				: ResponseEntity.status(HttpStatus.OK).body(topicList);
	}

	// adding question 
	@PostMapping("add")
	public ResponseEntity<?> addQuestions(@RequestBody AddQuestionDTORequest questionDTO) {
		ApiStatus apiStatus = questionServiceImpl.addQuestions(questionDTO);
		return (apiStatus.getStatus().equals(Constants.ERROR))
				? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiStatus)
				: ResponseEntity.status(HttpStatus.OK).body(apiStatus);
	}

	// Fetching question as per topic id and level id
	@GetMapping("get/{topicId}/{levelId}")
	public ResponseEntity<?> getQuestions(@PathVariable("topicId") Integer topicId,
			@PathVariable("levelId") Integer levelId) {
		List<LearnerQuestion> list = questionServiceImpl.getQuestions(topicId, levelId);
						return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	// saving user's logic
	@PostMapping("save")
	public ResponseEntity<?> saveQuestions(@RequestBody SaveLearnerLogicDTO solutionDTO) {
		ApiStatus apiStatus = questionServiceImpl.saveLearnerLogic(solutionDTO);
		return (apiStatus.getStatus().equals(Constants.ERROR))
				? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiStatus)
				: ResponseEntity.status(HttpStatus.OK).body(apiStatus);
	}	
	
	
}
