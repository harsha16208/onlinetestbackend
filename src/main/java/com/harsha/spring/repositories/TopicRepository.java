package com.harsha.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

	Optional<Topic> findByTopicNameAndExamDetails(String topicName, ExamDetails examDetails);
	
	List<Topic> findByExamDetails(ExamDetails examDetails);
}
