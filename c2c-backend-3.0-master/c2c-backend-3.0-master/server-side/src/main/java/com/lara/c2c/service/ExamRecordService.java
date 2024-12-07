package com.lara.c2c.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.exam.CumExamRecordResponse;
import com.lara.c2c.dto.exam.ExamCompDetailResponse;
import com.lara.c2c.dto.exam.ExamCompRecordResponse;
import com.lara.c2c.dto.exam.ExamPointDto;
import com.lara.c2c.dto.exam.FindExamRecordResponse;
import com.lara.c2c.model.CorrectOption;
import com.lara.c2c.model.CumulativeExamRecord;
import com.lara.c2c.model.ExamCompletedRecord;
import com.lara.c2c.model.ExamRecord;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.Learner;
import com.lara.c2c.model.LearnerTotalPoint;
import com.lara.c2c.model.VideoPoint;
import com.lara.c2c.repository.CorrectOptionRepository;
import com.lara.c2c.repository.CumulativeExamRepository;
import com.lara.c2c.repository.ExamCompRecordRepository;
import com.lara.c2c.repository.ExamRecordRepository;
import com.lara.c2c.repository.LearnerTotalPointRepository;
import com.lara.c2c.repository.TimeSpentOnVideoRepository;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;

@Service
public class ExamRecordService {
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private ExamRecordRepository exmRecRepo;
	
	@Autowired
	private ExamCompRecordRepository exmCompRecRepo;
	
	@Autowired
	private TimeSpentOnVideoRepository tsvRepo;
	
	@Autowired
	private CorrectOptionRepository correctOptionRepository;	
	
	@Autowired 
	private CumulativeExamRepository cumExamRepo;
	
	@Autowired
	private LearnerTotalPointService lrTotalPointService;
	
	public ExamCompletedRecord _createExamCompletedRecord(ExamCompletedRecord examCompletedRecord) {
		
		//collect questionIds
		String arr[] = examCompletedRecord.getQuestionAnsData().split(","); 
		Map<Integer, String> userQuestionAnsMap = new HashMap<Integer, String>();
		List<String> userAnsKeys = new ArrayList<String>();
		for(int i=0; i<arr.length; i++) {
			String data[]  = arr[i].split("-");
			Integer qId = Integer.parseInt(data[0]);
			String ansKey = "xx";			
			if(data.length >1) {
				ansKey = data[1];
			}			
			userAnsKeys.add(ansKey);
			userQuestionAnsMap.put(qId, ansKey);
		}
		System.out.println(userQuestionAnsMap);
		
		List<CorrectOption> actualQuesAnsList = getCorrectOptionsFromQIds(userQuestionAnsMap.keySet());
		List<String> actualAnsKeyList = new ArrayList<String>();
		int correctAnsCount = 0;
		for(int i=0; i<actualQuesAnsList.size(); i++) {
			actualAnsKeyList.add(actualQuesAnsList.get(i).getAnswerKey());
			if(userAnsKeys.get(i).equals(actualQuesAnsList.get(i).getAnswerKey())) {
				correctAnsCount = correctAnsCount + 1;
			}
		}		
		System.out.println(userAnsKeys);
		java.util.Date date = new java.util.Date();
		long t = date.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
		examCompletedRecord.setMarksObtained(correctAnsCount);
		examCompletedRecord.setTotalMarks(examCompletedRecord.getTotalMarks());
		int totalQuestions = (int) examCompletedRecord.getTotalMarks();
		examCompletedRecord.setTotalQuestions(totalQuestions);
		
		examCompletedRecord.setCompletedDate(sqlTimestamp);
		String microTopicId = exmRecRepo.getMicroTopicIdByExRecId(examCompletedRecord.getExamRecordId());
		examCompletedRecord.setMicroTopicId(microTopicId);
		
		double examPoints = getExamPoints(totalQuestions);
		double examScorePoints = (double)(correctAnsCount*totalQuestions)/100;
		ExamPointDto examPointRecord = exmCompRecRepo.findByMicroTopicIdAndUserId(microTopicId, examCompletedRecord.getUserId());
		if(examPointRecord.getExamPoint() != null) {
			examPoints = fixExamPoints(examPointRecord.getExamPoint(), examPointRecord.getCompletedDate());
		}
		examPoints = examPoints + examScorePoints;
		lrTotalPointService.addOrUpdateTotUserPts(examPoints);
		examCompletedRecord.setExamPoint(examPoints);
		return(exmCompRecRepo.save(examCompletedRecord));		 
	}	
	
