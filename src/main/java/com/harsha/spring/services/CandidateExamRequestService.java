package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.exceptions.ExamAlreadySubmittedException;
import com.harsha.spring.exceptions.IllegalAttemptException;
import com.harsha.spring.exceptions.InsufficientDetailsException;
import com.harsha.spring.exceptions.NoExamFoundException;
import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.models.Answer;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.ExamStatus;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.Question;
import com.harsha.spring.models.RegisteredCandidates;
import com.harsha.spring.models.Result;
import com.harsha.spring.models.Topic;
import com.harsha.spring.repositories.AnswerRepository;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.ExamStatusRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.QuestionRepository;
import com.harsha.spring.repositories.RegisteredCandidatesRepository;
import com.harsha.spring.repositories.ResultRepository;
import com.harsha.spring.repositories.TopicRepository;
import com.harsha.spring.util.ExamStrategy;
import com.harsha.spring.vo.AnswerPattern;
import com.harsha.spring.vo.AnswerVo;

@Service
public class CandidateExamRequestService {

	@Autowired
	private ExamDetailsRepository examDetailsRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private ExamStatusRepository examStatusRepository;

	@Autowired
	private RegisteredCandidatesRepository registeredCandidatesRepository;

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private TopicRepository topicRepository;

	public List<Map<String, List<Question>>> generateQuestionPaper(String orgId, String eId, String cId)
			throws Exception {
		// Getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		ExamDetails examDetails = null;
		Candidate candidate = null;
		RegisteredCandidates registredCandidateDetails = null;
		com.harsha.spring.models.User actualUser = null;
		List<Map<String, List<Question>>> questionPaper = new ArrayList<>();

		if (findCandidate.isPresent()) {
			candidate = findCandidate.get();
			// verifying logged in user with actual user
			actualUser = candidate.getUsername();
			if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
				throw new IllegalAccessException("You're not permitted here");
			}

			if (findOrganization.isPresent()) {
			} else {
				throw new OrganizationDoesntExistException("No organization found with given id : " + orgId);
			}
			if (findExam.isPresent()) {
				examDetails = findExam.get();
			} else {
				throw new NoExamFoundException("No Exam found with given id : " + eId);
			}

			// find whether candidate registered for exam or not
			Optional<RegisteredCandidates> findRegisteredCandidate = registeredCandidatesRepository
					.findByCandidateAndExamDetails(candidate, examDetails);

			if (findRegisteredCandidate.isPresent()) {
				
				//Exam should start after start time and grace period of 15mins is given
				LocalDateTime startTime = examDetails.getStartDate();
				LocalDateTime currentTime  = LocalDateTime.now();
				LocalDateTime endTime = examDetails.getEndDate();
				
				
				
				if (startTime.isAfter(currentTime) || currentTime.isAfter(endTime)) {
					return null;
				}
				
				registredCandidateDetails = findRegisteredCandidate.get();
				Optional<ExamStatus> findExamStatus = examStatusRepository.findByRegisteredCandidates(registredCandidateDetails);
				if (findExamStatus.isPresent()) {
					throw new IllegalAttemptException("You have already attempted the exam");
				}
				
				
				// if candidate is registered generate question paper and return
				List<Question> questions = questionRepository.findByExamId(examDetails);
				List<Topic> topics = topicRepository.findByExamDetails(examDetails);
				Map<String, Integer> weightage = new HashMap<>();

				topics.stream().forEach(topic -> {
					Map<String, List<Question>> sectionNameAndQuestions = new HashMap<>();
					int numberOfQuestionsInExam = topic.getNumberOfQuestionsInExam();
					int numberOfQuestions = topic.getNumberOfQuestions();
					weightage.put(topic.getTopicName(), topic.getNumberOfQuestionsInExam());
					List<Question> sectionquestions = questions.stream().filter(question -> {
						return question.getTopic().equals(topic.getTopicName());
					}).collect(Collectors.toList());
					List<Question> filteredQuestions = ExamStrategy.generateQuestionPaper(numberOfQuestions, numberOfQuestionsInExam, sectionquestions);
					sectionNameAndQuestions.put(topic.getTopicName(), filteredQuestions);
					questionPaper.add(sectionNameAndQuestions);
				});

				ExamStatus examStatus = new ExamStatus(true, registredCandidateDetails, LocalDateTime.now(), false, null);
				examStatusRepository.save(examStatus);
				
				return questionPaper;
			} else {
				throw new CandidateNotFoundException("Candidate not registered for exam");
			}

		}
		throw new CandidateNotFoundException("No Candidate found with given id : " + cId);
	}

	// when candidate submits answers
	public String submit(AnswerVo answers, String orgId, String eId, String cId) throws Exception {
		// getting logged in user
		org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		List<AnswerPattern> questionAndAnswer = answers.getAnswers();
		Optional<Organization> organization = organizationRepository.findById(orgId);
		Optional<ExamDetails> examDetails = examDetailsRepository.findById(eId);
		Optional<Candidate> findCandidate = candidateRepository.findById(cId);
		Optional<RegisteredCandidates> registeredCandidate = null;
		Candidate candidate = null;

		//
		if (organization.isEmpty() || examDetails.isEmpty() || findCandidate.isEmpty()) {
			System.out.println(1);
			throw new InsufficientDetailsException("Invalid submission");
		}
		
		candidate = findCandidate.get();
		registeredCandidate = registeredCandidatesRepository.findByCandidateAndExamDetails(candidate, examDetails.get());
		
		if (registeredCandidate.isEmpty()) {
			System.out.println(2);
			throw new InsufficientDetailsException("Invalid Submission");
		}

		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = registeredCandidate.get().getUser();
		if (!actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}

		if (resultRepository.findByRegisteredCandidates(registeredCandidate.get()).isPresent()) {
			throw new ExamAlreadySubmittedException("Your exam is already submitted");
		}

		long score = questionAndAnswer.stream().filter(answer -> {
			String submittedAnswer = answer.getAnswer();
			String qId = answer.getqId();
			Optional<Question> question = questionRepository.findById(qId);
			Answer answerFromDB = answerRepository.findByQuestion(question.get());
			String actualAnswer = answerFromDB.getAnswer();
			boolean result = actualAnswer.equals(submittedAnswer);
			return result;
		}).count();

		Optional<ExamStatus> status = examStatusRepository.findByRegisteredCandidates(registeredCandidate.get());
		if (status.isPresent()) {
			ExamStatus examStatus = status.get();
			examStatus.setSubmitted(true);
			examStatus.setSubmittedTime(LocalDateTime.now());
			examStatusRepository.save(examStatus);
		}


		Result result = new Result(score, organization.get(), examDetails.get(), registeredCandidate.get(), candidate);
		resultRepository.save(result);

		return "success";
	}

}
