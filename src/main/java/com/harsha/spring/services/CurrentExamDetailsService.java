package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.ExamLink;
import com.harsha.spring.models.ExamStatus;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.ExamLinkRepository;
import com.harsha.spring.repositories.ExamStatusRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;

@Service
public class CurrentExamDetailsService {

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private ExamLinkRepository examLinkRepository;
	
	@Autowired
	private ExamStatusRepository examStatusRepository;
	
	public List<Map<String, Object>> getCurrentExamDetails(String cId) throws CandidateNotFoundException {
		List<Map<String, Object>> currentExamDetails = new ArrayList<>();
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		Candidate candidate = null;
		List<RegisteredCandidates> registredExams = null;
		List<ExamDetails> examDetailsList = new ArrayList<>();
		List<ExamDetails> todaysExams = null;
		LocalDateTime currentTime = LocalDateTime.now();
		int currentDay = currentTime.getDayOfYear();
		int currentYear = currentTime.getYear();
		if (findCandidate.isPresent()) {
			candidate = findCandidate.get();
			registredExams = registeredCandidatesRepository.findByCandidate(candidate);
			
			

			// making a list of all the exams registered by candiadte
			registredExams.stream().forEach(exam -> {
				examDetailsList.add(exam.getExamDetails());
			});

			// filtering toadys exams from all the registered exams of candidate
			todaysExams = examDetailsList.stream().filter(exam -> {
				LocalDateTime startDate = exam.getStartDate();
				int examDay = startDate.getDayOfYear();
				int examYear = startDate.getYear();
				LocalDateTime endDate = exam.getEndDate();
				int examEndYear = endDate.getYear();
				int examEndDay = endDate.getDayOfYear();
				return (examDay == currentDay && examYear == currentYear) || (examDay <= currentDay && examEndDay >= currentDay && examEndYear == currentYear);
			}).collect(Collectors.toList());

			
			//preparing data which needs to be sent
			final Candidate CANDIDATE = candidate;
			todaysExams.stream().forEach(exam -> {
				Map<String, Object> details = new HashMap<>();
				String examId = exam.getExamId();
				String examName = exam.getExamName();
				LocalDateTime startDate = exam.getStartDate();
				LocalDateTime endDate = exam.getEndDate();
				Optional<ExamLink> findExamLink = examLinkRepository.findByExamDetails(exam);
				ExamLink examLink = findExamLink.get();
				String testUrl = examLink.getTestUrl();
				String organizationName = exam.getOrganization().getOrganizationName();
				short duration = exam.getDuration();
				
				//finding a candidate has attempted the exam or not
				RegisteredCandidates regCandidate = registeredCandidatesRepository.findByCandidateAndExamDetails(CANDIDATE, exam).get();
				
				Optional<ExamStatus> status = examStatusRepository.findByRegisteredCandidates(regCandidate);
				boolean isStatusPresent = status.isPresent();
				
				details.put("examId", examId);
				details.put("examName", examName);
				details.put("endDate", endDate);
				details.put("startDate", startDate);
				details.put("testUrl", testUrl);
				details.put("organizationName", organizationName);
				details.put("attempted", isStatusPresent);
				details.put("duration", duration);
				currentExamDetails.add(details);
			});
			
			return currentExamDetails;
		}
		throw new CandidateNotFoundException("No candidate found with given id : "+cId);
	}
}
