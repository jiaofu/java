package com.jex.take.data.service.control;

import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.enums.ExchangeEnum;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.websocket.huobi.WebSocketConnection;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheMemory {

    public static Map<String,WebSocketConnection> socketMap = new ConcurrentHashMap<>();
    public static Map<String,AsyncRequestClient> apiMap = new ConcurrentHashMap<>();




    @PostConstruct
    public void init(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setUrl(BaseUrl.huobiApi);
        AsyncRequestClient   huobiApiClinet= AsyncRequestClient.create(requestOptions);


        requestOptions.setUrl(BaseUrl.okApi);
        AsyncRequestClient  okApiClient = AsyncRequestClient.create(requestOptions);

        requestOptions.setUrl(BaseUrl.binanceiApi);
        AsyncRequestClient  binanceApiClinet = AsyncRequestClient.create(requestOptions);

        apiMap.put(ExchangeEnum.huobiTicket.getDesc(),huobiApiClinet);
        apiMap.put(ExchangeEnum.okTicket.getDesc(),okApiClient);
        apiMap.put(ExchangeEnum.binanceTicket.getDesc(),binanceApiClinet);

    }

}
