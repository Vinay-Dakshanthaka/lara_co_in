package com.lara.c2c.repository;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.dto.MicroTopicDTO;
import com.lara.c2c.dto.exam.QuesIdMtIdResponse;
import com.lara.c2c.model.Question;



public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	public List<Question> findByVideoIdAndMicroTopicId(String videoId, String microTopicId);
	
	@Query("select q from Question q where q.questionId in :qIds")
	public List<Question> findByQIds(int[] qIds);

	@Query("select q from Question q order by random()")
	public List<Question> findRandomQuestions();

	@Query("select q from Question q where q.questionId in :questionIds")
	public List<Question> findCumQuesByQIds(List<Integer> questionIds);
	
	@Query("select NEW com.lara.c2c.dto.exam.QuesIdMtIdResponse("
			+ "q.questionId,"
			+ "q.microTopicId) "			
			+ " from Question q "
			+ " where q.microTopicId in :microTopicIds")
	public List<QuesIdMtIdResponse> findQuesIdsListByMtIds(List<String> microTopicIds);
	
	
	@Query("SELECT new com.lara.c2c.dto.MicroTopicDTO(q.microTopicId, COUNT(q.microTopicId)) FROM Question q where q.microTopicId in :microTopicIds GROUP BY q.microTopicId")
	List<MicroTopicDTO> findQuestionsCountInMicroTopic(List<String> microTopicIds);
}
