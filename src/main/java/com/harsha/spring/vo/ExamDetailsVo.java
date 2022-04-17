package com.harsha.spring.vo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class ExamDetailsVo {

	@NotBlank(message = "Provide exam name")
	private String examName;
	@Min(value = 10, message = "the minimum exam duration should be 10 minutes")
	private short examDuration;
//	@Min(value = 20)
	private short numberOfQuestions;
//	@Min(value = 20)
	private short numberOfQuestionsInExam;
	@NotNull(message = "exam start date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime startDate;
	@NotNull(message = "examination end date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime endDate;
	@NotNull(message = "registration end date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime registrationEndDate;
	@Min(1)
	private short noOfTopics;
	@NotNull
	private Map<String, Integer> noOfQuestionsPerTopic = new HashMap<>();
	private Map<String, Integer> noOfQuestionsPerTopicInExam = new HashMap<>();
	@NotNull(message = "description is required for the exam")
	private String description;
	
	
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public short getExamDuration() {
		return examDuration;
	}
	public void setExamDuration(short examDuration) {
		this.examDuration = examDuration;
	}
	public short getNumberOfQuestions() {
		return numberOfQuestions;
	}
	public void setNumberOfQuestions(short numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	public short getNumberOfQuestionsInExam() {
		return numberOfQuestionsInExam;
	}
	public void setNumberOfQuestionsInExam(short numberOfQuestionsInExam) {
		this.numberOfQuestionsInExam = numberOfQuestionsInExam;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public LocalDateTime getRegistrationEndDate() {
		return registrationEndDate;
	}
	public void setRegistrationEndDate(LocalDateTime registrationEndDate) {
		this.registrationEndDate = registrationEndDate;
	}
	public Map<String, Integer> getNoOfQuestionsPerTopic() {
		return noOfQuestionsPerTopic;
	}
	public void setNoOfQuestionsPerTopic(Map<String, Integer> noOfQuestionsPerTopic) {
		this.noOfQuestionsPerTopic = noOfQuestionsPerTopic;
	}
	public short getNoOfTopics() {
		return noOfTopics;
	}
	public void setNoOfTopics(short noOfTopics) {
		this.noOfTopics = noOfTopics;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, Integer> getNoOfQuestionsPerTopicInExam() {
		return noOfQuestionsPerTopicInExam;
	}
	public void setNoOfQuestionsPerTopicInExam(Map<String, Integer> noOfQuestionsPerTopicInExam) {
		this.noOfQuestionsPerTopicInExam = noOfQuestionsPerTopicInExam;
	}
		
}
