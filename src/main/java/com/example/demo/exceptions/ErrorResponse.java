package com.example.demo.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

	private Integer status_code;
	
	private String message;
	
	private String error;
	
	private LocalDateTime timeStamp;

	public Integer getStatus_code() {
		return status_code;
	}

	public void setStatus_code(Integer status_code) {
		this.status_code = status_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public ErrorResponse(Integer status_code, String message, String error, LocalDateTime timeStamp) {
		this.status_code = status_code;
		this.message = message;
		this.error = error;
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status_code=" + status_code + ", message=" + message + ", error=" + error
				+ ", timeStamp=" + timeStamp + "]";
	}
	
	
}	
