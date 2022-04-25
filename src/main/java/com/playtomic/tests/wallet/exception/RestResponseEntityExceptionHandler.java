package com.playtomic.tests.wallet.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, WalletException.class })
    protected ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorMessage(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    public class ErrorMessage {
        private String message;

        public ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
