package com.jex.take.data.service.control.impl;


import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.control.CacheMemory;
import com.jex.take.data.service.control.RequsetData;
import com.jex.take.data.service.control.SaveTicketData;
import com.jex.take.data.service.control.Symbol;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.enums.ExchangeEnum;
import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.model.event.CandlestickEvent;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.Channels;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.websocket.huobi.SubscriptionClient;
import com.jex.take.data.service.websocket.huobi.SubscriptionOptions;
import com.jex.take.data.service.websocket.huobi.WebSocketConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RequsetDataImpl implements RequsetData {

    private final static String socketType = "websocket";
    private final static String apiType = "api";
    private final static long interval = 60 * 1000;

    @Resource
    SaveTicketData saveTicketData;

    @Override
    public void getApiData() {
        huobiApi();
        okApi();
        binanceApi();
    }


    private void huobiApi() {
        String from = ExchangeEnum.huobiTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if (work) {
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getHuobiTickers(callBack -> filteSymbol(callBack, from));

    }

    private void okApi() {

        String from = ExchangeEnum.okTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if (work) {
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getOkTickers(callBack -> filteSymbol(callBack, from));
    }

    private void binanceApi() {
        String from = ExchangeEnum.binanceTicket.getDesc();
        Boolean work = webSocketIsWork(from);

        if (work) {
            return;
        }
        AsyncRequestClient asyncRequestClient = CacheMemory.apiMap.get(from);
        asyncRequestClient.getBinanceTickers(callBack -> filteSymbol(callBack, from));
    }

    private void filteSymbol(AsyncResult<Map<String, TickerDTO>> callback, String from) {

        if (!callback.succeeded()) {
            log.info("请求api出现异常" + callback.getException());
            return;
        }
        List<String> symbols = Symbol.getSymbols(from);
        Map<String, TickerDTO> map = callback.getData();
        for (String name : symbols) {
            TickerDTO tickerDTO = map.get(name);
            if (tickerDTO != null) {
                handleTicket(from, apiType, tickerDTO);
            }
        }

    }

    private Boolean webSocketIsWork(String from) {
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
            huobiTicketSocket();
        } else if (from.equals(ExchangeEnum.okTicket.getDesc())) {
            okTicketSocket();
        } else if (from.equals(ExchangeEnum.binanceTicket.getDesc())) {
            binanceTicketSocket();
        }
    }


    @Override
    public void getTicketSocketData() {
        huobiTicketSocket();
        okTicketSocket();
        binanceTicketSocket();
    }

    @Override
    public void getKlineSocketData() {
        huobiKlineSocket();
        okKlineSocket();
        binanceKlineSocket();
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

    private void huobiTicketSocket() {
        String from = ExchangeEnum.huobiTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BaseUrl.huobiSocket);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("ticket");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        String symbols = String.join(",", Symbol.getSymbols(from));
        subscriptionClient.subscribeTickerHuobiEvent(symbols, CandlestickInterval.MIN1, (ticket) ->
                        handleTicket(from, socketType, ticket)
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

    private void okTicketSocket() {
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
    private void binanceKlineSocket() {
        String from = ExchangeEnum.binanceTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        String wsUrl = Channels.tickerOkChannel(Symbol.getSymbols(from));
        subscriptionOptions.setUri(wsUrl);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("kline");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        subscriptionClient.subscribeCandlestickBinanceEvent((ticket) ->
                        handleKline(from, ticket)
                , wor -> handleError(from, wor));
    }

    private void binanceTicketSocket() {
        String from = ExchangeEnum.binanceTicket.getDesc();
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        String wsUrl = Channels.tickerOkChannel(Symbol.getSymbols(from));
        subscriptionOptions.setUri(wsUrl);
        subscriptionOptions.setFromExchangeName(from);
        subscriptionOptions.setFromTaskName("ticket");
        SubscriptionClient subscriptionClient = SubscriptionClient.create(subscriptionOptions);
        subscriptionClient.subscribeTickerBinanceEvent((ticket) ->
                        handleTicket(from, socketType, ticket)
                , wor -> handleError(from, wor));
    }

    private void handleTicket(String from, String type, TickerDTO ticket) {
        log.info(" 来自" + from + "的" + type + ",请求,symbol:" + ticket.getSymbol() + "  时间" + ticket.getClose());
        saveTicketData.insertList(ticket);
    }
    private void handleKline(String from, CandlestickEvent candlestickEvent){
        log.info(" 来自" + from + "的,请求,symbol:" + candlestickEvent.getSymbol() + "  时间" + candlestickEvent.getTimestamp());

    }

    private void handleError(String from, ApiException wor) {
        log.info(" 来自的" + from + "webSocket出现错误:" + wor.getMessage());
    }
}
