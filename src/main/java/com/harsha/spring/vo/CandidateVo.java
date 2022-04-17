package com.harsha.spring.vo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class CandidateVo {

	private String candidateId;
	@NotBlank(message = "username required")
	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "username must be a mail id")
	private String username;
	@NotBlank(message = "password required")
	@Pattern(regexp = "^(?=.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
	message = "Password must contain atleast one capital letter, one digit and one special character")
	private String password;
	@NotBlank(message = "provide candidate name")
	private String name;
	@NotNull(message = "Date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@NotBlank(message = "Mobile number required")
	@Pattern(regexp = "^$|[0-9]{10}", message = "please provide mobile number in 10 digits")
	private String mobile;

	public CandidateVo() {
	}

	public CandidateVo(String username, String password, String name, LocalDate dateOfBirth, String mobile) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.mobile = mobile;
	}
	
	public CandidateVo(String username, String candidateId, String name, LocalDate dateOfBirth, String mobile, boolean out) {
		this.username = username;
		this.candidateId = candidateId;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "CandidateVo [username=" + username + ", password=" + password + ", name=" + name + ", dateOfBirth="
				+ dateOfBirth + "]";
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	
	
	
}
