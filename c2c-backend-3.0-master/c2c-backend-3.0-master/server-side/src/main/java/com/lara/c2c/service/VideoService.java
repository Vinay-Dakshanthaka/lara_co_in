package com.lara.c2c.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.video.VideoPlayListResponse;
import com.lara.c2c.dto.video.VideoPointDto;
import com.lara.c2c.dto.video.WatchedVideoResponse;
import com.lara.c2c.model.CoursePackage;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.model.TimeSpentOnVideo;
import com.lara.c2c.model.VideoPoint;
import com.lara.c2c.model.VideoTutorial;
import com.lara.c2c.repository.CoursePackageRepository;
import com.lara.c2c.repository.DemoVideoTutorialRepository;
import com.lara.c2c.repository.LearnerCourseRepository;
import com.lara.c2c.repository.MicroTopicRepository;
import com.lara.c2c.repository.TimeSpentOnVideoRepository;
import com.lara.c2c.repository.VideoPointRepository;
import com.lara.c2c.repository.VideoTutorialRepository;
import com.lara.c2c.utility.ServiceUtils;
import com.lara.c2c.utility.UserInfo;



@Service
public class VideoService {
	
	@Autowired
	private TimeSpentOnVideoRepository timeSpentVideoRepo;
	
	@Autowired
	private ExamRecordService examRecordService;
	
	@Autowired 
	VideoTutorialRepository videoTutorialRepository;
	
	@Autowired
	DemoVideoTutorialRepository videoDemoRepository;
	
	@Autowired
	private LearnerCourseRepository lrCoursePackRepo;
	
	@Autowired
	private VideoPointRepository videoPointRepo;
	
	@Autowired 
	private MicroTopicRepository microTopicRepo;
	
	@Autowired
	private LearnerTotalPointService lrTotalPointService;
	
	public WatchedVideoResponse addOrUpdateTimeSpentRecord(TimeSpentOnVideo timeSpentVideoRequest, String userId){  		
		WatchedVideoResponse watchedVidResp = new WatchedVideoResponse();
		TimeSpentOnVideo timeSpentVideoRecord = findByVideoIdAndUserIdAndCoursePackageId(timeSpentVideoRequest.getVideoId(), userId, timeSpentVideoRequest.getCoursePackageId());
		if(timeSpentVideoRecord == null) {
			timeSpentVideoRequest.setFrequency(1);		
			timeSpentVideoRepo.save(timeSpentVideoRequest);
			int examRecordId = examRecordService.addDueExam(timeSpentVideoRequest.getCoursePackageId(), timeSpentVideoRequest.getVideoId(), timeSpentVideoRequest.getMicroTopicId(), timeSpentVideoRequest.getUserId());
			watchedVidResp.setWatchedEarlier(false);
			watchedVidResp.setExamRecordId(examRecordId);
		
		}else {
			//new code addded to get exam record id for exixting watched video
			int examRecordId = examRecordService.getExamRecordIdForExistingExam(timeSpentVideoRequest.getCoursePackageId(), timeSpentVideoRequest.getVideoId(), timeSpentVideoRequest.getMicroTopicId(), timeSpentVideoRequest.getUserId());
			
			timeSpentVideoRecord.setFrequency(timeSpentVideoRecord.getFrequency()+1);
			timeSpentVideoRepo.updateTimeSpentVideoRecord(timeSpentVideoRecord.getVideoId(), 
					timeSpentVideoRecord.getUserId(),timeSpentVideoRecord.getFrequency());
			watchedVidResp.setWatchedEarlier(true);
			watchedVidResp.setExamRecordId(examRecordId);
		}
		VideoPoint videoPoint = new VideoPoint();
		videoPoint.setUserId(userId);
		String microTopicId = timeSpentVideoRequest.getMicroTopicId();
		videoPoint.setMicroTopicId(microTopicId);
		double videoPoints = getVideoPointsByDuration(getVideolengthByMctId(microTopicId));				
		VideoPointDto videoPointRecord = videoPointRepo.findVideoPointRecord(microTopicId, userId);
		if(videoPointRecord.getVideoPoint() != null) {
			videoPoints = fixVideoPoints(videoPoints, videoPointRecord.getVideoWatchedDate());
		}
		videoPoint.setVideoPoint(videoPoints);
		//fixVideoPointsOnRepeat()
		videoPointRepo.save(videoPoint);
		lrTotalPointService.addOrUpdateTotUserPts(videoPoints);
		return watchedVidResp;
		  
    } 
	
