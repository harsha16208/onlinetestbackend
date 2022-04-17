/*
 * The exception is raised when number of expected topics and provided topics are not the same
 */
package com.harsha.spring.exceptions;

public class TopicCountNotMatchedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2277736254144570200L;
	
	public TopicCountNotMatchedException(String msg) {
		super(msg);
	}

}
