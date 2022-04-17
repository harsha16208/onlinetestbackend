package com.harsha.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.models.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

	
	Optional<Result> findByRegisteredCandidates(RegisteredCandidates registeredCandidates);
	
	List<Result> findByExamDetailsAndOrganization(ExamDetails examDetails, Organization organization);
	
	List<Result> findByExamDetails(ExamDetails examDetails);
	
	List<Result> findByCandidate(Candidate candidate);
	
}
