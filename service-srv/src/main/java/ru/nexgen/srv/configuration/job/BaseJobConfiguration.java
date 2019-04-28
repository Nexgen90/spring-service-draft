package ru.nexgen.srv.configuration.job;

import org.quartz.Job;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

abstract class BaseJobConfiguration {

    protected JobDetailFactoryBean jobDetail(String name, Class<? extends Job> jobClass) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(jobClass);
        jobDetailFactoryBean.setName(name);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }
}
