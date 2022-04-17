package com.harsha.spring.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {
	
	List<Question> findByExamId(ExamDetails examId);
}
