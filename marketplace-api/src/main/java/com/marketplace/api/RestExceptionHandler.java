package com.marketplace.api;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.ErrorCodes;
import com.marketplace.domain.FileIOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleFailure(Exception e) {
        log.error("Internal server error ", e);
        return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> handleFailure(AccessDeniedException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileIOException.class)
    protected ResponseEntity<String> handleFailure(FileIOException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<String> handleFailure(StaleObjectStateException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<String> handleFailure(ApplicationException e) {
    	log.error("Application error ", e.getMessage());
        if (ErrorCodes.FORBIDDEN.equals(e.getCode())) {
            return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        if (ErrorCodes.NOT_FOUND.equals(e.getCode())) {
            return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (ErrorCodes.UNAUTHORIZED.equals(e.getCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> buildResponseEntity(String error, HttpStatus status) {
        return ResponseEntity.status(status).body(error);

    }
}
