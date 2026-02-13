package com.example.demo.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(TransactionNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, ex.getMessage()));
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(InvalidRequestException ex) {

		return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleDateError(Exception ex) {

		return ResponseEntity.badRequest()
				.body(new ErrorResponse(400, "Invalid date. Please use yyyy-MM-dd and valid calendar date."));
	}

}
