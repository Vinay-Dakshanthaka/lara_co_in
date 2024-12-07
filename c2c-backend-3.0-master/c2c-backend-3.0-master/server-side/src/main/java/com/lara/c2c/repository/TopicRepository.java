package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer>{

	List<Topic> findByCourseId(Integer courseId);

}
