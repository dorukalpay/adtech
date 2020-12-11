package com.swaarm.adtech.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Error implements Serializable {

    private String errorCode;

    private String message;

    @JsonCreator
    public Error(@JsonProperty("errorCode") String errorCode,
                 @JsonProperty("message") String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Error(ErrorCode errorCode, String message) {
        this.errorCode = errorCode.name();
        this.message = message;
    }
}
