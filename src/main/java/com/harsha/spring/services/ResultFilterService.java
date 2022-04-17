package com.harsha.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.InsufficientDetailsException;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.models.Result;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.ResultRepository;

@Service
public class ResultFilterService {

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	public boolean filterCandidates(int cutoff, String eId) throws InsufficientDetailsException, IllegalAccessException {
		// getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		ExamDetails examDetails = null;
		Organization organization = null;
		List<Result> results = null;
		List<Result> shortListed = null;
		List<Result> notShortListed = null;

		if (findExam.isEmpty()) {
			throw new InsufficientDetailsException("please provide sufficient details for filtering candidates");
		}
		examDetails = findExam.get();
		organization = examDetails.getOrganization();

		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = organization.getUsername();

		if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		
		examDetails.setResultsFiltered(true);
		
		results  = resultRepository.findByExamDetails(examDetails);
		
		shortListed = results.stream().filter(result -> result.getResult() >= cutoff).collect(Collectors.toList());
		
		notShortListed = results.stream().filter(result -> result.getResult() < cutoff).collect(Collectors.toList());
		
		shortListed.stream().forEach(candidate ->{
			candidate.setQualified(true);
		});
		
		notShortListed.stream().forEach(candidate ->{
			candidate.setQualified(false);
		});
		
		examDetailsRepository.save(examDetails);
		return true;
	}

	public String triggerMails(String orgId, String eId, long cutoff)
			throws InsufficientDetailsException, IllegalAccessException {
		// getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		Organization organization = null;
		ExamDetails examDetails = null;
		List<Result> results = null;
		List<Result> shortListed = null;
		List<String> emails = new ArrayList<>();

		if (findOrganization.isEmpty() || findExam.isEmpty()) {
			throw new InsufficientDetailsException("please provide sufficient details for filtering candidates");
		}
		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = findOrganization.get().getUsername();

		if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		organization = findOrganization.get();
		examDetails = findExam.get();

		results = resultRepository.findByExamDetailsAndOrganization(examDetails, organization);

		shortListed = results.stream().filter(result -> result.getResult() >= cutoff).collect(Collectors.toList());

		shortListed.stream().forEach(candidate -> {
			RegisteredCandidates regDetails = candidate.getRegisteredCandidates();
			User user = regDetails.getUser();
			String email = user.getUsername();
			emails.add(email);
		});

		final ExamDetails EXAM_DETAILS = examDetails;
		emails.stream().forEach(email -> {
			String examName = EXAM_DETAILS.getExamName();
			Optional<Organization> findOrg = organizationRepository.findById(EXAM_DETAILS.getOrganization().getId());
			Organization org = null;
			if (findOrg.isPresent()) {
				org = findOrg.get();
			}
			String subject = "congratulations";
			String body = String.format("Congratulations you are shortlisted in %s for %s ", org.getOrganizationName(),
					examName);
			try {
				emailSenderService.sendEmail(email, subject, body);
			} catch (Exception e) {
			}
		});

		return "success";
	}

}
