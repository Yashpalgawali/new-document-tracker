package com.example.demo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,WebRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false),HttpStatus.NOT_FOUND.toString() ,ex.getMessage(),LocalDateTime.now());
		
		return new ResponseEntity<ErrorResponseDto>(errorResponse , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceNotModifiedException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotModifiedException(ResourceNotModifiedException ex,WebRequest request) {
		
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false),HttpStatus.NOT_FOUND.toString() ,ex.getMessage(),LocalDateTime.now());
		
		return new ResponseEntity<ErrorResponseDto>(errorResponse , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(GlobalException ex,WebRequest request) {
		
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false),HttpStatus.NOT_FOUND.toString() ,ex.getMessage(),LocalDateTime.now());		
		return new ResponseEntity<ErrorResponseDto>(errorResponse , HttpStatus.NOT_FOUND);
	}
}
