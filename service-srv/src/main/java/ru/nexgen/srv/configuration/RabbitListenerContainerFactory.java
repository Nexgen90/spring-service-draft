package ru.nexgen.srv.configuration;

import lombok.Data;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

@Data
public class RabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {
    private Integer declarationRetries;

    @Override
    protected void initializeContainer(SimpleMessageListenerContainer instance, RabbitListenerEndpoint endpoint) {
        super.initializeContainer(instance, endpoint);

        if (this.declarationRetries != null) {
            instance.setDeclarationRetries(this.declarationRetries);
        }
    }
}
