package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;

@Service
public class ExamsAppliedDetailsService {

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	public List<Map<String, Object>> getAppliedExamDetails(String cId) throws CandidateNotFoundException {
		List<Map<String, Object>> appliedExamDetails = new ArrayList<>();
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		Candidate candidate = null;
		List<ExamDetails> examDetails = new ArrayList<>();
		List<RegisteredCandidates> examsapplied = null;
		if (findCandidate.isPresent()) {
			candidate = findCandidate.get();

			examsapplied = registeredCandidatesRepository.findByCandidate(candidate);
			examsapplied.stream().forEach(exam -> {
				examDetails.add(exam.getExamDetails());
			});

			examDetails.forEach(exam -> {
				Map<String, Object> subDetails = new HashMap<>();
				String examId = exam.getExamId();
				LocalDateTime startDate = exam.getStartDate();
				LocalDateTime endDate = exam.getEndDate();
				String examName = exam.getExamName();
				String organizationName = exam.getOrganization().getOrganizationName();
				subDetails.put("examId", examId);
				subDetails.put("startDate", startDate);
				subDetails.put("endDate", endDate);
				subDetails.put("examName", examName);
				subDetails.put("organizationName", organizationName);
				appliedExamDetails.add(subDetails);
			});

			return appliedExamDetails;
		}
		throw new CandidateNotFoundException("No candidate found with given Id : " + cId);
	}
}
