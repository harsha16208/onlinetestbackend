/*
 * The Exception is raised when questions are posted for a non-existing exam
 */
package com.harsha.spring.exceptions;

public class NoExamFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18049782491543395L;

	public NoExamFoundException(String msg) {
		super(msg);
	}
}
