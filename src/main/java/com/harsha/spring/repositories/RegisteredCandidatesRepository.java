package com.harsha.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;

@Repository
public interface RegisteredCandidatesRepository extends JpaRepository<RegisteredCandidates, String> {
	
	Optional<RegisteredCandidates> findByCandidateAndExamDetails(Candidate candidate, ExamDetails examDetails);
	
	List<RegisteredCandidates> findByExamDetailsAndOrganization(ExamDetails examDetails, Organization organization);
	
	List<RegisteredCandidates> findByCandidate(Candidate candidate);
	
	List<RegisteredCandidates> findByOrganization(Organization organization);

}
