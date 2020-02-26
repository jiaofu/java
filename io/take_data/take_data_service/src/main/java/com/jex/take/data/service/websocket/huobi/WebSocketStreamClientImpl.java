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

        String key = options.getFromExchangeName()+"_"+options.getFromTaskName();
        WebSocketConnection connection = null;
        String websocketUrl = options.getUri().toLowerCase();
        if (websocketUrl.contains(BaseUrl.okSocket.toLowerCase())) {
            connection = new OkWebSocketConnection(
                    options, request, watchDog, autoClose);

        } else if (websocketUrl.contains(BaseUrl.binanceSocket.toLowerCase())) {



            connection = new BinanceWebSocketConnection(
                    options, request, watchDog, autoClose);
        } else {

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
        subscribeCandlestickHuobiEvent(symbols, interval, callback, null);
    }

    @Override
    public void subscribeCandlestickHuobiEvent(
            String symbols,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeHuobiCandlestickEvent(
                parseSymbols(symbols), interval, subscriptionListener, errorHandler));
    }

    @Override
    public void subscribeCandlestickOKEvent(String symbols, SubscriptionListener<CandlestickEvent> callback, SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeCandlestickOkEvent(
                parseSymbols(symbols),  callback, errorHandler));
    }

    @Override
    public void subscribeCandlestickBinanceEvent(SubscriptionListener<CandlestickEvent> callback, SubscriptionErrorHandler errorHandler) {
        createConnection(requestImpl.subscribeCandlestickBinanceEvent(
                callback, errorHandler));
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