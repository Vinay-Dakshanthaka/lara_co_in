package com.lara.c2c.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.dto.PointsResponse;
import com.lara.c2c.dto.video.TimeSpentOnVideoResponse;
import com.lara.c2c.dto.video.VideoPlayListResponse;
import com.lara.c2c.dto.video.WatchedVideoResponse;
import com.lara.c2c.factory.S3Factory;
import com.lara.c2c.model.Course;
import com.lara.c2c.model.TimeSpentOnVideo;
import com.lara.c2c.repository.CourseMaterialRepository;
import com.lara.c2c.service.CourseMaterialService;
import com.lara.c2c.service.CourseService;
import com.lara.c2c.service.VideoService;
import com.lara.c2c.utility.Constants;
import com.lara.c2c.utility.UserInfo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/video")
public class VideoController {
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private HttpServletRequest hsRequest;
	
	@Autowired
	private CourseMaterialService courseMaterialService;
	
	@Autowired
	private S3Factory factory;
	
	@Autowired
	private CourseService courseService;
	
	@PostMapping("/videoRec")
	public ResponseEntity<WatchedVideoResponse> addOrUpdateTimeSpentRecord(@RequestBody TimeSpentOnVideo timeSpentVideoRequest){
		String userId = UserInfo.getUserId(hsRequest);
		WatchedVideoResponse response = videoService.addOrUpdateTimeSpentRecord(timeSpentVideoRequest, userId);
		return ResponseEntity.ok().body(response);		
	}
	
	
	/*@GetMapping("/findWatchedVideo/{userId}")
	public ResponseEntity<FinishedMicroTopicIdsResponse> findWatchedMicroTopicIds(@RequestParam("learnerId") String userId){
		FinishedMicroTopicIdsResponse finishedMTopicsResponse = new FinishedMicroTopicIdsResponse();
		finishedMTopicsResponse.setMicroTopicIds(videoService.findCompleteMTopicIds(userId));
		return ResponseEntity.ok().body(finishedMTopicsResponse);
	}*/
	
	@GetMapping("/findWatchedVideo")
	public ResponseEntity<TimeSpentOnVideoResponse> findAllWatchedVideos(){
		String userId = UserInfo.getUserId(hsRequest);
		TimeSpentOnVideoResponse timeSpentOnVideoResponse = new TimeSpentOnVideoResponse();
		timeSpentOnVideoResponse.setTimeSpentOnVideoData(videoService.findRecordsByUserId(userId));
		return ResponseEntity.ok().body(timeSpentOnVideoResponse);
	}
	
	
	@PostMapping("/uploadVideoUrl") 
    public ResponseEntity<Void> videoUrlUpload(@RequestParam("file") MultipartFile file) {
		
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.TEMP_FILE_UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            videoService.insertVideoRecords(path.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }
	
	@GetMapping("/findAllVideosByCourseId/{coursePackageId}/{courseId}")
	public ResponseEntity<List<VideoPlayListResponse>> getAllVideosByCourseId(@PathVariable int coursePackageId, @PathVariable int courseId){
		String userId = UserInfo.getUserId(hsRequest);
		List<VideoPlayListResponse> videoPlayListResp = videoService._getAllVideosByCourseId(userId, coursePackageId, courseId);
		System.out.println("videos:" + videoPlayListResp.size());
		return ResponseEntity.ok().body(videoPlayListResp);
	}
	

	@GetMapping("/findAllMaterialsByCourseId/{courseId}")
	public ResponseEntity<Iterable<String>> getAllMaterialsByCourseId(@PathVariable int courseId){
		String userId = UserInfo.getUserId(hsRequest);
		Iterable<String> materials = courseMaterialService.readAll(courseId);
		return ResponseEntity.ok().body(materials);
	}



	
	@RequestMapping("downloadMaterialFile/{courseId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer courseId) throws Exception{
		Course course = courseService._getCourseByCourseId(courseId);
		return factory.getFile(course.getCourseMaterial());
	}
	
	
	
	@GetMapping("/findVideoPoints")
	public ResponseEntity<PointsResponse> getTotalVideoPoints(){		
		String userId = UserInfo.getUserId(hsRequest);
		PointsResponse response = videoService.getVideoPointsForUser(userId);
		return ResponseEntity.ok().body(response);
	}
}
