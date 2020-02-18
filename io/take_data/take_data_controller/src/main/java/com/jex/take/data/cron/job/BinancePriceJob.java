package com.jex.take.data.cron.job;

import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import com.jex.take.data.service.websocket.huobi.SubscriptionOptions;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@DisallowConcurrentExecution
public class BinancePriceJob implements Job, Serializable {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
/*
        SyncRequestClient syncRequestClient = SyncRequestClient.create();

        Map<String, TickerDTO> map =  syncRequestClient.getTickers();

        for(Map.Entry<String, TickerDTO> entity :map.entrySet()){
            System.out.println("同步"+entity.getKey());
        }

        AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
        asyncRequestClient.getTickers(m->{
            if(m.succeeded()){
                for(Map.Entry<String, TickerDTO> entity :m.getData().entrySet()){
                    System.out.println("异步"+entity.getKey());
                }
            }
        });*/

        SubscriptionOptions subscriptionOptions =  new SubscriptionOptions();

        List<String> list = new ArrayList<>();
        list.add("BTCUSDT");
        list.add("ETHUSDT");
        subscriptionOptions.setTagWs(list);
        subscriptionOptions.setUri(BaseUrl.binanceSocket);
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);



        subscriptionClient.subscribeTickerBinanceEvent((event) -> {
            System.out.println(event.getSymbol());
        },e-> System.out.println(e.getMessage()));

        System.out.println("测试");


        try {
            Thread.sleep(1000000);
        }catch (Exception ex){

        }


    }
}
