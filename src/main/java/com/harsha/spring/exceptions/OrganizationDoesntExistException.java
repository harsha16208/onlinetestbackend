/*
 * The exception is created when an organization doesn't exist
 */

package com.harsha.spring.exceptions;

public class OrganizationDoesntExistException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6577348667395603914L;

	public OrganizationDoesntExistException(String msg) {
			super(msg);
	}

}
