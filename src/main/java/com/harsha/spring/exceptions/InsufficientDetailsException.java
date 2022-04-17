package com.harsha.spring.exceptions;

public class InsufficientDetailsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5617940522069807030L;

	public InsufficientDetailsException(String msg) {
		super(msg);
	}
}
