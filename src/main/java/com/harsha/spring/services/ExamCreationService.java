package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.harsha.spring.exceptions.IllegalExamDateException;
import com.harsha.spring.exceptions.NoExamFoundException;
import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.exceptions.QuestionChangeForgeryException;
import com.harsha.spring.exceptions.QuestionCountNotMatchedException;
import com.harsha.spring.exceptions.TopicCountNotMatchedException;
import com.harsha.spring.exceptions.TopicNotFoundException;
import com.harsha.spring.models.Answer;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.ExamLink;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.Question;
import com.harsha.spring.models.Topic;
import com.harsha.spring.repositories.AnswerRepository;
import com.harsha.spring.repositories.ExamDetailsRepository;
import com.harsha.spring.repositories.ExamLinkRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.QuestionRepository;
import com.harsha.spring.repositories.TopicRepository;
import com.harsha.spring.vo.ExamDetailsVo;
import com.harsha.spring.vo.QuestionPattern;
import com.harsha.spring.vo.QuestionVo;

@Service
public class ExamCreationService {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private ExamDetailsRepository examDetailsRepository;
	
	@Autowired
	private ExamLinkRepository examLinkRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	
	private final static Logger LOGGER = Logger.getLogger(ExamCreationService.class);
	
	/*
	 * The below method creates an examination and throws Exception if organization id doesn't exist
	 */
	public String createExam(String orgId, ExamDetailsVo examDetails) throws Exception{
		//getting logged in user
		User loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Organization> organization = organizationRepository.findById(orgId);
		if(!organization.isPresent()) { // if organization doesn't exist throw an exception
			LOGGER.error("Creation of exam with invalid organization id");
			throw new OrganizationDoesntExistException("organization with given id "+ orgId +" doesn't exist");
		}
		
		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = organization.get().getUsername();
		
		if (! actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		
		LocalDateTime startDate = examDetails.getStartDate();
		LocalDateTime endDate = examDetails.getEndDate();
		LocalDateTime registrationEndDate = examDetails.getRegistrationEndDate();
		
		if (startDate.isBefore(LocalDateTime.now())) {
			throw new IllegalExamDateException("cannot create exam in history");
		}
		if (endDate.compareTo(startDate) < 0) {
			LOGGER.error("End date is before start date");
			throw new IllegalExamDateException("The end date cannot be before start date");
		}
		
		if(registrationEndDate.compareTo(startDate) > 0) {
			LOGGER.error("registration date is greater than start date");
			throw new IllegalExamDateException("The registration date should not exceed start date");
		}
		
		short numberOfQuestions = examDetails.getNumberOfQuestions();
		short numberOfQuestionsInExam = examDetails.getNumberOfQuestionsInExam();
		
		if(numberOfQuestions < numberOfQuestionsInExam) {
			LOGGER.error("The number of questions in exam are greater than provided number of questions");
			throw new QuestionCountNotMatchedException("The number of questions in exam : "+numberOfQuestions+" are lesser than number of questions in exam : "
			+numberOfQuestionsInExam);
		}
		
		short numberOfTopics = examDetails.getNoOfTopics();
		Map<String, Integer> noOfQuestionsPerTopic = examDetails.getNoOfQuestionsPerTopic();
		final Map<String, Integer> noOfQuestionsPerTopicInExam = examDetails.getNoOfQuestionsPerTopicInExam();
		int noOfProvidedTopics = noOfQuestionsPerTopic.size();
		if (noOfProvidedTopics != numberOfTopics) {
			throw new TopicCountNotMatchedException("No of expected topics : "+numberOfTopics+" provided : "+noOfProvidedTopics);
		}
		
		
		ExamDetails createdExamDetails = new ExamDetails(organization.get(), examDetails.getNumberOfQuestions(), examDetails.getNumberOfQuestionsInExam(), 
				examDetails.getExamName(), examDetails.getExamDuration(), startDate, endDate, registrationEndDate, numberOfTopics,
				examDetails.getDescription());
		ExamDetails examDetailsFromDB = examDetailsRepository.save(createdExamDetails);
		LOGGER.info("exam created successfully");
		String organizationId = organization.get().getId();
		String regLink  = "register/applyforexam/"+organizationId+"/"+examDetailsFromDB.getExamId();
		String testUrl  = "test/"+organizationId+"/"+examDetailsFromDB.getExamId();
		ExamLink examLink = new ExamLink(examDetailsFromDB, regLink, testUrl);
		examLinkRepository.save(examLink);
		noOfQuestionsPerTopic.forEach((topicName, questionsNumber)->{
			final int noOfQuestionsInExam = noOfQuestionsPerTopicInExam.get(topicName);
			Topic topic = new Topic(topicName, questionsNumber, noOfQuestionsInExam, examDetailsFromDB);
			topicRepository.save(topic);
		});
		LOGGER.info("exam Links created successfully");
		return "created";
	}
	
	/*
	 * This method validates all the questions and throws the following exceptions
	 * 1. OrganizationDoesntExistException when a non-existing organization is used in URL
	 * 2. NoExamFoundException when an questions are posted for a non-existing exam
	 * 3. QuestionCountNotMatchedException when no.of incoming questions are not equal to the given number of questions for exam
	 */
	public String postQuestions(String orgId, String eId, QuestionVo questions) throws Exception{
		//getting logged in user
		User loggedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String status="";
		Optional<Organization> findOrganization = organizationRepository.findById(orgId);
		Optional<ExamDetails> findExam = examDetailsRepository.findById(eId);
		ExamDetails examDetails = null;
		List<List<QuestionPattern>> questionsReceived = null;
		int expectedNumberOfQuestions = 0;
		List<Question> questionsFromRepo = null;
		List<String> topics;
		int expectedTopics = 0;
		List<Integer> numberOfQuestionsInEachTopic = new ArrayList<>();
		int providedNumberOfQuestions = 0;
		List<String> notExistingTopics;
		List<Integer> expectedNumberOfQuestonsInEachtopic = new ArrayList<>();
		
		if(!findOrganization.isPresent()) {
			LOGGER.error("Posting questions with invalid organization id");
			throw new OrganizationDoesntExistException("No organization found with given id : "+orgId);
		}
		
		// verifying logged in user with actual user
		com.harsha.spring.models.User actualUser = findOrganization.get().getUsername();
		
		if (! actualUser.getUsername().equals(loggedUser.getUsername())) {
			throw new IllegalAccessException("You're not permitted here");
		}
		if(!findExam.isPresent()) {
			LOGGER.error("Posting questions with invalid examination id");
			throw new NoExamFoundException("No Exam found with given id : "+eId);
		}
		else {
			examDetails = findExam.get();
			questionsReceived = new ArrayList<>(questions.getQuestions().values());
			topics = new ArrayList<>(questions.getQuestions().keySet());		
			expectedNumberOfQuestions = findExam.get().getNumberOfQuestions();
			questionsFromRepo = questionRepository.findByExamId(findExam.get());
			expectedTopics = findExam.get().getNumberOfTopics();
			
			questionsReceived.stream().forEach(section -> {
				numberOfQuestionsInEachTopic.add(section.size());
			});
			
			providedNumberOfQuestions = numberOfQuestionsInEachTopic.stream().reduce(0, (num1, num2)-> num1+num2);
		}
		if(questionsFromRepo.size() > 0) {
			throw new QuestionChangeForgeryException("Questions for this exam are already posted");
		}
		if (providedNumberOfQuestions != expectedNumberOfQuestions ||  topics.size() != expectedTopics) {
			LOGGER.error("Posting questions without providing required number of questions");
			throw new QuestionCountNotMatchedException("The number of questions you provided is : "+providedNumberOfQuestions+ " expected : "+expectedNumberOfQuestions);
		}
		final ExamDetails EXAMDETAILS = examDetails;
		notExistingTopics = topics.stream().filter(topic ->
			!topicRepository.findByTopicNameAndExamDetails(topic, EXAMDETAILS).isPresent()	).collect(Collectors.toList());
		
		if (notExistingTopics.size() > 0) {
			throw new TopicNotFoundException("The following topics are not found : "+ notExistingTopics);
		}
		
		topics.stream().forEach(topic ->{
			Topic topicFromDB = topicRepository.findByTopicNameAndExamDetails(topic, EXAMDETAILS).get();
			int numberOfQuestions = topicFromDB.getNumberOfQuestions();
			expectedNumberOfQuestonsInEachtopic.add(numberOfQuestions);
		});
		
		boolean equals = expectedNumberOfQuestonsInEachtopic.equals(numberOfQuestionsInEachTopic);
		
		if (!equals) {
			throw new QuestionCountNotMatchedException("The number of questions expected in each topic are : "+ expectedNumberOfQuestonsInEachtopic+
					" Provided : "+numberOfQuestionsInEachTopic
					);
		}
		
		
		final List<List<QuestionPattern>> receivedQuestions = questionsReceived;
		questionsReceived.stream().forEach(topic->{
			topic.stream().forEach(question ->{
			String questionRetrieved = question.getQuestion();
			String option1 = question.getOption1();
			String option2 = question.getOption2();
			String option3 = question.getOption3();
			String option4 = question.getOption4();
			String answer = question.getAnswer();
			int indexOfTopic = receivedQuestions.indexOf(topic);
			String topicName = topics.get(indexOfTopic);
			Question createdQuestion = new Question(EXAMDETAILS, questionRetrieved, option1, option2, option3, option4, topicName);
			EXAMDETAILS.setQuestionsPosted(true);
			examDetailsRepository.save(EXAMDETAILS);
			Question savedQuestion = questionRepository.save(createdQuestion);
			Answer createdAnswer = new Answer(savedQuestion, answer);
			answerRepository.save(createdAnswer);
			
			});
		});
		
		LOGGER.info("question and answer created");
		status = "success";
		return status;
	}
	
}
