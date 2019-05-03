package ru.nexgen.oapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.nexgen.oapi.exception.handler.HttpExceptionHandler;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;
import ru.nexgen.oapi.service.TaskRegistrationService;
import ru.nexgen.oapi.validator.RqTaskRegistrationValidator;

import java.net.URI;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskRegistrationController.class)
@ContextConfiguration(classes = TaskRegistrationControllerTest.Config.class)
class TaskRegistrationControllerTest {
    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer();
    private static final long SOME_LONG = 63262L;
    private static final String TITLE = "valid title";
    private static final String TEXT = "valid text";
    private static final String EXPECTED_SERVICE_NAME = "service-oapi";
    private static final String EXPECTED_ERROR_DESCRIPTION = "Отсутствует обязательный параметр: <%s>, url: /task/register";
    private HttpHeaders httpHeaders;
    private RqTaskRegistration request;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRegistrationController controller;

    @Autowired
    private TaskRegistrationService registrationService;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        when(registrationService.createTask(any()))
                .thenReturn(SOME_LONG);
    }

    @Test
    void shouldRegisterNewTask() throws Exception {
        request = new RqTaskRegistration(TITLE, TEXT);

        mvc.perform(post(URI.create("/task/register"))
                .content(serializeToJson(request))
                .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId", is(Matchers.any(Number.class))));

        verify(registrationService, times(1)).createTask(any());
    }

    @ParameterizedTest
    @MethodSource("getBodyParamsAndExpectedErrorMsg")
    void shouldReturnErrorMsg(String title, String text, String expectedErrorMsg) throws Exception {
        request = new RqTaskRegistration(title, text);

        mvc.perform(post(URI.create("/task/register"))
                .content(serializeToJson(request))
                .headers(httpHeaders))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage", is(expectedErrorMsg)))
                .andExpect(jsonPath("$.service", is(EXPECTED_SERVICE_NAME)));

        verify(registrationService, times(0)).createTask(any());
    }

    private static Stream<Arguments> getBodyParamsAndExpectedErrorMsg() {
        return Stream.of(
                of("", TEXT, String.format(EXPECTED_ERROR_DESCRIPTION, "title")),
                of(TITLE, "", String.format(EXPECTED_ERROR_DESCRIPTION, "text"))
        );
    }

    private static String serializeToJson(Object obj) throws JsonProcessingException {
        return OBJECT_WRITER.writeValueAsString(obj);
    }

    @Configuration
    static class Config {

        @MockBean
        TaskRegistrationService registrationService;

        @Bean
        TaskRegistrationController controller() {
            return new TaskRegistrationController(registrationService, validator());
        }

        @Bean
        HttpExceptionHandler httpExceptionHandler() {
            return new HttpExceptionHandler();
        }

        @Bean
        RqTaskRegistrationValidator validator() {
            return new RqTaskRegistrationValidator();
        }
    }
}