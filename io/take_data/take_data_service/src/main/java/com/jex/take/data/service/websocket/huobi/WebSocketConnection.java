package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.util.InternalUtils;
import com.jex.take.data.service.util.JsonWrapper;
import com.jex.take.data.service.util.RestApiInvoker;
import com.jex.take.data.service.util.TimeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.io.IOException;
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



    // 发送通知

    void send(String str) {
        boolean result = false;
        log.debug("[Send]{}", str);
        if (webSocket != null) {
            result = webSocket.send(str);
        }
        if (!result) {
            log.error("[Sub][" + this.connectionId
                    + "] Failed to send message");
            closeOnError();
        }
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


    private void onReceiveAndClose(JsonWrapper jsonWrapper) {
        onReceive(jsonWrapper);
        if (autoClose) {
            close();
        }
    }
    public void close() {
        log.error("[Sub][" + this.connectionId + "] Closing normally");
        webSocket.cancel();
        webSocket = null;
        watchDog.onClosedNormally(this);
    }

    private void onReceive(JsonWrapper jsonWrapper) {
        Object obj = null;
        try {
            obj = request.jsonParser.parseJson(jsonWrapper);
        } catch (Exception e) {
            onError("Failed to parse server's response: " + e.getMessage(), e);
        }
        try {
            request.updateCallback.onReceive(obj);
        } catch (Exception e) {
            onError("Process error: " + e.getMessage()
                    + " You should capture the exception in your error handler", e);
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        if (state == ConnectionState.CONNECTED) {
            state = ConnectionState.IDLE;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
        log.info("[Sub][" + this.connectionId + "] Connected to server");
        watchDog.onConnectionCreated(this);
        if (request.connectionHandler != null) {
            request.connectionHandler.handle(this);
        }
        state = ConnectionState.CONNECTED;
        lastReceivedTime = TimeService.getCurrentTimeStamp();

    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        onError("Unexpected error: " + t.getMessage(), t);
        closeOnError();
    }

    private void onError(String errorMessage, Throwable e) {
        if (request.errorHandler != null) {
            ApiException exception = new ApiException(
                    ApiException.SUBSCRIPTION_ERROR, errorMessage, e);
            request.errorHandler.onError(exception);
        }
        log.error("[Sub][" + this.connectionId + "] " + errorMessage);
    }
    private void closeOnError() {
        if (webSocket != null) {
            this.webSocket.cancel();
            state = ConnectionState.CLOSED_ON_ERROR;
            log.error("[Sub][" + this.connectionId + "] Connection is closing due to error");
        }
    }
}
