package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.control.CacheMemory;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.enums.ExchangeEnum;
import com.jex.take.data.service.model.event.CandlestickEvent;
import com.jex.take.data.service.model.event.CandlestickReqEvent;
import com.jex.take.data.service.util.BaseUrl;
import com.jex.take.data.service.util.SubscriptionErrorHandler;
import com.jex.take.data.service.util.SubscriptionListener;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketStreamClientImpl implements SubscriptionClient {

    private final SubscriptionOptions options;
    private WebSocketWatchDog watchDog;

    private final WebsocketRequestImpl requestImpl;



    public WebSocketStreamClientImpl(SubscriptionOptions options) {

        this.watchDog = null;
        this.options = Objects.requireNonNull(options);

        this.requestImpl = new WebsocketRequestImpl();
    }

    private <T> void createConnection(WebsocketRequest<T> request, boolean autoClose) {
        if (watchDog == null) {
            watchDog = new WebSocketWatchDog(options);
        }

        String key = null;
        WebSocketConnection connection = null;
        String websocketUrl = options.getUri().toLowerCase();
        if (websocketUrl.contains(BaseUrl.okSocket.toLowerCase())) {
            connection = new OkWebSocketConnection(
                    options, request, watchDog, autoClose);
            key = ExchangeEnum.okTicket.getDesc();
        } else if (websocketUrl.contains(BaseUrl.binanceSocket.toLowerCase())) {

            // 组合查询 <symbol>@miniTicker
            StringBuilder wdString = new StringBuilder(BaseUrl.binanceSocket);
            wdString.append("/stream?streams=");
            for (String str : options.getTagWs()) {
                wdString.append(str.toLowerCase());
                wdString.append("@miniTicker");
                wdString.append("/");
            }
            if (options.getTagWs().size() > 0) {
                if (wdString.length() > 0) {
                    wdString.deleteCharAt(wdString.length() - 1);
                }
            }
            key = ExchangeEnum.binanceTicket.getDesc();
            String streamingUrl = wdString.toString();


            log.info("币安发送的websocket 链接:" + streamingUrl);
            options.setUri(streamingUrl);
            connection = new BinanceWebSocketConnection(
                    options, request, watchDog, autoClose);
        } else {
            key = ExchangeEnum.huobiTicket.getDesc();
            connection = new HuobiWebSocketConnection(
                    options, request, watchDog, autoClose);
        }

        if (autoClose == false) {
           WebSocketConnection webSocketConnectionList = CacheMemory.socketMap.get(key);
           if(webSocketConnectionList == null){
               CacheMemory.socketMap.put(key,connection);
           }
        }
        connection.connect();
    }


    private <T> void createConnection(WebsocketRequest<T> request) {
        createConnection(request, false);
    }

    private List<String> parseSymbols(String symbol) {
        return Arrays.asList(symbol.split("[,]"));
    }

    @Override
    public void subscribeCandlestickEvent(
            String symbols,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> callback) {
        subscribeCandlestickEvent(symbols, interval, callback, null);
    }

    @Override
    public void subscribeCandlestickEvent(
            String symbols,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeCandlestickEvent(
                parseSymbols(symbols), interval, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeTickerHuobiEvent(String symbols, CandlestickInterval interval, SubscriptionListener<TickerDTO> callback, SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeTickerHuobiEvent(
                parseSymbols(symbols), interval, callback, errorHandler));
    }

    @Override
    public void subscribeTickerOkEvent(String symbols, SubscriptionListener<TickerDTO> callback, SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeTickerOkEvent(
                parseSymbols(symbols), callback, errorHandler));
    }

    @Override
    public void subscribeTickerBinanceEvent(SubscriptionListener<TickerDTO> callback, SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeTickerBinanceEvent(
                callback, errorHandler));
    }

    @Override
    public void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener) {
        requestCandlestickEvent(symbols, from, to, interval, subscriptionListener, null);
    }

    @Override
    public void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        requestCandlestickEvent(symbols, from, to, interval, true, subscriptionListener, errorHandler);
    }

    public void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            boolean autoClose,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.requestCandlestickEvent(
                parseSymbols(symbols), from, to, interval, subscriptionListener, errorHandler), autoClose);
    }
}