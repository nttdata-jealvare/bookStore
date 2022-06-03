package com.nttdata.nova.bookStore.exception.global_handler;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nttdata.nova.bookStore.exception.InvalidIDException;
import com.nttdata.nova.bookStore.exception.InvalidNameExtensionException;
import com.nttdata.nova.bookStore.exception.editorialNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	private ErrorMessage message;

	@ExceptionHandler(value = { InvalidIDException.class })
	public HttpEntity<ErrorMessage> bookIdInvalidException(RuntimeException e) {
		this.message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), new Date(), "Not valid ID",
				"The ID should be more than zero");

		return new ResponseEntity<ErrorMessage>(this.message, HttpStatus.NOT_ACCEPTABLE);
	}
	

	@ExceptionHandler(value = { InvalidNameExtensionException.class })
	public HttpEntity<ErrorMessage> nameLengthException(RuntimeException e) {
		this.message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), new Date(), "Not valid name length",
				"The name length must be more than 4");

		return new ResponseEntity<ErrorMessage>(this.message, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = { editorialNotFoundException.class })
	public HttpEntity<ErrorMessage> editorialIdNotFoundException(RuntimeException e) {
		this.message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), "Editorial was not found",
				"This editorial was not found according to the ID you entered");

		return new ResponseEntity<ErrorMessage>(this.message, HttpStatus.NOT_FOUND);
	}
}
