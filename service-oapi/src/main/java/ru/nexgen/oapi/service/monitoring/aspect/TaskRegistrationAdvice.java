package ru.nexgen.oapi.service.monitoring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nexgen.oapi.service.monitoring.timer.TaskRegistrationTimer;

@Slf4j
@Component
@Aspect
public class TaskRegistrationAdvice extends BaseTimerAdvise {
    @Autowired
    public TaskRegistrationAdvice(TaskRegistrationTimer timer) {
        this.timer = timer;
    }

    @Around("execution(* ru.nexgen.oapi.controller.TaskRegistrationController.registerTask(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return super.aroundProcess(pjp);
    }
}
