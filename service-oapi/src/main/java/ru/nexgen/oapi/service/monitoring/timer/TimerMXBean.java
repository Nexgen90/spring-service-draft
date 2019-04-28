package ru.nexgen.oapi.service.monitoring.timer;

public interface TimerMXBean {

    double getTotalTime();

    double getMean();

    double getMax();

    long getCount();
}
