package com.shoppingcenter.app.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleFailure(Exception e) {
        e.printStackTrace();
        return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> handleFailure(AccessDeniedException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<String> handleFailure(ApplicationException e) {
        if (e.getCode() == ErrorCodes.FORBIDDEN) {
            return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        if (e.getCode() == ErrorCodes.NOT_FOUND) {
            return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (e.getCode() == ErrorCodes.VALIDATION_FAILED) {
            return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> buildResponseEntity(String error, HttpStatus status) {
        return ResponseEntity.status(status).body(error);
    }
}
