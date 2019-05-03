package ru.nexgen.oapi.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nexgen.oapi.exception.MandatoryParametersMissingException;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

class RqTaskRegistrationValidatorTest {
    private static final String EXPECTED_ERROR_MESSAGE = "Отсутствует обязательный параметр: <%s>";
    private RqTaskRegistrationValidator validator = new RqTaskRegistrationValidator();

    @ParameterizedTest
    @MethodSource("getCorrectBodyParams")
    void shouldValidate(String title, String text) {
        RqTaskRegistration request = new RqTaskRegistration(title, text);
        validator.validate(request);
    }

    @ParameterizedTest
    @MethodSource("getBodyParamsAndExpectedErrorMsg")
    void shouldNotValidate(String title, String text, String expectedErrorMsg) {
        RqTaskRegistration request = new RqTaskRegistration(title, text);

        MandatoryParametersMissingException exception = assertThrows(MandatoryParametersMissingException.class,
                () -> validator.validate(request));

        assertThat("Текст ошибки отличается от ожидаемого", exception.getMessage(), equalTo(expectedErrorMsg));
    }

    private static Stream<Arguments> getCorrectBodyParams() {
        return Stream.of(
                of("text", "text"),
                of("fasdfasfsa", "sdfasdfasfasfffasf"),
                of("1234", "2135"),
                of("??", "./(*("),
                of("one two three", "text text text")
        );
    }

    private static Stream<Arguments> getBodyParamsAndExpectedErrorMsg() {
        return Stream.of(
                of("", "text", String.format(EXPECTED_ERROR_MESSAGE, "title")),
                of(null, "text", String.format(EXPECTED_ERROR_MESSAGE, "title")),
                of("title", "", String.format(EXPECTED_ERROR_MESSAGE, "text")),
                of("title", null, String.format(EXPECTED_ERROR_MESSAGE, "text")),
                of("", "", String.format(EXPECTED_ERROR_MESSAGE, "title")),
                of(null, "", String.format(EXPECTED_ERROR_MESSAGE, "title")),
                of("", null, String.format(EXPECTED_ERROR_MESSAGE, "title"))
        );
    }
}