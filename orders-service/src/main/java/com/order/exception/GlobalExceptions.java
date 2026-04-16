package com.order.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {
	
	//manejo de errores de validacion @valid
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> ValidationErrors(MethodArgumentNotValidException ex){
		Map<String, Object> errors =  new HashMap<>();
		errors.put("timestap", LocalDateTime.now());
		errors.put("status", HttpStatus.BAD_REQUEST.value());
		errors.put("error", "Error de validacion");
		
		Map<String, String> fieldErrors = new HashMap<>();
		 ex.getBindingResult().getFieldErrors().forEach(error ->
         fieldErrors.put(error.getField(), error.getDefaultMessage()));
		 
		 errors.put("fields", fieldErrors);
		 return ResponseEntity.badRequest().body(errors);
	}
	
	//manejo de runtime exception
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> ErrorRuntimeException (RuntimeException ex){
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", ex.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	//otro error inesperado 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> GenericException(Exception ex){
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put("error", "Error interno del servidor");
		error.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
