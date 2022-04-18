package com.harsha.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.NoExamFoundException;
import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;
import com.harsha.spring.vo.ExamMailVo;

@Service
public class TriggerExamMailsService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	public void triggerMails(ExamMailVo mailContent, String eId)
			throws NoExamFoundException, OrganizationDoesntExistException, IllegalAccessException {
		// getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		Organization organization = null;
		ExamDetails examDetails = null;
		List<String> mailIds = new ArrayList<>();

		if (!findExam.isPresent()) {
			throw new NoExamFoundException("No exam found with given details");
		} else {
			examDetails = findExam.get();
			organization = examDetails.getOrganization();
		}
		
		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = organization.getUsername();

		if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		

		

		List<RegisteredCandidates> registeredCandidates = registeredCandidatesRepository
				.findByExamDetailsAndOrganization(examDetails, organization);
		if (registeredCandidates.size() > 0) {
			registeredCandidates.stream().forEach(candidate -> {
				String email = candidate.getUser().getUsername();
				mailIds.add(email);
			});

			mailIds.stream().forEach(email -> {
				String subject = mailContent.getSubject();
				String body = mailContent.getBody();

				try {
					emailSenderService.sendEmail(email, subject, body);
				} catch (Exception e) {
				}
			});

		}

	}
}
