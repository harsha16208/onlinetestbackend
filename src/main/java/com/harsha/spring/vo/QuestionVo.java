package com.harsha.spring.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionVo {

	private Map<String, List<QuestionPattern>> questions = new HashMap<>();

	public void setQuestions(Map<String, List<QuestionPattern>> questions) {
		this.questions = questions;
	}

	public Map<String, List<QuestionPattern>> getQuestions() {
		return questions;
	}

}
