/*
 * This exception is raised when number of questions posted are not equal to expected number of questions
 */

package com.harsha.spring.exceptions;

public class QuestionCountNotMatchedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8288079884655921498L;

	public QuestionCountNotMatchedException(String msg) {
		super(msg);
	}
}