	public double getExamPoints(int totalQuestions) {
		double examPoints = 0;
		if(totalQuestions <= 10) {
			examPoints = 1;
		}else if(totalQuestions > 10 && totalQuestions <= 20) {
			examPoints = 2;
		}else if(totalQuestions > 20 && totalQuestions <=30) {
			examPoints = 3;
		}else if(totalQuestions > 30 && totalQuestions <=40) {
			examPoints = 4;
		}else if(totalQuestions > 40 && totalQuestions <=50) {
			examPoints = 5;
		}else if(totalQuestions > 50 && totalQuestions <=60) {
			examPoints = 6;
		}else if(totalQuestions > 60 && totalQuestions <=70) {
			examPoints = 7;
		}else if(totalQuestions > 70 && totalQuestions <=80) {
			examPoints = 8;
		}else if(totalQuestions > 80 && totalQuestions <=90) {
			examPoints = 9;
		}else if(totalQuestions > 90 && totalQuestions <=100) {
			examPoints = 10;
		}else if(totalQuestions > 100 && totalQuestions <=110) {
			examPoints = 11;
		}else if(totalQuestions > 110 && totalQuestions <=120) {
			examPoints = 12;
		}				
		
		return examPoints;
		
	}
	
	public double fixExamPoints(double examPoints, Date completedDate) {
		Date currDate = ServiceUtils.getCurrentDateTime();		
		int daysdiff = 0;
	    long diff = currDate.getTime() - completedDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
	    daysdiff = (int) diffDays;	    
	    if(daysdiff < 30) {
	    	examPoints = examPoints/2;
	    }
		return examPoints;
	}
	
	public List<CorrectOption> getCorrectOptionsFromQIds(Set<Integer> set) {
		return correctOptionRepository.findByIdIn(set);
	}
	
	public List<ExamRecord> findRecordbyUserId(String userId){
		return exmRecRepo.findByUserId(userId);		
	}		
	
	public int addDueExam(int coursePackageId, String videoId, String microTopicId, String userId) {
		ExamRecord exDueRecord = new ExamRecord();
		exDueRecord.setCoursePackageId(coursePackageId);
		exDueRecord.setVideoId(videoId);
		exDueRecord.setMicroTopicId(microTopicId);
		java.util.Date date = new java.util.Date();
		long t = date.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
		exDueRecord.setDueDate(sqlTimestamp);
		exDueRecord.setUserId(userId);
		exDueRecord.setStatus(1);
		return exmRecRepo.save(exDueRecord).getExamRecordId();
	}
	
	public void updateDueExamStatus(int status, int examRecordId, String userId) {
		exmRecRepo.updateDueExamStatus(status, examRecordId, userId);
	}

	public List<FindExamRecordResponse> _getAllExamRecordsbyUserId(String userId){
		 List<FindExamRecordResponse> examRecords = new ArrayList<>();  
		 exmRecRepo.findLearnerExamRecords(userId).forEach(examRecords::add);  
		    return examRecords;  
	}
	
	public List<ExamCompletedRecord> _findExamCompRecordByExamRecId(int examRecordId) {
		List<ExamCompletedRecord> exCompRec = exmCompRecRepo.findByExamRecordId(examRecordId);
		return exCompRec;
	}

	public List<ExamCompRecordResponse> _getGrpCompExRecbyUserId(String userId){
		List<ExamCompRecordResponse> exGrpCompRecResp = exmCompRecRepo.findGrpExCompRecByLrId(userId);
		return exGrpCompRecResp;
	}
	
	public List<ExamCompDetailResponse> _getAllCompExRecByExRecId(int exRecId){
		String userId = UserInfo.getUserId(hsRequest).toUpperCase();
		List<ExamCompDetailResponse> exCompRecResp = exmCompRecRepo.findAllExCompRecByLrId(exRecId, userId);
		return exCompRecResp;
	}

	public List<String> _getWatchedMicroTopicIds(int coursePackageId, String userId){
		List<String> microTopicList = tsvRepo.findWatchedMtpsByCpkgIdAndUserId(coursePackageId, userId);
		return microTopicList;
	}

	public PointsResponse getExamPointsForUser(String userId) {
		Optional<List<ExamCompletedRecord>> examCompRecObj = exmCompRecRepo.findByUserId(userId);
		if(examCompRecObj.isPresent()) {
			PointsResponse examPoints = exmCompRecRepo.getTotalExamPoints(userId);
			return examPoints;		
		}
		return null;
		
	}
	
	public PointsResponse getCumExamPointsForUser(String userId) {
		Optional<List<CumulativeExamRecord>> cumExamRecObj = cumExamRepo.findByUserId(userId);
		if(cumExamRecObj.isPresent()) {
			PointsResponse cumExamPoints = cumExamRepo.getTotalCumExamPoints(userId);
			return cumExamPoints;	
		}
		return null;			
	}

	public int getExamRecordIdForExistingExam(int coursePackageId, String videoId, String microTopicId, String userId) {
		return exmRecRepo.findExistingExamRecordId(coursePackageId, videoId, microTopicId, userId);
	}
	
}
