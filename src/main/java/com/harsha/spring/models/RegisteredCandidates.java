package com.harsha.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.harsha.spring.generators.IdGenerator;

@Entity
@Table(name = "registered_candidates")
public class RegisteredCandidates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_seq")
	@GenericGenerator(name = "reg_seq", strategy = "com.harsha.spring.generators.IdGenerator", 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1" ),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "REGCID_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d")
			
	})
	private String regId;
	@OneToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;
	@OneToOne
	@JoinColumn(name = "examination_id")
	private ExamDetails examDetails;
	@OneToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;
	@OneToOne
	@JoinColumn(name = "username")
	private User user;
	
	public RegisteredCandidates(Candidate candidate, ExamDetails examDetails, Organization organization,
			User user) {
		this.candidate = candidate;
		this.examDetails = examDetails;
		this.organization = organization;
		this.user = user;
	}

	public RegisteredCandidates() {
	}
	

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public ExamDetails getExamDetails() {
		return examDetails;
	}

	public void setExamDetails(ExamDetails examDetails) {
		this.examDetails = examDetails;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}