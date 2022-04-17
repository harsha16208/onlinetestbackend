package com.harsha.spring.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(value = { DuplicateAccountException.class,
			OrganizationDoesntExistException.class,
			IllegalExamDateException.class,
			NoExamFoundException.class,
			QuestionCountNotMatchedException.class,
			QuestionChangeForgeryException.class,
			CandidateNotFoundException.class,
			CandidateAlreadyRegisteredException.class,
			TopicCountNotMatchedException.class,
			TopicNotFoundException.class,
			BadCredentialsException.class,
			UsernameNotFoundException.class,
			ExamAlreadySubmittedException.class,
			InsufficientDetailsException.class,
			UserNotVerifiedException.class,
			Exception.class
	}
			)
	public ApiError handleDuplicateAccountException(Exception exception)
	{
		String message =  exception.getMessage();
		ApiError error = new ApiError(new Date(), message);
		LOGGER.error(message);
		return error;
	}
	
	@ResponseStatus(HttpStatus.PARTIAL_CONTENT)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
	{
		Map<String,List<String>> errors = new HashMap<>();
		List<String> list = new ArrayList<>();
		exception.getBindingResult().getAllErrors().forEach(error->{
			String errorMessage = error.getDefaultMessage();
			list.add(errorMessage);
		});
		errors.put("errors", list);
		return errors;
	}
	
	@ResponseStatus(HttpStatus.IM_USED)
	@ExceptionHandler(IllegalAttemptException.class)
	public ApiError handleIllegalAttemptException(Exception exception) {
		String message = exception.getMessage();
		ApiError error = new ApiError(new Date(), message);
		LOGGER.error(message);
		return error;
	}
	
}
