package com.jex.take.data.cron.listener;


import com.jex.take.data.cron.job.HuobiPriceJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Service
public class HuobiPriceHandleThread {

    @Autowired
    private Scheduler scheduler;

   //@PostConstruct
    public void init() throws Exception {

        JobDetail jobDetail = JobBuilder
                .newJob(HuobiPriceJob.class)
                .withIdentity("priceRebate", "rebate")
                .build();

        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("priceRebate", "rebate")
                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
