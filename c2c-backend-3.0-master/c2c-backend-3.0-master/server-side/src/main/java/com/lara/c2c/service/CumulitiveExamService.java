package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.exam.CumExamPointDto;
import com.lara.c2c.dto.exam.CumExamRecordResponse;
import com.lara.c2c.dto.exam.FindAllCumRecResponse;
import com.lara.c2c.dto.topic.MicroTopicDto;
import com.lara.c2c.model.CorrectOption;
import com.lara.c2c.model.CumulativeExamRecord;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.repository.CumulativeExamRepository;
import com.lara.c2c.repository.MicroTopicRepository;
import com.lara.c2c.utility.ServiceUtils;


@Service
public class CumulitiveExamService{
	
	@Autowired
	private CumulativeExamRepository cumExamRepo;
	
	@Autowired
	private MicroTopicRepository microTopicRepo;
	
	@Autowired
	private ExamRecordService examRecordService;
	
	@Autowired
	private LearnerTotalPointService lrTotalPointService;
	
	public CumExamRecordResponse _addCumulativeExRecord(CumulativeExamRecord request, String userId){
		CumExamRecordResponse response = new CumExamRecordResponse();
		request.setUserId(userId);
		
		try {
			//check record exist against userId with status 0;
			CumulativeExamRecord cumExamRecord = cumExamRepo.findByUserIdAndStatus(userId, 0);
			if(cumExamRecord != null) {
				request.setCumExamRecordId(cumExamRecord.getCumExamRecordId());
				cumExamRepo.save(request);
				response.setCumExamRecordId(cumExamRecord.getCumExamRecordId());
			}else {
				response.setCumExamRecordId(cumExamRepo.save(request).getCumExamRecordId());
			}			
		}catch(Exception ex) {
			response.setExceptionReport(new ExceptionReport(ex));
		}
		return response;
	}

	public List<CumulativeExamRecord> _getAllCumExRecByCumExRecId(int cumExRecId, String userId){		
		List<CumulativeExamRecord> exCompRecResp = cumExamRepo.findByCumExamRecordIdAndUserId(cumExRecId, userId);
		return exCompRecResp;
	}
	
	public CumulativeExamRecord _updateCumExRecord(CumulativeExamRecord request, String userId) {
		
		//collect questionIds
		System.out.println("_updateCumExRecord1:" + request);
		String arr[] = request.getQuestionAnsData().split(","); 
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
		System.out.println("_updateCumExRecord2:" + userQuestionAnsMap);
		
		
		List<CorrectOption> actualQuesAnsList = examRecordService.getCorrectOptionsFromQIds(userQuestionAnsMap.keySet());
		List<String> actualAnsKeyList = new ArrayList<String>();
		int correctAnsCount = 0;
		for(int i=0; i<actualQuesAnsList.size(); i++) {
			actualAnsKeyList.add(actualQuesAnsList.get(i).getAnswerKey());
			if(userAnsKeys.get(i).equals(actualQuesAnsList.get(i).getAnswerKey())) {
				correctAnsCount = correctAnsCount + 1;
			}
		}		
		System.out.println("_updateCumExRecord3:" + userAnsKeys);
		
		request.setMarksObtained(correctAnsCount);
		request.setTotalMarks(request.getTotalMarks());
		int totalQuestions = (int) request.getTotalMarks();
		request.setTotalQuestions(totalQuestions);
		request.setStatus(1);
		request.setUserId(userId);
		
		String currAttendedMcts = request.getExamRelatedInfo();
		String previousAttendedMcts = "";
		double cumExamPoints = getCumExamPoints(totalQuestions);
		double cumExamScorePoints = (double) (correctAnsCount*totalQuestions)/50;
		CumExamPointDto cumExamPointRecord = cumExamRepo.getLastCumExamPointRecord(userId);
		if(cumExamPointRecord.getCumExamPoint() != null) {
			previousAttendedMcts = cumExamPointRecord.getExamRelatedInfo();
			cumExamPoints = fixCumExamPoints(cumExamPoints, cumExamPointRecord.getCompletedDate(), currAttendedMcts, previousAttendedMcts);
			
		}
		cumExamPoints = cumExamPoints + cumExamScorePoints;
		request.setCumExamPoint(cumExamPoints);
		lrTotalPointService.addOrUpdateTotUserPts(cumExamPoints);
		return(cumExamRepo.save(request));		 
	}
	
