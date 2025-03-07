package com.rubenrbr.prices.infrastructure.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.rubenrbr.prices.domain.exception.PriceNotFoundException;
import com.rubenrbr.prices.infrastructure.rest.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setMessage("Invalid parameter format");
    if (ex.getName().equals("applicationDate")) {
      error.setDetails(
          "The parameter 'applicationDate' has an invalid format. "
              + "Date format is 2020-06-16T21:00:00Z");
    } else {
      error.setDetails(
          "The parameter '" + ex.getName() + "' has an invalid format. Must be a number");
    }
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setMessage("Missing required parameter '" + ex.getParameterName() + "'");
    error.setDetails(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setMessage("Price not found");
    error.setDetails(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse error = new ErrorResponse();
    error.setMessage("Internal server error");
    error.setDetails(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
