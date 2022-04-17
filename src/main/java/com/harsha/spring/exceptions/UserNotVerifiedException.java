package com.harsha.spring.exceptions;

public class UserNotVerifiedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940085587488623152L;

	
	public UserNotVerifiedException(String msg) {
		super(msg);
	}
}
