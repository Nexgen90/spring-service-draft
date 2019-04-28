package ru.nexgen.oapi.service.monitoring.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ManagedResource(objectName="mon.task:name=registration")
public class TaskRegistrationTimer extends BaseTimer {

    @Autowired
    public TaskRegistrationTimer(MeterRegistry registry) {
        this.timer = Timer.builder("RegistrationTimer")
                .publishPercentiles(PERCENTILE_95) // median and 95th percentile
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(MILLIS_PER_SECOND))
                .register(registry);
    }
}
