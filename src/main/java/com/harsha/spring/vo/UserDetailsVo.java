package com.harsha.spring.vo;

import java.time.LocalDate;

public class UserDetailsVo {

	private String username;
	private String role;
	private Boolean isAccessGiven;
	private String name;
	private String mobile;
	private LocalDate dateOfCreation;
	
	public UserDetailsVo() {
	}
	
	public UserDetailsVo(String username, String role, Boolean isAccessGiven, String name, String mobile,
			LocalDate dateOfCreation) {
		this.username = username;
		this.role = role;
		this.isAccessGiven = isAccessGiven;
		this.name = name;
		this.mobile = mobile;
		this.dateOfCreation = dateOfCreation;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getHasAccess() {
		return isAccessGiven;
	}
	public void setHasAccess(Boolean isAccessGiven) {
		this.isAccessGiven = isAccessGiven;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(LocalDate dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
}
