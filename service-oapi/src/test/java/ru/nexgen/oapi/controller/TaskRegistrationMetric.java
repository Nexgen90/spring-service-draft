package ru.nexgen.oapi.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nexgen.oapi.exception.MandatoryParametersMissingException;
import ru.nexgen.oapi.exception.handler.HttpExceptionHandler;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;
import ru.nexgen.oapi.service.TaskRegistrationService;
import ru.nexgen.oapi.service.monitoring.aspect.TaskRegistrationAdvice;
import ru.nexgen.oapi.service.monitoring.timer.TaskRegistrationTimer;
import ru.nexgen.oapi.service.rabbit.InternalQueueService;
import ru.nexgen.oapi.validator.RqTaskRegistrationValidator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskRegistrationMetric.Config.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRegistrationMetric {
    private static final Long EXPECTED_INCREMENTED_VALUE = 1L;
    private static final Long EXPECTED_NOT_INCREMENTED_VALUE = 0L;
    private static final long SOME_LONG = 666L;

    @Autowired
    TaskRegistrationTimer taskRegistrationTimer;

    @Autowired
    TaskRegistrationService taskRegistrationService;

    @Autowired
    private TaskRegistrationController taskRegistrationController;

    @Test
    void shouldIncrementMetric() {
        when(taskRegistrationService.createTask(any()))
                .thenReturn(SOME_LONG);
        taskRegistrationController.registerTask(new RqTaskRegistration("title", "message"));

        assertThat("Не произошло ожидаемое увеличение счётчика",
                taskRegistrationTimer.getCount(), equalTo(EXPECTED_INCREMENTED_VALUE));
    }

    @Test
    void shouldNotIncrementMetric() {
        assertThrows(MandatoryParametersMissingException.class,
                () -> taskRegistrationController.registerTask(new RqTaskRegistration("title", "")));
        assertThat("Произошло незапланированное увеличение счётчика",
                taskRegistrationTimer.getCount(), equalTo(EXPECTED_NOT_INCREMENTED_VALUE));
    }


    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    static class Config {

        @Bean
        TaskRegistrationController taskRegistrationController() {
            return new TaskRegistrationController(taskRegistrationService, taskRegistrationValidator());
        }

        @MockBean
        TaskRegistrationService taskRegistrationService;

        @Bean
        RqTaskRegistrationValidator taskRegistrationValidator() {
            return new RqTaskRegistrationValidator();
        }

        @Bean
        HttpExceptionHandler httpExceptionHandler() {
            return new HttpExceptionHandler();
        }

        @Bean
        TaskRegistrationAdvice advice() {
            return new TaskRegistrationAdvice(taskRegistrationTimer());
        }

        @Bean
        TaskRegistrationTimer taskRegistrationTimer() {
            return new TaskRegistrationTimer(registry());
        }

        @Bean
        MeterRegistry registry() {
            return new SimpleMeterRegistry();
        }

    }
}
