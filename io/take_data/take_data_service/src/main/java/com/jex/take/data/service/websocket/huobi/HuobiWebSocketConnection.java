package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.util.InternalUtils;
import com.jex.take.data.service.util.JsonWrapper;
import com.jex.take.data.service.util.TimeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import okio.ByteString;

import java.io.IOException;

@Slf4j
public class HuobiWebSocketConnection extends WebSocketConnection {


    HuobiWebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose){
        super(options,request,watchDog,autoClose);
    }

    // webSock 相关



    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        lastReceivedTime = TimeService.getCurrentTimeStamp();
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

            String data;
            try {
                data = new String(InternalUtils.decode(bytes.toByteArray()));
            } catch (IOException e) {
                log.error("[Sub][" + this.connectionId
                        + "] Receive message error: " + e.getMessage());
                closeOnError();
                return;
            }
            log.debug("[On Message][{}] {}", connectionId, data);
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
            if (jsonWrapper.containKey("status") && !"ok".equals(jsonWrapper.getString("status"))) {
                String errorCode = jsonWrapper.getStringOrDefault("err-code", "");
                String errorMsg = jsonWrapper.getStringOrDefault("err-msg", "");
                onError(errorCode + ": " + errorMsg, null);
                log.error("[Sub][" + this.connectionId
                        + "] Got error from server: " + errorCode + "; " + errorMsg);
                close();
            } else if (jsonWrapper.containKey("op")) {
                String op = jsonWrapper.getString("op");
                if (op.equals("notify")) {
                    onReceive(jsonWrapper);
                } else if (op.equals("ping")) {
                    processPingOnTradingLine(jsonWrapper, webSocket);
                } else if (op.equals("auth")) {
                    if (request.authHandler != null) {
                        request.authHandler.handle(this);
                    }
                } else if (op.equals("req")) {
                    onReceiveAndClose(jsonWrapper);
                }
            } else if (jsonWrapper.containKey("ch") || jsonWrapper.containKey("rep")) {
                onReceiveAndClose(jsonWrapper);
            } else if (jsonWrapper.containKey("ping")) {
                processPingOnMarketLine(jsonWrapper, webSocket);
            } else if (jsonWrapper.containKey("subbed")) {
            }
        } catch (Exception e) {
            log.error("[Sub][" + this.connectionId + "] Unexpected error: " + e.getMessage());
            closeOnError();
        }
    }

    private void processPingOnTradingLine(JsonWrapper jsonWrapper, WebSocket webSocket) {
        long ts = jsonWrapper.getLong("ts");
        webSocket.send(String.format("{\"op\":\"pong\",\"ts\":%d}", ts));
    }

    private void processPingOnMarketLine(JsonWrapper jsonWrapper, WebSocket webSocket) {
        long ts = jsonWrapper.getLong("ping");
        webSocket.send(String.format("{\"pong\":%d}", ts));
    }



}
