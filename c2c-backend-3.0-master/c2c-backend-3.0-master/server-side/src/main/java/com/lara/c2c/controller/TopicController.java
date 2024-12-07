package com.lara.c2c.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.dto.MicroTopicDTO;
import com.lara.c2c.dto.topic.FindTopicsResponse;
import com.lara.c2c.model.MicroTopic;
import com.lara.c2c.model.SubTopic;
import com.lara.c2c.model.Topic;
import com.lara.c2c.service.TopicService;
import com.lara.c2c.utility.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/topic")
public class TopicController {
	
	@Autowired
	private TopicService topicService;
	
	@GetMapping("/findAll")
	public ResponseEntity<FindTopicsResponse> retrieveAllTopicData() {	
		
		FindTopicsResponse topicsResponse = new FindTopicsResponse();
		topicsResponse.setTopicsData(topicService.getAllTopics());
		
		if (Constants.isRequestFailed(topicsResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(topicsResponse);
		}
		return ResponseEntity.ok().body(topicsResponse);	
	}
	
	@PostMapping("/upload") 
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) {
		String UPLOADED_FOLDER = ".\\src\\main\\resources\\temp\\";
		System.out.println(UPLOADED_FOLDER);
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            topicService.insertTopicSubMicroRecords(path.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }
	
	@PostMapping("/addSkillTopic")
	public ResponseEntity<Void> insertSkillTopicData(@RequestBody Topic skillTopicRequest){
		topicService.addSkillTopic(skillTopicRequest);
		return ResponseEntity.ok().build();			
	}
	
	@GetMapping("/getSkillTopic/{id}")
	public Optional<Topic> retrieveSkillTopicData(@PathVariable Integer id) {			
		return topicService.getSkillTopic(id);
	}
	
	@DeleteMapping("deleteSkillTopic/{id}")
	public ResponseEntity<Void> deleteSKillTopicRecord(@PathVariable Integer id){
		topicService.deleteSkillTopic(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteAllSkillTopic")
	public ResponseEntity<Void> deleteAllSkillTopicRecord(){
		topicService.deleteAllSkillTopic();
		return ResponseEntity.ok().build();
	}
	
	//subTopic
	@PostMapping("/addSubTopic")
	public ResponseEntity<Void> insertSubTopicData(@RequestBody SubTopic subTopicRequest){
		topicService.addSubTopic(subTopicRequest);
		return ResponseEntity.ok().build();			
	}
	
	@DeleteMapping("deleteSubTopic/{id}")
	public ResponseEntity<Void> deleteSubTopicRecord(@PathVariable Integer id){
		topicService.deleteSubTopic(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteAllSubTopic")
	public ResponseEntity<Void> deleteAllSubTopicRecord(){
		topicService.deleteAllSubTopic();
		return ResponseEntity.ok().build();
	}
	
	//microTopic
	@PostMapping("/addMicroTopic")
	public ResponseEntity<Void> insertMicroTopicData(@RequestBody MicroTopic microTopicRequest){
		topicService.addMicroTopic(microTopicRequest);
		return ResponseEntity.ok().build();			
	}
	
	@DeleteMapping("deleteMicroTopic/{id}")
	public ResponseEntity<Void> deleteMicroTopicRecord(@PathVariable String id){
		topicService.deleteMicroTopic(id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteAllMicroTopic")
	public ResponseEntity<Void> deleteAllMicroTopicRecord(){
		topicService.deleteAllMicroTopic();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/findAllTopicsByCourseId/{courseId}")
	public ResponseEntity<FindTopicsResponse> getAllTopicDetailsByCourseId(@PathVariable int courseId) {	
		
		FindTopicsResponse topicsResponse = new FindTopicsResponse();
		topicsResponse.setTopicsData(topicService._getAllTopicDetailsByCourseId(courseId));
		
		if (Constants.isRequestFailed(topicsResponse)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(topicsResponse);
		}
		return ResponseEntity.ok().body(topicsResponse);	
	}

	@GetMapping("/findAllQuestionsCountByCourseId/{courseId}")
	public ResponseEntity<List<MicroTopicDTO>> getAllQuestionsCountMicroTopicWise(@PathVariable int courseId){
		return ResponseEntity.ok().body(topicService._getAllQuestionsCountMicroTopicWise(courseId));
	}
}










