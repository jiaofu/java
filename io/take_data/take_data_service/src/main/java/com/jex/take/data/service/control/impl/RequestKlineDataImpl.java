package com.jex.take.data.service.control.impl;

import com.jex.take.data.service.control.CacheMemory;
import com.jex.take.data.service.control.RequestKlineData;
import com.jex.take.data.service.control.Symbol;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.enums.ExchangeEnum;
import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.model.event.CandlestickEvent;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.Channels;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import com.jex.take.data.service.websocket.huobi.SubscriptionOptions;
import com.jex.take.data.service.websocket.huobi.WebSocketConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestKlineDataImpl implements RequestKlineData {

    private final static long interval = 60 * 1000;
    private final  static String taskName = "kline";

    @Override
    public void getData() {
        webSocketIsWork(ExchangeEnum.binanceTicket.getDesc());
        webSocketIsWork(ExchangeEnum.huobiTicket.getDesc());
        webSocketIsWork(ExchangeEnum.okTicket.getDesc());
    }

    private Boolean webSocketIsWork(String from) {
        WebSocketConnection webSocketConnection = CacheMemory.socketMap.get(from+"_"+taskName);
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
            huobiKlineSocket();
        } else if (from.equals(ExchangeEnum.okTicket.getDesc())) {
            okKlineSocket();
        } else if (from.equals(ExchangeEnum.binanceTicket.getDesc())) {
            binanceKlineSocket();
        }
    }

    private void huobiKlineSocket() {
        String from = ExchangeEnum.huobiTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.huobiSocket);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("kline");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        String symbols = String.join(",", Symbol.getSymbols(from));
        subscriptionClient.subscribeCandlestickHuobiEvent(symbols, CandlestickInterval.MIN1, (CandlestickEvent) ->
                        handleKline(from, CandlestickEvent)
                , wor -> handleError(from, wor));
    }

    private void okKlineSocket() {
        String from = ExchangeEnum.okTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.okSocket);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("kline");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        String symbols = String.join(",", Symbol.getSymbols(from));
        subscriptionClient.subscribeCandlestickOKEvent(symbols, (ticket) ->
                        handleKline(from, ticket)
                , wor -> handleError(from, wor));
    }

    private void binanceKlineSocket() {
        String from = ExchangeEnum.binanceTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        String wsUrl = Channels.klineBinanceChannel(Symbol.getSymbols(from));
        subscriptionOptions.setUri(wsUrl);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("kline");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        subscriptionClient.subscribeCandlestickBinanceEvent((ticket) ->
                        handleKline(from, ticket)
                , wor -> handleError(from, wor));
    }
    private void handleKline(String from, CandlestickEvent candlestickEvent){
        log.info(" 来自" + from + "的,请求,symbol:" + candlestickEvent.getSymbol() + "  时间" + candlestickEvent.getTimestamp());

    }

    private void handleError(String from, ApiException wor) {
        log.info(" 来自的" + from + "webSocket出现错误:" + wor.getMessage());
    }
}
