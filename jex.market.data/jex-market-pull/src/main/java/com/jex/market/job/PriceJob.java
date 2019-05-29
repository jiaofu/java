package com.jex.market.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class PriceJob  implements Job {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println(new Date() +"xxxxxxxxxxx");
    }
}
