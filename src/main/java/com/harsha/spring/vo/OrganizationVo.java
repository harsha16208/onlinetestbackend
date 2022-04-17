package com.harsha.spring.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class OrganizationVo {

	private String organizationId;
	@NotBlank(message = "Username is Required")
	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "username must be a mail id")
	private String username;
	@NotBlank(message = "password is Required")
	@Pattern(regexp = "^(?=.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
	message = "Password must contain atleast one capital letter, one digit and one special character")
	private String password;
	@NotBlank
	@Pattern(regexp = "^$|[0-9]{10}", message = "please provide mobile number in 10 digits")
	private String mobile;
	@NotBlank(message = "provide organization name")
	private String organizationName;
	private String orgLink;
	private boolean isAccessGiven;
	
	public OrganizationVo() {
	}
	
	public OrganizationVo(String username, String password, String mobile, String organizationName, String orgLink) {
		this.username = username;
		this.password = password;
		this.mobile = mobile;
		this.organizationName = organizationName;
		this.orgLink = orgLink;
	}
	
	public OrganizationVo(String username, String organizationId, String mobile, String organizationName, String orgLink, boolean isAccessGiven, boolean out) {
		this.username = username;
		this.organizationId = organizationId;
		this.mobile = mobile;
		this.organizationName = organizationName;
		this.orgLink = orgLink;
		this.isAccessGiven = isAccessGiven;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrgLink() {
		return orgLink;
	}

	public void setOrgLink(String orgLink) {
		this.orgLink = orgLink;

	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public boolean isAccessGiven() {
		return isAccessGiven;
	}

	public void setAccessGiven(boolean isAccessGiven) {
		this.isAccessGiven = isAccessGiven;
	}
	
}
