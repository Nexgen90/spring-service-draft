package ru.nexgen.srv.amqp.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.nexgen.srv.model.dto.SrvTask;
import ru.nexgen.srv.service.TaskProcessor;

@Slf4j
@Component
@AllArgsConstructor
public class InternalMessagesConsumer {

    private TaskProcessor taskProcessor;

    @RabbitListener(
            queues = {"${amqp.internal.queue}"},
            concurrency = "${amqp.internal.consumerCount}",
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void receivedMessage(SrvTask task) {
        taskProcessor.process(task);
    }
}
