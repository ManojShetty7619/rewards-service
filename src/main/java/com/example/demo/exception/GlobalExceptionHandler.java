package com.example.demo.exception;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(TransactionNotFoundException ex) {

		log.error("Transaction not found: {}", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, ex.getMessage()));
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(InvalidRequestException ex) {

		log.error("Invalid request: {}", ex.getMessage());

		return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class, DateTimeParseException.class })
	public ResponseEntity<ErrorResponse> handleDateError(Exception ex) {

		log.error("Invalid date format: {}", ex.getMessage());

		return ResponseEntity.badRequest()
				.body(new ErrorResponse(400, "Invalid date. Please use yyyy-MM-dd and provide a valid calendar date."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

		log.error("Unexpected error occurred", ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(500, "Something went wrong. Please contact support."));
	}
}
