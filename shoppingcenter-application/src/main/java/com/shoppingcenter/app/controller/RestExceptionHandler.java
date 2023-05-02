package com.shoppingcenter.app.controller;

import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.FileIOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @Autowired
//    private AppProperties properties;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleFailure(Exception e) {
        e.printStackTrace();
        return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> handleFailure(AccessDeniedException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileIOException.class)
    protected ResponseEntity<String> handleFailure(FileIOException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<String> handleFailure(StaleObjectStateException e) {
        return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<String> handleFailure(ApplicationException e) {
    	e.printStackTrace();
        if (ErrorCodes.FORBIDDEN.equals(e.getCode())) {
            return buildResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        if (ErrorCodes.NOT_FOUND.equals(e.getCode())) {
            return buildResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (ErrorCodes.UNAUTHORIZED.equals(e.getCode())) {
            // clear cookies
            // var headers = new HttpHeaders();
            // headers.add("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=/;
            // Domain=%s; HttpOnly",
            // JwtTokenFilter.ACCESS_TOKEN_KEY, "", 0, properties.getDomain()));
//            headers.add("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=/; Domain=%s; HttpOnly",
//                    JwtTokenFilter.REFRESH_TOKEN_KEY, "", 0, properties.getDomain()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<String> buildResponseEntity(String error, HttpStatus status) {
        return ResponseEntity.status(status).body(error);

    }
}
