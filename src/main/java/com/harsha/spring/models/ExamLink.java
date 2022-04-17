package com.harsha.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exam_links")
public class ExamLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	@JoinColumn(name = "examDetails")
	private ExamDetails examDetails; 
	private String regLink;
	private String testUrl;
	
	public ExamLink() {
	}
	
	public ExamLink(ExamDetails examDetails, String regLink, String testUrl) {
		this.examDetails = examDetails;
		this.regLink = regLink;
		this.testUrl = testUrl;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDetails getExamDetails() {
		return examDetails;
	}
	public void setExamDetails(ExamDetails examDetails) {
		this.examDetails = examDetails;
	}
	public String getRegLink() {
		return regLink;
	}
	public void setRegLink(String regLink) {
		this.regLink = regLink;
	}
	public String getTestUrl() {
		return testUrl;
	}
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
}
