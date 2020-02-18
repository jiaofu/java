package com.jex.take.data.cron.job;

import com.jex.take.data.service.client.SyncRequestClient;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import com.jex.take.data.service.websocket.huobi.SubscriptionOptions;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;


@Slf4j
@Service
@DisallowConcurrentExecution
public class OkPriceJob implements Job, Serializable {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

/*        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setUrl(BaseUrl.okApi);
        SyncRequestClient syncRequestClient = SyncRequestClient.create(requestOptions);

        Map<String, TickerDTO> map =  syncRequestClient.getOktickers();

        for(Map.Entry<String, TickerDTO> entity :map.entrySet()){
            System.out.println("同步"+entity.getKey());
        }*/

/*        AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
        asyncRequestClient.getTickers(m->{
            if(m.succeeded()){
                for(Map.Entry<String, TickerDTO> entity :m.getData().entrySet()){
                    System.out.println("异步"+entity.getKey());
                }
            }
        });*/

        SubscriptionOptions subscriptionOptions =  new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.okSocket);
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);

        String symbol = "ETH-USDT,BTC-USDT";

        subscriptionClient.subscribeTickerOkEvent(symbol, (event) -> {
            System.out.println("--------------- Subscribe Candlestick ------------------");
            System.out.println(event.getSymbol());
        },e-> System.out.println(e.getMessage()));

        System.out.println("测试");


        try {
            Thread.sleep(1000000);
        }catch (Exception ex){

        }


    }
}
