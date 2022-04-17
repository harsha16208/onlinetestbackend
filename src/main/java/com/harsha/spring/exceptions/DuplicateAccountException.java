/*
 * @Author : Harsha
 * 
 * The Exception class is for generating exceptions when a user tries to create account with existing username
 */

package com.harsha.spring.exceptions;

public class DuplicateAccountException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2908027891850358150L;

	public DuplicateAccountException(String msg) {
		super(msg);
	}
}
