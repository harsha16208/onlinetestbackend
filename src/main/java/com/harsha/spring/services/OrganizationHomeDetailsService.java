package com.harsha.spring.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;

@Service
public class OrganizationHomeDetailsService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	public Map<String, String> getHomeDetails(String orgId) throws OrganizationDoesntExistException {
		Map<String, String> homeDetails = new HashMap<>();
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		Organization organization = null;
		if (!findOrganization.isPresent()) {
			throw new OrganizationDoesntExistException("No organization Exists with given id :" + orgId);
		}
		organization = findOrganization.get();
		List<ExamDetails> examsCreated = examDetailsRepository.findByOrganization(organization);
		List<RegisteredCandidates> registeredCandidates = registeredCandidatesRepository
				.findByOrganization(organization);

		String numberOfExamsCreated = String.valueOf(examsCreated.size());
		long questionsPosted = examsCreated.stream().filter(exam -> {
			return exam.isQuestionsPosted();
		}).count();
		long questionsNotPosted = examsCreated.stream().filter(exam -> {
			return !exam.isQuestionsPosted();
		}).count();
		String numberOfregisteredCandidates = String.valueOf(registeredCandidates.size());

		homeDetails.put("examsCreated", numberOfExamsCreated);
		homeDetails.put("questionsPosted", String.valueOf(questionsPosted));
		homeDetails.put("questionsNotPosted", String.valueOf(questionsNotPosted));
		homeDetails.put("registeredCandidates", numberOfregisteredCandidates);

		return homeDetails;
	}
}
