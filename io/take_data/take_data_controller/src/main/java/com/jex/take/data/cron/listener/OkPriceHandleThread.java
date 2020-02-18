package com.jex.take.data.cron.listener;

import com.jex.take.data.cron.job.HuobiPriceJob;
import com.jex.take.data.cron.job.OkPriceJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
@Service
public class OkPriceHandleThread {
    @Autowired
    private Scheduler scheduler;

    //@PostConstruct
    public void init() throws Exception {

        JobDetail jobDetail = JobBuilder
                .newJob(OkPriceJob.class)
                .withIdentity("priceOkRebate", "rebate")
                .build();

        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("priceOkRebate", "rebate")
                .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
