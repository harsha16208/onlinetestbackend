package com.harsha.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "topics")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String topicName;
	private int numberOfQuestions;
	private int numberOfQuestionsInExam;
	@ManyToOne
	@JoinColumn(name = "exam_details")
	private ExamDetails examDetails;
	
	public Topic() {
	}
	
	public Topic( String topicName, int numberOfQuestions, int numberOfQuestionsInExam,
			ExamDetails examDetails) {
		this.topicName = topicName;
		this.numberOfQuestions = numberOfQuestions;
		this.numberOfQuestionsInExam = numberOfQuestionsInExam;
		this.examDetails = examDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public ExamDetails getExamDetails() {
		return examDetails;
	}

	public void setExamDetails(ExamDetails examDetails) {
		this.examDetails = examDetails;
	}

	public int getNumberOfQuestionsInExam() {
		return numberOfQuestionsInExam;
	}

	public void setNumberOfQuestionsInExam(int numberOfQuestionsInExam) {
		this.numberOfQuestionsInExam = numberOfQuestionsInExam;
	}
	
}
