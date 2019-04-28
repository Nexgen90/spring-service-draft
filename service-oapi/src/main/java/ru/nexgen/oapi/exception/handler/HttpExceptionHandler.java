package ru.nexgen.oapi.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nexgen.oapi.exception.MandatoryParametersMissingException;
import ru.nexgen.oapi.model.dto.error.ErrorDescription;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler {
    private static final String SERVICE_NAME = "service-oapi";

    @ExceptionHandler(MandatoryParametersMissingException.class)
    public ResponseEntity jsonBodyValidationExceptionHandler(Exception e, HttpServletRequest request) {
        log.error("Required parameter is missing");
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setErrorMessage(e.getMessage() + ", url: " + getUrl(request));
        errorDescription.setService(SERVICE_NAME);
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    private String getUrl(HttpServletRequest request) {
        return ((String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"));
    }
}
