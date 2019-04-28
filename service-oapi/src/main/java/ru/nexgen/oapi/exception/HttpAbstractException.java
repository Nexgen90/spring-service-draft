package ru.nexgen.oapi.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class HttpAbstractException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final String service;

    public HttpAbstractException(HttpStatus httpStatus, String errorMessage, String service) {
        super(errorMessage, null, true, false);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.service = service;
    }
}
