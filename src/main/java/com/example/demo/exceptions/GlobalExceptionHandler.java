package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException  ex ){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value() ,"User Not Found", ex.getMessage(),LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(VendorNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleVendorNotFoundException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value() ,"Vendor Not Found", ex.getMessage(),LocalDateTime.now());
		System.err.println("Inside handleVendorNotFoundException() "+ errorResponse.toString());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	 
}
