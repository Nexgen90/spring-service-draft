package ru.nexgen.oapi.service.monitoring.timer;

import io.micrometer.core.instrument.Timer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public abstract class BaseTimer implements TimerMXBean {
    public static final double PERCENTILE_95 = 0.95;
    public static final long MILLIS_PER_SECOND = 1000L;

    protected Timer timer;

    public Timer getInstance() {
        return timer;
    }

    @Override
    public double getTotalTime() {
        return timer.totalTime(MILLISECONDS);
    }

    @Override
    public double getMean() {
        return timer.mean(MILLISECONDS);
    }

    @Override
    public double getMax() {
        return timer.max(MILLISECONDS);
    }

    @Override
    public long getCount() {
        return timer.count();
    }
}
