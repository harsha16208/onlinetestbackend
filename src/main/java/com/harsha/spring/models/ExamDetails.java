package com.harsha.spring.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harsha.spring.generators.IdGenerator;

@Entity
@Table(name = "examdetails")
public class ExamDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_seq")
	@GenericGenerator(name = "exam_seq", strategy = "com.harsha.spring.generators.IdGenerator", 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "2" ),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "EId_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
			
	})
	private String examId;
	@OneToOne
	@JoinColumn(name = "organization")
	@JsonIgnore
	private Organization organization;
	private int numberOfQuestions;
	private int numberOfQuestionInExam;
	private String examName;
	private short duration;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private  LocalDateTime registrationEndDate;
	private int numberOfTopics;
	private String description;
	@Column(columnDefinition = "boolean default false")
	private boolean questionsPosted;
	@Column(columnDefinition = "boolean default false")
	private boolean resultsFiltered;
	
	public ExamDetails() {
	}

	public ExamDetails(Organization organization, int numberOfQuestions, int numberOfQuestionInExam,
			String examName, short duration,  LocalDateTime startDate,
			 LocalDateTime endDate,  LocalDateTime registartionEndDate, int numberOfTopics, String description) {
		this.organization = organization;
		this.numberOfQuestions = numberOfQuestions;
		this.numberOfQuestionInExam = numberOfQuestionInExam;
		this.examName = examName;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;
		this.registrationEndDate = registartionEndDate;
		this.numberOfTopics = numberOfTopics;
		this.description = description;
	}
	

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public int getNumberOfQuestionInExam() {
		return numberOfQuestionInExam;
	}

	public void setNumberOfQuestionInExam(int numberOfQuestionInExam) {
		this.numberOfQuestionInExam = numberOfQuestionInExam;
	}



	public short getDuration() {
		return duration;
	}

	public void setDuration(short duration) {
		this.duration = duration;
	}

	public  LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate( LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public  LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate( LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public  LocalDateTime getRegistrationEndDate() {
		return registrationEndDate;
	}

	public void setRegistrationEndDate( LocalDateTime registrationEndDate) {
		this.registrationEndDate = registrationEndDate;
	}

	public int getNumberOfTopics() {
		return numberOfTopics;
	}

	public void setNumberOfTopics(int numberOfTopics) {
		this.numberOfTopics = numberOfTopics;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isQuestionsPosted() {
		return questionsPosted;
	}

	public void setQuestionsPosted(boolean questionsPosted) {
		this.questionsPosted = questionsPosted;
	}

	public boolean isResultsFiltered() {
		return resultsFiltered;
	}

	public void setResultsFiltered(boolean resultsFiltered) {
		this.resultsFiltered = resultsFiltered;
	}

}
