package ru.nexgen.oapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nexgen.oapi.configuration.properties.InternalRabbitProperties;
import ru.nexgen.oapi.model.dto.RqTaskRegistration;
import ru.nexgen.oapi.model.dto.SrvTask;
import ru.nexgen.oapi.service.rabbit.InternalQueueService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskRegistrationService {
    private final InternalQueueService internalQueueService;
    private final InternalRabbitProperties properties;
    private String routingKey;

    @PostConstruct
    public void init() {
        routingKey = properties.getRoutingKey();
    }

    public Long createTask(RqTaskRegistration request) {
        Long taskId = 1L; //todo: save to db and get taskId;

        SrvTask task = new SrvTask(taskId, request.getTitle(), request.getText());
        internalQueueService.sendToSrv(routingKey, task);
        log.info("New task register with id: {}", taskId);
        return taskId;
    }
}
