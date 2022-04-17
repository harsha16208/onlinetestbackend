package com.harsha.spring.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Result;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.ResultRepository;

@Service
public class ResultsService {
	
	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	public List<Map<String, Object>> getResults(String eId) {
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		List<Map<String, Object>> examResults = new ArrayList<>();
		List<Result> allResults= null;
		ExamDetails examDetails = null;
		if (findExam.isPresent()) {
			Map<String, Object> resultData = new HashMap<>();
			examDetails = findExam.get();
			allResults = resultRepository.findByExamDetails(examDetails);
			allResults.stream().forEach(result ->{
				resultData.put("name", result.getCandidate().getName());
				resultData.put("score", result.getResult());
				resultData.put("regId", result.getRegisteredCandidates().getRegId());
				resultData.put("candidateId", result.getCandidate().getId());
				examResults.add(resultData);
			});
			return examResults;
		}
		return null;
	}
	
}
