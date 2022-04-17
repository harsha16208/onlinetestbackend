/*
 * This exception is raised when requested candidate id is not found
 */

package com.harsha.spring.exceptions;

public class CandidateNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6575697818558302007L;

	public CandidateNotFoundException(String msg) {
		super(msg);
	}
}
