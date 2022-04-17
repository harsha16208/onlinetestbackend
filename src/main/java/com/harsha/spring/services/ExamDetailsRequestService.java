package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.repositories.ExamDetailsRepository;

@Service
public class ExamDetailsRequestService {
	
	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	public Map<String, String> examDetails(String eId) {
		Map<String, String> details = new HashMap<>();
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		ExamDetails examDetails = null;
		if (findExam.isPresent()) {
			examDetails = findExam.get();
			String examId = examDetails.getExamId();
			String examName = examDetails.getExamName();
			LocalDateTime startDate = examDetails.getStartDate();
			LocalDateTime endDate = examDetails.getEndDate();
			LocalDateTime registrationEndDate = examDetails.getRegistrationEndDate();
			String orgId = examDetails.getOrganization().getId();
			String organizationName = examDetails.getOrganization().getOrganizationName();
			short duration = examDetails.getDuration();
			details.put("examId", examId);
			details.put("examName", examName);
			details.put("startDate", startDate.toString());
			details.put("endDate", endDate.toString());
			details.put("registrationEndDate", registrationEndDate.toString());
			details.put("orgId", orgId);
			details.put("organizationName", organizationName);
			details.put("duration", String.valueOf(duration));
			return details;
		}
		return null;
	}
}
