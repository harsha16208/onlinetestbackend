package com.harsha.spring.vo;

import javax.validation.constraints.NotNull;

public class ExamMailVo {
	@NotNull
	private String Subject;
	@NotNull
	private String body;
	public ExamMailVo() {
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
