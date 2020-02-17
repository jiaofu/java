package com.jex.take.data.controller.cron.job;

import com.huobi.client.model.LastTradeAndBestQuote;
import com.shui.data.analysis.dao.bean.SymbolHuobiBean;
import com.shui.data.analysis.service.CacheMemory;
import com.shui.data.analysis.service.data.SourceMarket;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Service
@DisallowConcurrentExecution
public class PriceJob implements Job, Serializable {

    @Resource
    SourceMarket sourceMarket;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, requestOptions)
    }
}
