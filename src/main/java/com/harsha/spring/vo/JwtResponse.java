package com.harsha.spring.vo;

public class JwtResponse {

	private String jwtToken;
	private String role;
	private Object userDetails;
	
	public JwtResponse(String jwtToken, String role, Object userDetails) {
		this.jwtToken = jwtToken;
		this.role = role;
		this.userDetails = userDetails;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Object getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Object userDetails) {
		this.userDetails = userDetails;
	}
}
