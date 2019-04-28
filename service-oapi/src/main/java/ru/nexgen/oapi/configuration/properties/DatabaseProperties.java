package ru.nexgen.oapi.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties("db.postgres")
public class DatabaseProperties {

    private String driverClassName = "org.postgresql.Driver";

    @NotNull
    private String url;

    @NotNull
    private String user;

    @NotNull
    private String password;

    @NotNull
    private Integer initialSize;

    @NotNull
    private Integer maxActive;

    @NotNull
    private Integer minIdle;

    @NotNull
    private Integer maxIdle;

    private String jdbcInterceptors = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";
}
