package ru.nexgen.srv.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties("job")
public class SchedulingProperties {
    private DeadlineTaskProperties deadline;

    @Data
    public static class DeadlineTaskProperties {
        @NotNull
        private String cron;
    }
}
