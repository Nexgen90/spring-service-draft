package ru.nexgen.oapi.service.monitoring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import ru.nexgen.oapi.service.monitoring.timer.BaseTimer;

import java.util.concurrent.atomic.AtomicReference;

public abstract class BaseTimerAdvise {
    protected BaseTimer timer;

    public Object aroundProcess(ProceedingJoinPoint pjp) throws Throwable {
        AtomicReference<Object> result = new AtomicReference<>();
        AtomicReference<Throwable> t = new AtomicReference<>();

        timer.getInstance().record(
                () -> {
                    try {
                        result.set(pjp.proceed());
                    } catch (Throwable throwable) {
                        t.set(throwable);
                    }
                    return result;
                }
        );

        if (t.get() != null) {
            throw t.get();
        }

        return result.get();
    }
}
