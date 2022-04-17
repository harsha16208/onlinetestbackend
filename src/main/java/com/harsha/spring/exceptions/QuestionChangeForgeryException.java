/*
 * This exception is raised when overriding of existing questions happen while posting questions
 */

package com.harsha.spring.exceptions;

public class QuestionChangeForgeryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7887517336117851655L;
	
	public QuestionChangeForgeryException(String msg) {
		super(msg);
	}

}
