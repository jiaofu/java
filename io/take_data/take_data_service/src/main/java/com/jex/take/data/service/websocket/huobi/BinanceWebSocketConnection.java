package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.util.JsonWrapper;
import com.jex.take.data.service.util.TimeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import okio.ByteString;

@Slf4j
public class BinanceWebSocketConnection extends WebSocketConnection {
    BinanceWebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose) {
        super(options, request, watchDog, autoClose);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        lastReceivedTime = TimeService.getCurrentTimeStamp();

        try {
            if (request == null) {
                log.error("[Sub][" + this.connectionId
                        + "] request is null");
                closeOnError();
                return;
            }
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(text);
            onReceiveAndClose(jsonWrapper);
        } catch (Exception e) {
            log.error("[Sub][" + this.request.name + "] Unexpected error: " + e.getMessage());
            closeOnError();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        try {
            if (request == null) {
                log.error("[Sub][" + this.connectionId
                        + "] request is null");
                closeOnError();
                return;
            }
            lastReceivedTime = TimeService.getCurrentTimeStamp();


            log.info("[Sub][" + this.request.name + "] 拿到 ByteString 的消息");

        } catch (Exception e) {
            log.error("[Sub][" + this.connectionId + "] Unexpected error: " + e.getMessage());
            closeOnError();
        }
    }
}
