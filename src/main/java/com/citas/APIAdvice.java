package com.citas;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.citas.DTO.ErrorDTO;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@ResponseBody
@ControllerAdvice(basePackages = "com.citas.controllers")
public class APIAdvice {
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO notFound(NotFoundException e) {
		return new ErrorDTO(404,e.getMessage());
	}
	
	@ExceptionHandler(InvalidDataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO notFound(InvalidDataException e) {
		return new ErrorDTO(400,e.getMessage());
	}
}
