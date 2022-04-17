package com.harsha.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harsha.spring.models.Answer;
import com.harsha.spring.models.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	Answer findByQuestion(Question question);
}
