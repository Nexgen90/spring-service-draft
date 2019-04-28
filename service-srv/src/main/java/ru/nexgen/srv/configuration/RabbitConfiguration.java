package ru.nexgen.srv.configuration;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;
import ru.nexgen.srv.configuration.properties.InternalRabbitProperties;

@Configuration
public class RabbitConfiguration {

    private final FatalExceptionStrategy fatalExceptionStrategy;
    private final InternalRabbitProperties properties;

    @Autowired
    public RabbitConfiguration(FatalExceptionStrategy fatalExceptionStrategy,
                               InternalRabbitProperties properties) {
        this.fatalExceptionStrategy = fatalExceptionStrategy;
        this.properties = properties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(properties.getHost(), properties.getPort());
        connectionFactory.setUsername(properties.getUsername());
        connectionFactory.setPassword(properties.getPassword());
        connectionFactory.setVirtualHost(properties.getVirtualHost());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * Bean for reading messages
     * is used in @RabbitListener annotation.
     */
    @Bean
    public SimpleRabbitListenerContainerFactory cciSimpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                                        MessageConverter messageConverter) {
        RabbitListenerContainerFactory factory = new RabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setErrorHandler(errorHandler());
        factory.setPrefetchCount(properties.getPrefetchCount());
        factory.setFailedDeclarationRetryInterval(properties.getFailedDeclarationRetryInterval());
        factory.setDeclarationRetries(properties.getDeclarationRetries());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(fatalExceptionStrategy);
    }
}
