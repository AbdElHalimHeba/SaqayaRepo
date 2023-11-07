package com.saqaya.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.saqaya.exception.AlreadyExistsException;
import com.saqaya.exception.EmptyContentException;

/**
 * 
 * <h1>GlobalExceptionHandler Class</h1> 
 * <p>Cross cutting exception handling mechanism.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmptyContentException.class)
	public ResponseEntity<String> handleEmptyContentException(EmptyContentException exception) {

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException exception) {

		return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAuthorizationException(AccessDeniedException exception) {

		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception exception) {

		return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
