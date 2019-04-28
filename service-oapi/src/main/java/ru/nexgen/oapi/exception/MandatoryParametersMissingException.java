package ru.nexgen.oapi.exception;

import org.springframework.http.HttpStatus;

public class MandatoryParametersMissingException extends HttpAbstractException {
    private static final String ERROR_MESSAGE = "Отсутствует обязательный параметр: <%s>";
    private String message;

    public MandatoryParametersMissingException(HttpStatus httpStatus, String parameterName, String service) {
        super(httpStatus, String.format(ERROR_MESSAGE, parameterName), service);
        message = String.format(ERROR_MESSAGE, parameterName);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
