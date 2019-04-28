package ru.nexgen.srv.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ru.nexgen.srv.service.DeadlineNotificationService;

@Slf4j
@DisallowConcurrentExecution
public class DeadlineNotificationJob extends QuartzJobBean {

    @Autowired
    private DeadlineNotificationService deadlineNotificationService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //todo: поднятие из базы тасок, по которым пришло время нотификации
        deadlineNotificationService.notifyUser();
    }
}
