package com.shoppingcenter.app.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shoppingcenter.core.ApplicationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleFailure(Exception e) {
        e.printStackTrace();
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleFailure(AccessDeniedException e) {
        return buildResponseEntity(new ErrorResponse(HttpStatus.FORBIDDEN.name(), e.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ErrorResponse> handleFailure(ApplicationException e) {
        return buildResponseEntity(new ErrorResponse(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse error, HttpStatus status) {
        return new ResponseEntity<>(error, status);
    }
}
