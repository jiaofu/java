package com.jex.take.data.service.websocket.huobi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceWebSocketConnection extends WebSocketConnection  {
    BinanceWebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose){
        super(options,request,watchDog,autoClose);
    }
}
