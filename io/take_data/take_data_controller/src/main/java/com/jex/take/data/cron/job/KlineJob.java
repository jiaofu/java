package com.jex.take.data.cron.job;

import com.jex.take.data.service.control.RequestKlineData;
import com.jex.take.data.service.control.RequsetTicketData;
import com.jex.take.data.service.control.SaveKlineData;
import com.jex.take.data.service.control.SaveTicketData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

@Slf4j
@Service
public class KlineJob  implements Job, Serializable {
    @Resource
    RequestKlineData requestKlineData;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            requestKlineData.getData();

        }catch (Exception ex){
            log.error("出现异常情况",ex);
        }


    }

}
