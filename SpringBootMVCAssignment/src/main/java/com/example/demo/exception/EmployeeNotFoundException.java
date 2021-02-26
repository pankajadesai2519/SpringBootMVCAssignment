package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class EmployeeNotFoundException extends HttpStatusCodeException{

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException() {
		super(HttpStatus.NOT_FOUND);
		
	}
	public EmployeeNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND,message);
	}
}
