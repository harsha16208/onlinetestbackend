package com.harsha.spring.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserEmailVo {
	
	@NotBlank(message = "email required")
	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "must be a mail id")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
