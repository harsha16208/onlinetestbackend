package com.harsha.spring.vo;


import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdationDetailsVo {

	private String username;
	private String name;
	private String candidateId;
	private String dateOfBirth;
	private String mobile;
	public UpdationDetailsVo() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "UpdationDetailsVo [username=" + username + ", name=" + name + ", candidateId=" + candidateId
				+ ", dateOfBirth=" + dateOfBirth + ", mobile=" + mobile + "]";
	}
	
	
}
