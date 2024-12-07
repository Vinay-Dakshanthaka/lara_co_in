package com.lara.c2c.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.CoursePackToMicroTopicResp;
import com.lara.c2c.dto.exam.CumExamResultResponse;
import com.lara.c2c.dto.exam.FindExQuesDetailResp;
import com.lara.c2c.dto.exam.QuesIdMtIdResponse;
import com.lara.c2c.dto.exam.ResultResponse;
import com.lara.c2c.model.CorrectOption;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.Option;
import com.lara.c2c.model.Question;
import com.lara.c2c.repository.CumulativeExamRepository;
import com.lara.c2c.repository.ExamCompRecordRepository;
import com.lara.c2c.repository.QuestionRepository;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private ExamCompRecordRepository exmCompRecRepo;
	
	@Autowired
	HttpServletRequest hsRequest;
	
	@Autowired
	private CumulativeExamRepository cumExamRecordRepo;
	
	@Autowired
	private CoursePackageService coursePackService;
	
	public void saveQuestionAnswers(Question question) {
		questionRepository.save(question);
	}
	
	public List<Question> getAllQuestions(){  
	    List<Question> questionRecords = new ArrayList<>();  
	    questionRepository.findRandomQuestions().forEach(questionRecords::add);  
	    return questionRecords;  
	}
	
	public void insertQuestionRecords(String filePath) throws IOException {
	      
	 	FileInputStream excelFile = new FileInputStream(new File(filePath));
	    @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    for(int i=1;i<worksheet.getPhysicalNumberOfRows(); i++) {	        
	    	XSSFRow row = worksheet.getRow(i);
	    	
	    	int totalCells = row.getPhysicalNumberOfCells();
	    	//int optionAnsCells = totalCells - 5;
	    	int optionsCellIndex = 5;
	    	Question question = new Question();
	    	
	    	
	    	String videoId = String.valueOf((int) row.getCell(0).getNumericCellValue()).toString();
	    	question.setVideoId(videoId);
	    	String microTopicId = String.valueOf((int) row.getCell(1).getNumericCellValue()).toString();
	    	question.setMicroTopicId(microTopicId);
	    	question.setLevelId((int) row.getCell(2).getNumericCellValue());
			question.setMarksAllocated((int) row.getCell(3).getNumericCellValue());
			question.setQuestionDesc(row.getCell(4).getStringCellValue());
			
			List<Option> options = new ArrayList<Option>();
			for(int j=optionsCellIndex; j<totalCells-1; j++) {				
				Option option = new Option();
				String arr [] = (row.getCell(j).getStringCellValue()).split("::");
				if(arr.length == 2) {
					option.setOptionAbbr(arr[0].toCharArray()[0]);
					option.setOptionDesc(arr[1]);				
					options.add(option);
				}				
			}
			question.setOptions(options);
			List<CorrectOption> correctOptions = new ArrayList<CorrectOption>();
			CorrectOption cOption = new CorrectOption();
			StringBuilder sb = new StringBuilder();
			String arr [] = (row.getCell(totalCells-1).getStringCellValue()).split(",");
			for(int k=0; k< arr.length; k++) {
				sb.append(arr[k].toCharArray()[0]);
				cOption.setAnswerKey(sb.toString());				
			}
			correctOptions.add(cOption);
			question.setCorrectOptions(correctOptions);
			saveQuestionAnswers(question);		
	    }
	}

	public List<Question> getQuestionForExam(String videoId, String microTopicId) {
		//todo remove correctOPtions list from data
		return questionRepository.findByVideoIdAndMicroTopicId(videoId, microTopicId);
	}
	
	public List<Question> getCumQuestionForExam(List<Integer> questionIds){
		
		return questionRepository.findCumQuesByQIds(questionIds);
	}
	
	public List<Integer> getRandomQuestionIdsList(List<String> microTopicIds, int totalQuestions) throws Exception{
		List<QuesIdMtIdResponse> quesIdsList = questionRepository.findQuesIdsListByMtIds(microTopicIds);
		if(quesIdsList.size() < totalQuestions) {
			throw new Exception("Insufficient Questions. Please try later.");
		}
		List<Integer> quesIds = new ArrayList();
		for (QuesIdMtIdResponse questionObj : quesIdsList){
			quesIds.add(questionObj.getQuestionId());			
		}
		System.out.println(quesIds);
		List<Integer> randomQuesIds = getRandomElement(quesIds, totalQuestions);
		return randomQuesIds;
	}		
	
	/*public List<Integer> getRandomElement(List<Integer> list, int totalItems) { 
		Random rand = new Random(); 
		List<Integer> newList = new ArrayList<>(); 
		for (int i = 0; i < totalItems; i++) { 
			int randomIndex = rand.nextInt(list.size()); 
			newList.add(list.get(randomIndex)); 
		} 
		return newList; 
	}*/ 
	
	public List<Integer> getRandomElement(List<Integer> list, int totalItems) { 
		Random rand = new Random(); 
		List<Integer> newList = new ArrayList<>(); 
		for (int i = 0; i < totalItems; i++) { 
		int randomIndex = rand.nextInt(list.size()); 
		newList.add(list.get(randomIndex)); 
		list.remove(randomIndex); 
		} 
		return newList; 
	} 
	
	public FindExQuesDetailResp _getQuesAnsByQIds(int cexRecId){	
		FindExQuesDetailResp exQResponse = new FindExQuesDetailResp();
		String userId = UserInfo.getUserId(hsRequest);
		ResultResponse resultObj = exmCompRecRepo.findQuesAnsData(cexRecId, userId);
		exQResponse.setExamResultData(resultObj);
		String qaData [] = resultObj.getQuestionAnsData().split(",");
		int qIds [] = new int[qaData.length];
		for(int i=0; i<qIds.length; i++) {
			qIds[i] = Integer.parseInt(qaData[i].split("-")[0]);
		}
		exQResponse.setQuestionsList(questionRepository.findByQIds(qIds));
		return exQResponse;
	}

	public FindExQuesDetailResp getQuestionDetailsForExam(String videoId, String microTopicId){
		FindExQuesDetailResp response = new FindExQuesDetailResp();
		response.setQuestionsList(getQuestionForExam(videoId, microTopicId));
		List<CoursePackToMicroTopicResp> coursePackDetailList = coursePackService._getDetailsByMicroTopicId(microTopicId);
		response.setCoursePackDetails(coursePackDetailList.get(0));
		return response;
	}

	public FindExQuesDetailResp getCumQuestionDetailsForExam(String microTopicIdsStr, int totalQuestions){
		FindExQuesDetailResp response = new FindExQuesDetailResp();
		List<String> microTopicIds = Arrays.asList(microTopicIdsStr.split("\\s*,\\s*"));
		/*List<Integer> microTopicIdsInt = ServiceUtils.convertStringListToIntList( 
				microTopicIds, 
	            Integer::parseInt); */
		List<Integer> randomQuesIdsList = new ArrayList<Integer>();
		try {
			randomQuesIdsList = getRandomQuestionIdsList(microTopicIds, totalQuestions);
			response.setQuestionsList(getCumQuestionForExam(randomQuesIdsList));
		}catch(Exception ex) {
			response.setExceptionReport(new ExceptionReport(ex));
		}		
		String microTopicId = microTopicIds.get(0);
		List<CoursePackToMicroTopicResp> coursePackDetailList = coursePackService._getDetailsByMicroTopicId(microTopicId);
		response.setCoursePackDetails(coursePackDetailList.get(0));
		return response;
	}
	
	

	public FindExQuesDetailResp _getCumQuesAnsByQIds(int cumExamRecordId, String userId){
		FindExQuesDetailResp exQResponse = new FindExQuesDetailResp();
		CumExamResultResponse resultObj = cumExamRecordRepo.findCumQuesAnsData(cumExamRecordId, userId);
		exQResponse.setCumExamResultData(resultObj);
		String qaData [] = resultObj.getQuestionAnsData().split(",");
		int qIds [] = new int[qaData.length];
		for(int i=0; i<qIds.length; i++) {
			qIds[i] = Integer.parseInt(qaData[i].split("-")[0]);
		}
		exQResponse.setQuestionsList(questionRepository.findByQIds(qIds));
		return exQResponse;
	}
}
