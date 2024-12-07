package com.lara.c2c.service;


import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.MicroTopicDTO;
import com.lara.c2c.model.MicroTopic;

import com.lara.c2c.model.SubTopic;
import com.lara.c2c.model.Topic;
import com.lara.c2c.repository.MicroTopicRepository;
import com.lara.c2c.repository.QuestionRepository;
import com.lara.c2c.repository.TopicRepository;
import com.lara.c2c.repository.SubTopicRepository;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepo;
	
	@Autowired
	private SubTopicRepository subTopicRepository;
	
	@Autowired
	private MicroTopicRepository microTopicRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	public List<Topic> getAllTopics() {
		 List<Topic> skillTopicRecords = new ArrayList<>();  
		 topicRepo.findAll().forEach(skillTopicRecords::add);  
		    return skillTopicRecords;  
	}
	
	public void addSkillTopic(Topic skillTopic) {
		topicRepo.save(skillTopic);
	}
	
	public Optional<Topic> getSkillTopic(Integer id){
		return topicRepo.findById(id);
	}
	
	public void deleteSkillTopic(Integer id) {
		topicRepo.deleteById(id);
	}
	
	public void deleteAllSkillTopic() {
		topicRepo.deleteAll();
	}
	
	//subTopic
	public void addSubTopic(SubTopic subTopic) {
		subTopicRepository.save(subTopic);
	}
	
	public Optional<SubTopic> getSubTopic(Integer id){
		return subTopicRepository.findById(id);
	}
	
	public void deleteSubTopic(Integer id) {
		topicRepo.deleteById(id);
	}
	
	public void deleteAllSubTopic() {
		topicRepo.deleteAll();
	}
	
	//microTopic
	public void addMicroTopic(MicroTopic microTopic) {
		microTopicRepository.save(microTopic);
	}
	
	public Optional<MicroTopic> getMicroTopic(String id){
		return microTopicRepository.findById(id);
	}
	
	public void deleteMicroTopic(String id) {
		microTopicRepository.deleteById(id);
	}
	
	public void deleteAllMicroTopic() {
		microTopicRepository.deleteAll();
	}
	
	public void insertTopicSubMicroRecords(String filePath) throws IOException {
		Topic topic = new Topic();
		topic.setTopicName("Topic1");
		
		List<SubTopic> subTopics = new ArrayList<SubTopic>();			
		SubTopic subTopic = new SubTopic();
		subTopic.setSubTopicName("Test SubTopic1");
		
		List<MicroTopic> microTopics= new ArrayList<MicroTopic>();
		MicroTopic microTopic1 = new MicroTopic();
		microTopic1.setMicroTopicName("Test MicroTopic 1");
		
		microTopics.add(microTopic1);
		subTopic.setMicroTopic(microTopics);						
		topic.setSubTopic(subTopics);
		
		topicRepo.save(topic);
		

	}

	public List<Topic> _getAllTopicDetailsByCourseId(int courseId){		
		return topicRepo.findByCourseId(courseId);
	}
	
	public List<MicroTopicDTO> _getAllQuestionsCountMicroTopicWise(int courseId){
		List<String> microTopicIds = microTopicRepository.findMicroTopicIds(courseId);
		//System.out.println("Microtopic IDS:" + microTopicIds);
		List<MicroTopicDTO> list = questionRepository.findQuestionsCountInMicroTopic(microTopicIds);
//		for(MicroTopicDTO dto : list) {
//			System.out.println(dto.getMicroTopicId() + ":" + dto.getQuestionsCount());
//		}
		return list;
	}

}
