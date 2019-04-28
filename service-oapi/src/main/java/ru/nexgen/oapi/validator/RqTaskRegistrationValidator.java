package ru.nexgen.oapi.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.nexgen.oapi.exception.MandatoryParametersMissingException;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;

@Component
public class RqTaskRegistrationValidator {
    private static final String SERVICE_NAME = "service-oapi";

    public void validate(RqTaskRegistration request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new MandatoryParametersMissingException(HttpStatus.BAD_REQUEST, "title", SERVICE_NAME);
        }

        if (request.getText() == null || request.getText().isEmpty()) {
            throw new MandatoryParametersMissingException(HttpStatus.BAD_REQUEST, "text", SERVICE_NAME);
        }
    }
}
