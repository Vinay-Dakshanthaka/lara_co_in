package com.lara.c2c.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.exam.CumExamRecordResponse;
import com.lara.c2c.dto.exam.ExamCompDetailResponse;
import com.lara.c2c.dto.exam.ExamCompRecordResponse;
import com.lara.c2c.dto.exam.FindAllCumRecResponse;
import com.lara.c2c.dto.exam.FindExamRecordResponse;
import com.lara.c2c.dto.exam.TotalPointsDto;
import com.lara.c2c.dto.topic.MicroTopicDto;
import com.lara.c2c.model.CumulativeExamRecord;
import com.lara.c2c.model.ExamCompletedRecord;
import com.lara.c2c.service.CumulitiveExamService;
import com.lara.c2c.service.ExamRecordService;
import com.lara.c2c.service.VideoService;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/examRecord")
public class ExamRecordController {
	
	@Autowired
	private ExamRecordService examRecordService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private CumulitiveExamService cumExamService;
	
	@Autowired
	private VideoService videoService;
	
	
	@PostMapping("/addExamCompletedRecord")
	public ResponseEntity<ExamCompletedRecord> createExamcompletedRecord( @RequestBody ExamCompletedRecord examCompletedRecordRequest) {
		String userId = UserInfo.getUserId(hsRequest);
		examCompletedRecordRequest.setUserId(userId);
		if(examRecordService._findExamCompRecordByExamRecId(examCompletedRecordRequest.getExamRecordId()).size() == 0) {
			examRecordService.updateDueExamStatus(0,examCompletedRecordRequest.getExamRecordId(), userId);
		}
		ExamCompletedRecord examCompRecord = examRecordService._createExamCompletedRecord(examCompletedRecordRequest);					
		return ResponseEntity.ok().body(examCompRecord);	
		
	}

	@PostMapping("/saveCumulativeExamRecord")
	public ResponseEntity<CumExamRecordResponse> addCumulativeExRecord(@RequestBody CumulativeExamRecord request){
		String userId = UserInfo.getUserId(hsRequest);
		CumExamRecordResponse response = cumExamService._addCumulativeExRecord(request, userId);		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/updateCumulativeExamRecord")
	public ResponseEntity<CumulativeExamRecord> updateCumExRecord(@RequestBody CumulativeExamRecord request){
		String userId = UserInfo.getUserId(hsRequest);
		CumulativeExamRecord response = cumExamService._updateCumExRecord(request, userId);		
		return ResponseEntity.ok().body(response);
	}
	/*@GetMapping("findByJoin")
	public ResponseEntity<List<ExamRecordDto>> findExRecord(){		
		return ResponseEntity.ok().body(examRecordService.test123());	
	}*/
	
	@GetMapping("/findAllExamRecords")
	public ResponseEntity<List<FindExamRecordResponse>> getAllExamRecordsbyUserId(){
		String userId = UserInfo.getUserId(hsRequest);
		List<FindExamRecordResponse > exRecResp = examRecordService._getAllExamRecordsbyUserId(userId);		
		return ResponseEntity.ok().body(exRecResp);
		
	}
	
	
	
	@GetMapping("/findGrpCompExamRecords")
	public ResponseEntity<List<ExamCompRecordResponse>> getGrpCompExRecbyUserId(){
		String userId = UserInfo.getUserId(hsRequest);
		List<ExamCompRecordResponse> exCompGrpRecResp = examRecordService._getGrpCompExRecbyUserId(userId);		
		return ResponseEntity.ok().body(exCompGrpRecResp);
		
	}
	
	@GetMapping("/findAllCompExamRecords/{exRecId}")
	public ResponseEntity<List<ExamCompDetailResponse>> getAllCompExRecByExRecId(@PathVariable int exRecId){
		List<ExamCompDetailResponse> exCompDetailRecResp = examRecordService._getAllCompExRecByExRecId(exRecId);		
		return ResponseEntity.ok().body(exCompDetailRecResp);
		
	}
	
	@GetMapping("/findCumulativeExRecord/{cumExRecId}")
	public ResponseEntity<List<CumulativeExamRecord>> getAllCumExRecByCumExRecId(@PathVariable int cumExRecId){
		String userId = UserInfo.getUserId(hsRequest).toUpperCase();
		List<CumulativeExamRecord> cumExRecordResp = cumExamService._getAllCumExRecByCumExRecId(cumExRecId, userId);		
		return ResponseEntity.ok().body(cumExRecordResp);
		
	}
	
	@GetMapping("/findAllCumulativeExRecords")
	public ResponseEntity<List<FindAllCumRecResponse>>getAllCumExRecordsByUserId(){
		String userId = UserInfo.getUserId(hsRequest);
		List<FindAllCumRecResponse> response = cumExamService._getAllCumExRecordsByUserId(userId);
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping("/findMiTListByCumExRecId/{cumExamRecordId}")	
	public ResponseEntity<List<MicroTopicDto>>getMiTListByCumExRecId(@PathVariable int cumExamRecordId){
		String userId = UserInfo.getUserId(hsRequest);
		List<MicroTopicDto> response = cumExamService._getMiTListByCumExRecId(cumExamRecordId, userId);
		return ResponseEntity.ok().body(response);		
	}
	
	@GetMapping("/findWatchedMicroTopicIds/{coursePackageId}")
	public ResponseEntity<List<String>> getWatchedMicroTopicIds(@PathVariable int coursePackageId){
		String userId = UserInfo.getUserId(hsRequest);
		List<String> response = examRecordService._getWatchedMicroTopicIds(coursePackageId, userId);
		return ResponseEntity.ok().body(response);	
	}
	
	@GetMapping("/findLearnerPoints")
	public ResponseEntity<TotalPointsDto> getTotalVideoPoints(){
		TotalPointsDto response = new TotalPointsDto();
		String userId = UserInfo.getUserId(hsRequest);
		PointsResponse pr = examRecordService.getExamPointsForUser(userId);
		if(pr !=null) {
			response.setExamPoints(pr.getPoint());
			response.setLastUpdatedExamPoints(pr.getLastUpdated());
		}		
		PointsResponse cpr = examRecordService.getCumExamPointsForUser(userId);
		if(cpr != null) {
			response.setCumExamPoints(cpr.getPoint());
			response.setLastUpdatedCumExamPoints(cpr.getLastUpdated());
		}
		PointsResponse vpr = videoService.getVideoPointsForUser(userId);
		if(vpr != null) {
			response.setVideoPoints(vpr.getPoint());
			response.setLastUpdatedVideoPoints(vpr.getLastUpdated());
		}
		
		double totalPoints = response.getVideoPoints() + response.getExamPoints() + response.getCumExamPoints();
		response.setTotalPoints(totalPoints);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/findCumExamPoints")
	public ResponseEntity<PointsResponse> getTotalCumExamPoints(){
		String userId = UserInfo.getUserId(hsRequest);
		PointsResponse response = examRecordService.getCumExamPointsForUser(userId);
		return ResponseEntity.ok().body(response);
	}
}
