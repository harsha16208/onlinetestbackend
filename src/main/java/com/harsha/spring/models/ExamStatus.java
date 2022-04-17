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

@Entity
@Table(name = "exam_status")
public class ExamStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int statusId;
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean attempted;
	@OneToOne
	@JoinColumn(name = "registered_candidates")
	private RegisteredCandidates registeredCandidates;
	private LocalDateTime startTime;
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean submitted;
	private LocalDateTime submittedTime;

	public ExamStatus() {
	}

	public ExamStatus(boolean attempted, RegisteredCandidates registeredCandidates, LocalDateTime startTime,
			boolean submitted, LocalDateTime submittedTime) {
		this.attempted = attempted;
		this.registeredCandidates = registeredCandidates;
		this.startTime = startTime;
		this.submitted = submitted;
		this.submittedTime = submittedTime;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public boolean isAttempted() {
		return attempted;
	}

	public void setAttempted(boolean attempted) {
		this.attempted = attempted;
	}

	public RegisteredCandidates getRegisteredCandidate() {
		return registeredCandidates;
	}

	public void setRegisteredCandidates(RegisteredCandidates registeredCandidates) {
		this.registeredCandidates = registeredCandidates;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public LocalDateTime getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(LocalDateTime submittedTime) {
		this.submittedTime = submittedTime;
	}

	public void setRegisteredCandidate(RegisteredCandidates registeredCandidates) {
		this.registeredCandidates = registeredCandidates;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	

}
