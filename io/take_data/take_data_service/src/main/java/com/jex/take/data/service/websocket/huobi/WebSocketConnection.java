package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.util.JsonWrapper;
import com.jex.take.data.service.util.RestApiInvoker;
import com.jex.take.data.service.util.TimeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

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




    private String  fromExchangeName;
    private String  fromTaskName;


    WebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose) {
        this.connectionId = WebSocketConnection.connectionCounter++;
        this.request = request;
        this.autoClose = autoClose;
        try {
            subscriptionMarketUrl = options.getUri();
            this.fromExchangeName = options.getFromExchangeName();
            this.fromTaskName = options.getFromTaskName();
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


    //websocket

    // 发送通知

   public void send(String str) {
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

    public void onReceiveAndClose(JsonWrapper jsonWrapper) {
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

    public void onReceive(JsonWrapper jsonWrapper) {
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
        watchDog. onConnectionCreated(this);
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

    public void onError(String errorMessage, Throwable e) {
        if (request.errorHandler != null) {
            ApiException exception = new ApiException(
                    ApiException.SUBSCRIPTION_ERROR, errorMessage, e);
            request.errorHandler.onError(exception);
        }
        log.error("[Sub][" + this.connectionId + "] " + errorMessage);
    }
    public void closeOnError() {
        if (webSocket != null) {
            this.webSocket.cancel();
            state = ConnectionState.CLOSED_ON_ERROR;
            log.error("[Sub][" + this.connectionId + "] Connection is closing due to error");
        }
    }

    public void setLastReceivedTime(long lastReceivedTime) {
        this.lastReceivedTime = lastReceivedTime;
    }

    public long getLastReceivedTime() {
        return this.lastReceivedTime;
    }

    public String getFromExchangeName() {
        return fromExchangeName;
    }

    public String getFromTaskName() {
        return fromTaskName;
    }
}
