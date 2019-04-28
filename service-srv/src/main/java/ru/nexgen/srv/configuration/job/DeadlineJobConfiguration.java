package ru.nexgen.srv.configuration.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import ru.nexgen.srv.configuration.properties.SchedulingProperties;
import ru.nexgen.srv.configuration.properties.SchedulingProperties.DeadlineTaskProperties;
import ru.nexgen.srv.job.DeadlineNotificationJob;

@Configuration
public class DeadlineJobConfiguration extends BaseJobConfiguration {

    private static final String DEADLINE_NOTIFICATION_JOB_TRIGGER = "Deadline_notification_job_trigger";
    private static final String DEADLINE_NOTIFICATION_JOB_DETAIL = "Deadline_notification_job_detail";

    private final DeadlineTaskProperties properties;

    @Autowired
    public DeadlineJobConfiguration(SchedulingProperties schedulingProperties) {
        this.properties = schedulingProperties.getDeadline();
    }

    @Bean
    public JobDetailFactoryBean deadlineNotificationJobDetail() {
        return jobDetail(DEADLINE_NOTIFICATION_JOB_DETAIL, DeadlineNotificationJob.class);
    }

    @Bean
    public Trigger deadlineNotificationJobTrigger(JobDetail deadlineNotificationJobDetail) {
        return TriggerBuilder.newTrigger().forJob(deadlineNotificationJobDetail)
                .withIdentity(DEADLINE_NOTIFICATION_JOB_TRIGGER)
                .withSchedule(CronScheduleBuilder.cronSchedule(properties.getCron()))
                .build();
    }

}