	public double getVideoPointsByDuration(int videoDuration) {
		double videoPoints = 0;
		if(videoDuration <= 10) {
			videoPoints = 1;
		}else if(videoDuration > 10 && videoDuration <= 20) {
			videoPoints = 2;
		}else if(videoDuration > 20 && videoDuration <=30) {
			videoPoints = 3;
		}else if(videoDuration > 30 && videoDuration <=40) {
			videoPoints = 4;
		}else if(videoDuration > 40 && videoDuration <=50) {
			videoPoints = 5;
		}else if(videoDuration > 50 && videoDuration <=60) {
			videoPoints = 6;
		}else if(videoDuration > 60 && videoDuration <=70) {
			videoPoints = 7;
		}else if(videoDuration > 70 && videoDuration <=80) {
			videoPoints = 8;
		}else if(videoDuration > 80 && videoDuration <=90) {
			videoPoints = 9;
		}else if(videoDuration > 90 && videoDuration <=100) {
			videoPoints = 10;
		}else if(videoDuration > 100 && videoDuration <=110) {
			videoPoints = 11;
		}else if(videoDuration > 110 && videoDuration <=120) {
			videoPoints = 12;
		}				
		
		return videoPoints;
		
	}
	
	public double fixVideoPoints(double videoPoints, Date videoWatchedDate) {
		Date currDate = ServiceUtils.getCurrentDateTime();
		
		int daysdiff = 0;
	    long diff = currDate.getTime() - videoWatchedDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
	    daysdiff = (int) diffDays;
	    
	    if(daysdiff < 30) {
	    	videoPoints = videoPoints/2;
	    }
		return videoPoints;
	}
	
	public int getVideolengthByMctId(String microTopicId) {
		String duration =  microTopicRepo.findVideoLengthByMctId(microTopicId);		
		return Integer.parseInt(duration.split(" ")[0]);		
	}
	
	public TimeSpentOnVideo findByVideoIdAndUserIdAndCoursePackageId(String videoId, String userId, Integer coursePackageId){  
        return timeSpentVideoRepo.findByVideoIdAndUserIdAndCoursePackageId(videoId, userId, coursePackageId);  
    }
	
	
	public List<TimeSpentOnVideo> findRecordsByUserId(String userId){  
        return timeSpentVideoRepo.findByUserId(userId);  
    }
	
	/*public List<Integer> findCompleteMTopicIds(String userId){
		return timeSpentVideoRepo.findMicroTopicIdsbyUserId(userId);
	}*/
	

	public void insertVideoRecords(String filePath) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(filePath));
	    @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	      
	    	XSSFRow row = worksheet.getRow(i);	    		    
	    	VideoTutorial videoTutorial = new VideoTutorial();
	    		    
	    	videoTutorial.setMicroTopicId(row.getCell(0).getStringCellValue());
	    	videoTutorial.setSrc(row.getCell(1).getStringCellValue());
	    	videoTutorial.setTitle(row.getCell(2).getStringCellValue());
	    	videoTutorial.setType(row.getCell(3).getStringCellValue());
						
			saveVideoTutorials(videoTutorial);		
	    }
	}
	
	public void saveVideoTutorials(VideoTutorial videoTutorial) {
		videoTutorialRepository.save(videoTutorial);
	}

	public List<VideoPlayListResponse> _getAllVideosByCourseId(String userId, int coursePackageId, int courseId){
		boolean isPackageSubscribed = checkPackageSubscription(userId, coursePackageId);
		List<VideoPlayListResponse> videoPlayListRecords = new ArrayList();
		if(isPackageSubscribed) {
			videoPlayListRecords = videoTutorialRepository.getAllVideosByCourseId(courseId);
		}else {
			videoPlayListRecords = videoDemoRepository.getAllVideosByCourseId(courseId);
		}
		//System.out.println("_getAllVideosByCourseId userId :" + userId + ", coursePackageId :" + coursePackageId + ", courseId :" + courseId + ", videoPlayListRecords.size:" + videoPlayListRecords.size());
		//System.out.println(videoPlayListRecords);
		return videoPlayListRecords;
	}
	
	public boolean checkPackageSubscription(String userId, int coursePackageId) {
		
		LearnerCourse learnerCoursePack = lrCoursePackRepo.findByCoursePackageIdAndUserId(coursePackageId, userId);
		if(learnerCoursePack != null) {
			return true;
		}else {
			return false;
		}
		
	}


	public PointsResponse getVideoPointsForUser(String userId) {
		Optional<List<VideoPoint>> videoPointObj = videoPointRepo.findByUserId(userId);
		if(videoPointObj.isPresent()) {
			PointsResponse videoPoints = videoPointRepo.getTotalVideoPoints(userId);
			return videoPoints;
		}		
		return null;
	}
	
	
}
