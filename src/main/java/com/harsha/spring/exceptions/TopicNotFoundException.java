/*
 * The following exception is raised when questions are created with non existing topics
 */


package com.harsha.spring.exceptions;

public class TopicNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1900952860698088527L;
	
	public TopicNotFoundException(String msg) {
		super(msg);
	}

}
