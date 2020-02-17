package com.jex.take.data.cron.job;


import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service
@DisallowConcurrentExecution
public class HuobiPriceJob implements Job, Serializable {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
/*
        SyncRequestClient syncRequestClient = SyncRequestClient.create();

        Map<String, TradeStatistics> map =  syncRequestClient.getTickers();

        for(Map.Entry<String, TradeStatistics> entity :map.entrySet()){
            System.out.println("同步"+entity.getKey());
        }

        AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
        asyncRequestClient.getTickers(m->{
            if(m.succeeded()){
                for(Map.Entry<String, TradeStatistics> entity :m.getData().entrySet()){
                    System.out.println("异步"+entity.getKey());
                }
            }
        });*/

        SubscriptionClient subscriptionClient = SubscriptionClient.create();
        String symbol = "btcusdt";
        subscriptionClient.subscribeCandlestickEvent(symbol, CandlestickInterval.MIN15, (candlestickEvent) -> {
            System.out.println("--------------- Subscribe Candlestick ------------------");
            System.out.println("id: " + candlestickEvent.getData().getId());
            System.out.println("Timestamp: " + candlestickEvent.getData().getTimestamp());
            System.out.println("High: " + candlestickEvent.getData().getHigh());
            System.out.println("Low: " + candlestickEvent.getData().getLow());
            System.out.println("Open: " + candlestickEvent.getData().getOpen());
            System.out.println("Close: " + candlestickEvent.getData().getClose());
            System.out.println("Volume: " + candlestickEvent.getData().getVolume());
        });

        System.out.println("测试");


        try {
            Thread.sleep(1000000);
        }catch (Exception ex){

        }


    }
}
