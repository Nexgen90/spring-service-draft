package ru.nexgen.oapi.service.rabbit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.nexgen.oapi.configuration.properties.InternalRabbitProperties;
import ru.nexgen.oapi.model.dto.SrvTask;

@Slf4j
@Service
@AllArgsConstructor
public class InternalQueueService {
    private final InternalRabbitProperties properties;
    private final RabbitTemplate rabbitTemplate;

    public void sendToSrv(String routingKey, SrvTask srvTask) {
        log.debug("Sent SrvTask = {}, with routing key = {}", srvTask, routingKey);
        rabbitTemplate.convertAndSend(properties.getExchange(), routingKey, srvTask);
    }
}
