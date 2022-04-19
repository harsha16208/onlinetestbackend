package com.harsha.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harsha.spring.generators.IdGenerator;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "que_seq")
	@GenericGenerator(name = "que_seq", strategy = "com.harsha.spring.generators.IdGenerator", 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1" ),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "QUE_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
			
	})
	private String qId;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "examId")
	private ExamDetails examId;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String topic;
	
	
	public Question() {
		
	}
	
	public Question( ExamDetails eId, String question, String option1, String option2, String option3,
			String option4, String topic) {
		this.examId = eId;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.topic = topic;
	}
	
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public ExamDetails getExamId() {
		return examId;
	}
	public void setExamId(ExamDetails eId) {
		this.examId = eId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	
	
}
