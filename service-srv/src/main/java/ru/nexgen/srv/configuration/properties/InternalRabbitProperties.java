package ru.nexgen.srv.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties("amqp.internal")
public class InternalRabbitProperties {

    @NotNull
    private String host;

    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String virtualHost;

    @NotNull
    private String queue;

    @NotNull
    private String exchange;

    @NotNull
    private Integer consumerCount;

    @NotNull
    private Integer declarationRetries;

    @NotNull
    private Long failedDeclarationRetryInterval;

    @NotNull
    private Integer prefetchCount;
}
