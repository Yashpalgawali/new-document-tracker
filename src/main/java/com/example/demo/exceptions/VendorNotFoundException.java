package com.example.demo.exceptions;

public class VendorNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123223423434L;

	public VendorNotFoundException(String message) {
			super(message);
	}

	
}
