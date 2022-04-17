package com.harsha.spring.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.Result;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.ResultRepository;

@Service
public class CandidateResultsService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private ResultRepository resultRepository;
	
	public List<Map<String, Object>> getCandidateResults(String cId) throws CandidateNotFoundException {
		List<Map<String, Object>> candidateResults = new ArrayList<>();
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		Candidate candidate = null;
		List<Result> results = null;
		if (findCandidate.isPresent()) {
			candidate = findCandidate.get();
			results = resultRepository.findByCandidate(candidate);
			results.stream().forEach(result -> {
				Map<String, Object> resultDetails = new HashMap<>();
				String examId = result.getExamDetails().getExamId();
				String examName = result.getExamDetails().getExamName();
				String organizationName = result.getOrganization().getOrganizationName();
				long score = result.getResult();
				boolean qualified = result.isQualified();
				boolean resultFiltered = result.getExamDetails().isResultsFiltered();

				resultDetails.put("examId", examId);
				resultDetails.put("examName", examName);
				resultDetails.put("organizationName", organizationName);
				resultDetails.put("score", score);
				resultDetails.put("qualified", qualified);
				resultDetails.put("resultFiltered", resultFiltered);

				candidateResults.add(resultDetails);
			});

			return candidateResults;
		}
		throw new CandidateNotFoundException("No candidate found with given Id : " + cId);
	}

}
