package com.harsha.spring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.Topic;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.TopicRepository;

@Service
public class ExamDetailsService {
	
	@Autowired
	private ExamDetailsRepository examDetailsRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private TopicRepository topicRepository;

	public List<ExamDetails> getExamDetails(String orgId){
		
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		List<ExamDetails> examDetails = null;
		Organization organization = null;
		if (findOrganization.isPresent()) {
			organization = findOrganization.get();
			examDetails = examDetailsRepository.findByOrganization(organization);
			return examDetails;
		}
		return null;
	}

	public ExamDetails getExamDetails(String orgId, String eId) {

		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		List<ExamDetails> examDetails = null;
		Organization organization = null;
		if (findOrganization.isPresent()) {
			organization = findOrganization.get();
			examDetails = examDetailsRepository.findByOrganization(organization);
			List<ExamDetails> filteredExam = examDetails.stream().filter(exam -> exam.getExamId().equals(eId)).collect(Collectors.toList());
			if (filteredExam.size() > 0)
				return filteredExam.get(0);
		}
		return null;
	}
	
	public List<Topic> getTopicDetails(String eId){
		Optional<ExamDetails> findExam  = examDetailsRepository.findById(eId);
		ExamDetails examDetails = null;
		List<Topic> topics = null;
		if (findExam.isPresent()) {
			examDetails = findExam.get();
			topics = topicRepository.findByExamDetails(examDetails);
		}
		return topics;
	}
}
