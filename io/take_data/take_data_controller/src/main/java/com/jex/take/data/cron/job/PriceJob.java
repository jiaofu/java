package com.jex.take.data.cron.job;

import com.jex.take.data.service.control.RequsetData;
import com.jex.take.data.service.control.SaveDbData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

@Slf4j
@Service
@DisallowConcurrentExecution
public class PriceJob  implements Job, Serializable {

    @Resource
    RequsetData requsetData;
    @Resource
    SaveDbData saveDbData;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            requsetData.getApiData();

            saveDbData.checkList();
        }catch (Exception ex){
          log.error("出现异常情况",ex);
        }


    }
}