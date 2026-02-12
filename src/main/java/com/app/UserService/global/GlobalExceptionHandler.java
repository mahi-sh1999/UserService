package com.app.UserService.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.UserService.service.exception.ErrorResponse;
import com.app.UserService.service.exception.UserNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler  {
	
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse>  handleUserNotFoundException(
	UserNotFoundException ex){
	
	
	logger.error("UserNotFoundException",ex);
	ErrorResponse error= new ErrorResponse(org.springframework.http.HttpStatus.NOT_FOUND.value(),ex.getMessage());
	
	return new ResponseEntity<ErrorResponse>(error,org.springframework.http.HttpStatus.NOT_FOUND );
	
	
	}

}
