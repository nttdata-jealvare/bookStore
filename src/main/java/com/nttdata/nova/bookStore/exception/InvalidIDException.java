package com.nttdata.nova.bookStore.exception;

public class InvalidIDException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidIDException() {
		super("The ID should be more than zero");
	}

}
