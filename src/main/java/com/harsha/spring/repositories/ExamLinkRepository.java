package com.harsha.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.ExamLink;

public interface ExamLinkRepository extends JpaRepository<ExamLink, Integer>{

	Optional<ExamLink> findByExamDetails(ExamDetails examDetails);
}
