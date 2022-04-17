/*
 * This exception is raised when a candidate tries to register multiple times for a single examination
 */

package com.harsha.spring.exceptions;

public class CandidateAlreadyRegisteredException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1746149088265411642L;

	public CandidateAlreadyRegisteredException(String msg)
	{
		super(msg);
	}
}