	public double getCumExamPoints(int totalQuestions) {
		double cumExamPoints = 0;
		if(totalQuestions <= 10) {
			cumExamPoints = 2;
		}else if(totalQuestions > 10 && totalQuestions <= 20) {
			cumExamPoints = 4;
		}else if(totalQuestions > 20 && totalQuestions <=30) {
			cumExamPoints = 6;
		}else if(totalQuestions > 30 && totalQuestions <=40) {
			cumExamPoints = 8;
		}else if(totalQuestions > 40 && totalQuestions <=50) {
			cumExamPoints = 10;
		}else if(totalQuestions > 50 && totalQuestions <=60) {
			cumExamPoints = 12;
		}else if(totalQuestions > 60 && totalQuestions <=70) {
			cumExamPoints = 14;
		}else if(totalQuestions > 70 && totalQuestions <=80) {
			cumExamPoints = 16;
		}else if(totalQuestions > 80 && totalQuestions <=90) {
			cumExamPoints = 18;
		}else if(totalQuestions > 90 && totalQuestions <=100) {
			cumExamPoints = 20;
		}else if(totalQuestions > 100 && totalQuestions <=110) {
			cumExamPoints = 22;
		}else if(totalQuestions > 110 && totalQuestions <=120) {
			cumExamPoints = 24;
		}				
		
		return cumExamPoints;
		
	}
	
	public double fixCumExamPoints(double cumExamPoints, Date completedDate, String currAttendedMcts, String previousAttendedMcts) {
		
		List<String> currAttendedMctsList = Arrays.asList(currAttendedMcts.split(","));
		List<String> newMctsList = new ArrayList<String>();
		for(int i=0; i<currAttendedMctsList.size(); i++) {
			newMctsList.add(currAttendedMctsList.get(i));
		}
		List<String> previousAttendedMctsList = Arrays.asList(previousAttendedMcts.split(","));
		List<String> prevMctsList = new ArrayList<String>();
		for(int i=0; i<previousAttendedMctsList.size(); i++) {
			prevMctsList.add(previousAttendedMctsList.get(i));
		}
		
		newMctsList.removeAll(prevMctsList);
		
		System.out.println(newMctsList);
		System.out.println(newMctsList.size());
		
		int newMctsListSize = newMctsList.size();
		double diffPercent = 0;
		if(newMctsListSize > 0) {
			diffPercent = (newMctsListSize*100)/currAttendedMctsList.size();
		}
		
		Date currDate = ServiceUtils.getCurrentDateTime();		
		int daysdiff = 0;
	    long diff = currDate.getTime() - completedDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
	    daysdiff = (int) diffDays;	    
	    if(daysdiff < 30) {
	    	cumExamPoints = cumExamPoints/2;
	    	if(diffPercent >= 10 && diffPercent < 20) {
	    		cumExamPoints = (cumExamPoints*60)/100;
	    	}else if(diffPercent >= 20 && diffPercent < 30) {
	    		cumExamPoints = (cumExamPoints*70)/100;
	    	}else if(diffPercent >= 30 && diffPercent < 40) {
	    		cumExamPoints = (cumExamPoints*80)/100;
	    	}else if(diffPercent >= 40 && diffPercent < 50) {
	    		cumExamPoints = (cumExamPoints*90)/100;
	    	}
	    }
		return cumExamPoints;
	}
	
	public List<FindAllCumRecResponse> _getAllCumExRecordsByUserId(String userId){
		List<FindAllCumRecResponse> response = cumExamRepo.getAllCumExRecordsByUserId(userId, 1);
		return response;
	}

	public List<MicroTopicDto> _getMiTListByCumExRecId(int cumExamRecordId, String userId){
		CumulativeExamRecord cumExRecord = cumExamRepo.findByCumExamRecordId(cumExamRecordId);
		List<String> microTopicIds = Arrays.asList(cumExRecord.getExamRelatedInfo().split("\\s*,\\s*"));
		/*List<Integer> microTopicIdsInt = ServiceUtils.convertStringListToIntList( 
				microTopicIds, 
	            Integer::parseInt);*/ 
		List<MicroTopicDto> microTopicList = microTopicRepo.findMicroTopicsByMitIds(microTopicIds);
		return microTopicList;
	}	
}
