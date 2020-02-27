package com.jex.take.data.cron.listener;

import com.jex.take.data.cron.job.PriceJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Service
public class KlineHandleThread {
    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws Exception {

        JobDetail jobDetail = JobBuilder
                .newJob(PriceJob.class)
                .withIdentity("priceRebate", "rebate")
                .build();

        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("priceRebate", "rebate")
                .withSchedule(simpleSchedule().withIntervalInSeconds(30).repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
