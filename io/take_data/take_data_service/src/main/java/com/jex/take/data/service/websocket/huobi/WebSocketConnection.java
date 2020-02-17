package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.util.RestApiInvoker;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.net.URI;

@Slf4j
public class WebSocketConnection extends WebSocketListener {
    private String subscriptionMarketUrl = "wss://api.huobi.pro/ws";
    protected volatile ConnectionState state = ConnectionState.IDLE;
    protected final int connectionId;
    protected static int connectionCounter = 0;
    protected volatile long lastReceivedTime = 0;
    protected WebSocket webSocket = null;
    protected int delayInSecond = 0;
    protected  Request okhttpRequest;
    protected final WebsocketRequest request;
    protected final boolean autoClose;
    protected final WebSocketWatchDog watchDog;




    WebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose) {
        this.connectionId = WebSocketConnection.connectionCounter++;
        this.request = request;
        this.autoClose = autoClose;
        try {
            String host = new URI(options.getUri()).getHost();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(request.authHandler == null){
            this.okhttpRequest = new Request.Builder().url(subscriptionMarketUrl).build();
        }
        this.watchDog = watchDog;
        log.info("[Sub] Connection [id: "
                + this.connectionId
                + "] created for " + request.name);
    }

    public ConnectionState getState() {
        return state;
    }
    int getConnectionId() {
        return this.connectionId;
    }

    long getLastReceivedTime() {
        return this.lastReceivedTime;
    }
    void reConnect(int delayInSecond) {
        log.warn("[Sub][" + this.connectionId + "] Reconnecting after " + delayInSecond + " seconds later");
        if (webSocket != null) {
            webSocket.cancel();
            webSocket = null;
        }
        this.delayInSecond = delayInSecond;
        state = ConnectionState.DELAY_CONNECT;
    }
    void reConnect() {
        if (delayInSecond != 0) {
            delayInSecond--;
        } else {
            connect();
        }
    }
    void connect() {
        if (state == ConnectionState.CONNECTED) {
            log.info("[Sub][" + this.connectionId + "] Already connected");
            return;
        }
        log.info("[Sub][" + this.connectionId + "] Connecting...");
        webSocket = RestApiInvoker.createWebSocket(okhttpRequest, this);
    }

    //
}
