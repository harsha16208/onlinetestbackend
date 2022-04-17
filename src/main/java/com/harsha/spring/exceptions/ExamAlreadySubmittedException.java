package com.harsha.spring.exceptions;

public class ExamAlreadySubmittedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2260636114451400787L;
	
	public ExamAlreadySubmittedException(String msg) {
		super(msg);
	}

}
