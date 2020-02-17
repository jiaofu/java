package com.jex.take.data.cron.job;

import com.jex.take.data.service.client.SyncRequestClient;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.RequestOptions;
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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setUrl(BaseUrl.okApi);
        SyncRequestClient syncRequestClient = SyncRequestClient.create(requestOptions);

        Map<String, TickerDTO> map =  syncRequestClient.getOktickers();

        for(Map.Entry<String, TickerDTO> entity :map.entrySet()){
            System.out.println("同步"+entity.getKey());
        }

/*        AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
        asyncRequestClient.getTickers(m->{
            if(m.succeeded()){
                for(Map.Entry<String, TickerDTO> entity :m.getData().entrySet()){
                    System.out.println("异步"+entity.getKey());
                }
            }
        });*/

/*        SubscriptionClient subscriptionClient = SubscriptionClient.create();
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
        });*/

        System.out.println("测试");


        try {
            Thread.sleep(1000000);
        }catch (Exception ex){

        }


    }
}
