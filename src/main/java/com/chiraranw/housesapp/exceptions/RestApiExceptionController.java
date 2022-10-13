package com.chiraranw.housesapp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestApiExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest re) {
        final String errorMessage = ex.getMessage();
        return buildErrorResponse(new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND,re.getPathInfo()));
    }

    @ExceptionHandler(TimeOutException.class)
    public ResponseEntity<Object> timeOut(TimeOutException ex, HttpServletRequest re) {
        final String errorMessage = ex.getMessage();
        return buildErrorResponse(new ErrorResponse(errorMessage, HttpStatus.REQUEST_TIMEOUT,re.getPathInfo()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> internalServerError(InternalServerException ex, HttpServletRequest re) {
        final String errorMessage = ex.getMessage();
        return buildErrorResponse(new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR,re.getPathInfo()));
    }

    @ExceptionHandler(UnAuthorisedException.class)
    public ResponseEntity<Object> unAuthorised(UnAuthorisedException ex, HttpServletRequest re) {
        final String errorMessage = ex.getMessage();
        return buildErrorResponse(new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED,re.getPathInfo()));
    }


    private ResponseEntity<Object> buildErrorResponse(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
