package com.mdt.enocachallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> invalidIdException(InvalidIdException invalidIdException){
        return getErrorResponse(invalidIdException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<ErrorResponse> getErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
