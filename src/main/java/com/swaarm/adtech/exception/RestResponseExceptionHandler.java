package com.swaarm.adtech.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Error> handle(BaseException ex) {
        log.warn("BaseException occurred", ex);
        ErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(new Error(errorCode, ex.getMessage()), errorCode.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handle(HttpMessageNotReadableException ex) {
        log.warn("InvalidFormatException occurred", ex);
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        return new ResponseEntity<>(new Error(errorCode, ex.getMessage()), errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        log.warn("Unexpected exception occurred", ex);
        ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        return new ResponseEntity<>(new Error(errorCode, ex.getMessage()), errorCode.getHttpStatus());
    }
}
