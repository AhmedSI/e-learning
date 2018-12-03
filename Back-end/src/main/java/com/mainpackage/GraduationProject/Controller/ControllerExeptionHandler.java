package com.mainpackage.GraduationProject.Controller;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mainpackage.GraduationProject.util.ErrorMessage;

@ControllerAdvice
public class ControllerExeptionHandler {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	ErrorMessage exeptionHandler(ValidationException e)
	{
		return new ErrorMessage("400",e.getMessage());
	}
		@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	ErrorMessage exeption(DataIntegrityViolationException  e)
	{
		return new ErrorMessage("500",e.getMessage());
	}
	
	
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	ErrorMessage exeption(NoSuchElementException  e)
	{
		return new ErrorMessage("500",e.getMessage());
	}

	
}
