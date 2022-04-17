package com.harsha.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.ExamStatus;
import com.harsha.spring.models.RegisteredCandidates;

public interface ExamStatusRepository extends JpaRepository<ExamStatus, Integer>{
	
	Optional<ExamStatus> findByRegisteredCandidates(RegisteredCandidates registeredCandidates);

}
