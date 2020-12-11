package com.swaarm.adtech.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
public enum ErrorCode implements Serializable {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_EXIST(HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST);

    @Getter
    private final HttpStatus httpStatus;
}
