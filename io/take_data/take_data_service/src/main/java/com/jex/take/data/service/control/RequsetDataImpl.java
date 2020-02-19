package com.jex.take.data.service.control;


import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.enums.ExchangeEnum;
import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import com.jex.take.data.service.websocket.huobi.SubscriptionOptions;
import com.jex.take.data.service.websocket.huobi.WebSocketConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RequsetDataImpl implements RequsetData {

    private final static String socketType = "websocket";
    private final static String apiType = "api";
    private final static long interval = 60 * 1000;

    @Override
    public void getApiData() {
        huobiApi();
        okApi();
        binanceApi();
    }


    private void huobiApi(){
        String from = ExchangeEnum.huobiTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if(work){
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getHuobiTickers(callBack ->filteSymbol(callBack,from));

    }

    private void okApi(){

        String from = ExchangeEnum.okTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if(work){
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getOkTickers(callBack ->filteSymbol(callBack,from));
    }

    private void binanceApi(){
        String from = ExchangeEnum.binanceTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if(work){
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getHuobiTickers(callBack ->filteSymbol(callBack,from));
    }
    private void filteSymbol(AsyncResult<Map<String, TickerDTO>> callback,String from ){

        if(!callback.succeeded()){
            log.info("请求api出现异常"+callback.getException());
            return;
        }
        List<String> symbols = Symbol.getSymbols(ExchangeEnum.huobiTicket.getDesc());
        Map<String, TickerDTO> map =  callback.getData();
        for(String name :symbols ){
            TickerDTO tickerDTO = map.get(name);
            if(tickerDTO != null){
                handleTicket(from,apiType,tickerDTO);
            }
        }

    }

    private Boolean webSocketIsWork(String from){
        WebSocketConnection webSocketConnection = CacheMemory.socketMap.get(from);
        long now = System.currentTimeMillis();
        if (webSocketConnection != null && webSocketConnection.getState() == ConnectionState.CONNECTED && (webSocketConnection.getLastReceivedTime() + interval) > now) {
            return true;
        }
        if(webSocketConnection == null){
            startWebSocket(from);

        }
        return false;

    }


    public void startWebSocket(String from) {
        if (from.equals(ExchangeEnum.huobiTicket.getDesc())) {
            huobiSocket();
        } else if (from.equals(ExchangeEnum.okTicket.getDesc())) {
            okWebSocket();
        } else if (from.equals(ExchangeEnum.binanceTicket.getDesc())) {
            binanceSocket();
        }
    }


    @Override
    public void getWebSocketData() {
        huobiSocket();
        okWebSocket();
        binanceSocket();
    }

    private void huobiSocket() {
        String from = ExchangeEnum.huobiTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.huobiApi);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("ticket");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        String symbols = String.join(",", Symbol.getSymbols(from));
        subscriptionClient.subscribeTickerHuobiEvent(symbols, CandlestickInterval.MIN1, (ticket) ->
                        handleTicket(from, socketType, ticket)
                , wor -> handleError(from, wor));
    }

    private void okWebSocket() {
        String from = ExchangeEnum.okTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.okSocket);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("ticket");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        String symbols = String.join(",", Symbol.getSymbols(from));
        subscriptionClient.subscribeTickerOkEvent(symbols, (ticket) ->
                        handleTicket(from, socketType, ticket)
                , wor -> handleError(from, wor));
    }

    private void binanceSocket() {
        String from = ExchangeEnum.binanceTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.binanceSocket);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("ticket");
        subscriptionOptions.setTagWs(Symbol.getSymbols(from));
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        subscriptionClient.subscribeTickerBinanceEvent((ticket) ->
                        handleTicket(from, socketType, ticket)
                , wor -> handleError(from, wor));
    }

    private void handleTicket(String from, String type, TickerDTO ticket) {
        log.info(" 来自" + from + "的" + type + ",请求,symbol:" + ticket.getClose() + "时间" + ticket.getClose());
    }

    private void handleError(String from, ApiException wor) {
        log.info(" 来自的" + from + "webSocket出现错误:" + wor.getMessage());
    }
}
