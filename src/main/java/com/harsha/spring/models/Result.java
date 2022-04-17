package com.harsha.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int resultId;
	private long result;
	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private ExamDetails examDetails;
	@OneToOne
	@JoinColumn(name = "registered_candidates")
	private RegisteredCandidates registeredCandidates;
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isQualified;
	@Column(nullable =false, columnDefinition = "boolean default false")
	private boolean isResultFiltered;
	@ManyToOne
	@JoinColumn(name = "candidate")
	private Candidate candidate;
	
	
	public Result() {
	}

	public Result(long result, Organization organization, ExamDetails examDetails,
			RegisteredCandidates registeredCandidates, Candidate candidate) {
		this.result = result;
		this.organization = organization;
		this.examDetails = examDetails;
		this.registeredCandidates = registeredCandidates;
		this.candidate = candidate;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}


	public ExamDetails getExamDetails() {
		return examDetails;
	}

	public void setExamDetails(ExamDetails examDetails) {
		this.examDetails = examDetails;
	}

	public RegisteredCandidates getRegisteredCandidates() {
		return registeredCandidates;
	}

	public void setRegisteredCandidates(RegisteredCandidates registeredCandidates) {
		this.registeredCandidates = registeredCandidates;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public long getResult() {
		return result;
	}

	public boolean isQualified() {
		return isQualified;
	}

	public void setQualified(boolean isQualified) {
		this.isQualified = isQualified;
	}

	public boolean isResultFiltered() {
		return isResultFiltered;
	}

	public void setResultFiltered(boolean isResultFiltered) {
		this.isResultFiltered = isResultFiltered;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}
