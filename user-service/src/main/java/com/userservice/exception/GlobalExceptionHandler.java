package com.userservice.exception;

import com.userservice.constants.ErrorCode;
import com.userservice.response.ApiResponse;
import com.userservice.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Generic Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleAllExceptions(Exception ex) {
		ex.printStackTrace(); // replace with logger in production
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseUtil.error(ErrorCode.SERVER_ERROR, ex.getMessage()));
	}

	// Validation Errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMsg = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst().orElse(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseUtil.error(ErrorCode.BAD_REQUEST, errorMsg));
	}

	// Runtime Exceptions (custom)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse> handleRuntimeExceptions(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseUtil.error(ErrorCode.SERVER_ERROR, ex.getMessage()));
	}
}
