package com.harsha.spring.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.exceptions.DuplicateAccountException;
import com.harsha.spring.exceptions.NoExamFoundException;
import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.exceptions.UserNotVerifiedException;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.models.Role;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;
import com.harsha.spring.repositories.RoleRepository;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.vo.CandidateVo;
import com.harsha.spring.vo.OrganizationVo;
import org.jboss.logging.Logger;

@Service
public class RegisterService {

	private static final Logger LOGGER = Logger.getLogger(RegisterService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserVerificationService userVerificationService;

	@Autowired
	private EmailSenderService emailSenderService;

	/*
	 * The method is for registering as an organization, this method throws
	 * exceptions if we are using an existing username to create an account or if
	 * the details are insufficient
	 */
	public String registerAsOrganization(OrganizationVo organization) throws Exception {
		String exceptionMessage = "";
		String username = organization.getUsername();
		Optional<User> user = userRepository.findById(username);
		if (user.isEmpty()) { // if there exists no user with given username create a new account else throw
								// duplicate account exception
			long mobile = Long.parseLong(organization.getMobile());
			Role role = roleRepository.getById("ORG");
			String password = bCryptPasswordEncoder.encode(organization.getPassword());
			User userDetails = new User(organization.getUsername(), password, role, LocalDate.now());
			Organization organizationDetails = new Organization(organization.getOrganizationName(),
					organization.getOrgLink(), userDetails, mobile);
			// verrifying user
			boolean userVerified = userVerificationService.isUserVerified(username);
			if (!userVerified) {
				throw new UserNotVerifiedException("please verify your account");
			}
			userRepository.save(userDetails); // saving user credentials into user table
			organizationRepository.save(organizationDetails); // saving organization details into organization table
			LOGGER.info("Organization created successfully");

			Organization createdOrganization = organizationRepository.findByUsername(userDetails);
			String orgId = createdOrganization.getId();
			// sending mail after account activation
			String subject = "Account activation";
			String body = "Thank you for registering Your account will be activated shortly in case of queries contact Admin";
			emailSenderService.sendEmail(username, subject, body);
			return orgId;
		}

		exceptionMessage = username + " already taken";
		throw new DuplicateAccountException(exceptionMessage);
	}

	/*
	 * The below method is for registering as a candidate, this method throws
	 * exceptions if we are using an existing username to create an account or if
	 * the details are insufficient
	 */
	public String registerAsCandidate(CandidateVo candidate) throws Exception {
		String username = candidate.getUsername();
		long mobile = Long.parseLong(candidate.getMobile());
		Optional<User> user = userRepository.findById(username);
		String exceptionMessage = "";

		if (user.isEmpty()) {
			Role role = roleRepository.getById("CANDIDATE");
			String password = bCryptPasswordEncoder.encode(candidate.getPassword());
			User userDetails = new User(candidate.getUsername(), password, role, LocalDate.now());
			Candidate candidateDetails = new Candidate(candidate.getName(), candidate.getDateOfBirth(), userDetails,
					mobile);
			boolean userVerified = userVerificationService.isUserVerified(username);
			if (!userVerified) {
				throw new UserNotVerifiedException("please verify your account");
			}
			userRepository.save(userDetails);
			candidateRepository.save(candidateDetails);
			LOGGER.info("Candidate created successfully");

			Candidate createdCandidate = candidateRepository.findByUsername(userDetails);
			String cid = createdCandidate.getId();

			// sending mail after account activation
			String subject = "Account activation";
			String body = "Your account activated successfully";
			emailSenderService.sendEmail(username, subject, body);

			return cid;
		}

		exceptionMessage = username + " already taken";
		throw new DuplicateAccountException(exceptionMessage);
	}

	/*
	 * The method is for registering candidate for an examination
	 */
	public String candidateRegisterForExam(String orgId, String eId, String cId) throws Exception {
		// getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		Optional<RegisteredCandidates> findByCandidateAndExamDetails = null;

		Organization organization = null;
		ExamDetails examDetails = null;
		Candidate candidate = null;
		User user = null;

		if (findCandidate.isEmpty()) {
			throw new CandidateNotFoundException("No canidate found with given id : " + cId);
		}
		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = findCandidate.get().getUsername();

		if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		if (findOrganization.isEmpty()) {
			throw new OrganizationDoesntExistException("organization doesn't exist with given id : " + orgId);
		}
		if (findExam.isEmpty()) {
			throw new NoExamFoundException("No exam found with given id : " + eId);
		}

		organization = findOrganization.get();
		examDetails = findExam.get();
		candidate = findCandidate.get();
		findByCandidateAndExamDetails = registeredCandidatesRepository.findByCandidateAndExamDetails(candidate,
				examDetails);
		user = candidate.getUsername();

		if (findByCandidateAndExamDetails.isPresent()) {
			return null;
		}

		LocalDateTime registrationEndDate = examDetails.getRegistrationEndDate();
		LocalDateTime currentDateAndTime = LocalDateTime.now();

		if (currentDateAndTime.isAfter(registrationEndDate)) {
			return "expired";
		}

		RegisteredCandidates registeredCandidateDetails = new RegisteredCandidates(candidate, examDetails, organization,
				user);
		registeredCandidatesRepository.save(registeredCandidateDetails);
		LOGGER.info("candidate " + cId + " registered successfully");

		User username = candidate.getUsername();
		String description = examDetails.getDescription();
		String examName = examDetails.getExamName();
		LocalDateTime startDate = examDetails.getStartDate();
		String organizationName = organization.getOrganizationName();
		String email = username.getUsername();
		String subject = "Acknowledgemnt for your application";
		String body = String.format(
				"Thanks for choosing your interest in applying for %s in %s we will shortly reach you with test url.\n"
						+ "The exam Date is %s and Description : %s",
				examName, organizationName, startDate, description);
		emailSenderService.sendEmail(email, subject, body);

		return "success";
	}

}
