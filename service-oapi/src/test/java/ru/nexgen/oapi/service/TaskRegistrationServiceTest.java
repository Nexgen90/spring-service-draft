package ru.nexgen.oapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nexgen.oapi.configuration.properties.InternalRabbitProperties;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;
import ru.nexgen.oapi.model.dto.SrvTask;
import ru.nexgen.oapi.service.rabbit.InternalQueueService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TaskRegistrationServiceTest.Config.class)
class TaskRegistrationServiceTest {
    private static final String TITLE = "abc";
    private static final String TEXT = "def";
    private static final String ROUTING_KEY = "test_routing_key";

    @Autowired
    private InternalRabbitProperties internalRabbitProperties;

    @Autowired
    private InternalQueueService internalQueueService;

    @Autowired
    private TaskRegistrationService registrationService;


    @Test
    void shouldSendTaskToRabbitMq() {
        RqTaskRegistration request = new RqTaskRegistration(TITLE, TEXT);
        Long taskId = registrationService.createTask(request);
        assertThat("Получен некорректный taskId", taskId, isA(Long.class));

        ArgumentCaptor<SrvTask> srvTaskArgument = ArgumentCaptor.forClass(SrvTask.class);
        verify(internalQueueService).sendToSrv(any(String.class), srvTaskArgument.capture());
        assertThat(srvTaskArgument.getValue().getTitle(), equalTo(TITLE));
        assertThat(srvTaskArgument.getValue().getMessage(), equalTo(TEXT));
        assertThat(srvTaskArgument.getValue().getTaskId(), isA(Long.class));

        verify(internalQueueService, times(1)).sendToSrv(eq(ROUTING_KEY), any(SrvTask.class));

    }

    @Configuration
    static class Config {

        @MockBean
        InternalQueueService internalQueueService;

        @Bean
        TaskRegistrationService registrationService() {
            return new TaskRegistrationService(internalQueueService, internalRabbitProperties());
        }

        @Bean
        InternalRabbitProperties internalRabbitProperties() {
            InternalRabbitProperties internalRabbitProperties = new InternalRabbitProperties();
            internalRabbitProperties.setRoutingKey(ROUTING_KEY);
            return internalRabbitProperties;
        }
    }
}