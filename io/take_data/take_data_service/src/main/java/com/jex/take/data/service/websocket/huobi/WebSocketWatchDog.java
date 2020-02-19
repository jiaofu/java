package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.control.CacheMemory;
import com.jex.take.data.service.enums.ConnectionState;
import com.jex.take.data.service.util.BaseUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketWatchDog {

    private final CopyOnWriteArrayList<WebSocketConnection> TIME_HELPER = new CopyOnWriteArrayList<>();
    private final SubscriptionOptions options;
    private static final Logger log = LoggerFactory.getLogger(WebSocketConnection.class);

    WebSocketWatchDog(SubscriptionOptions subscriptionOptions) {
        this.options = Objects.requireNonNull(subscriptionOptions);
        long t = 1_000;
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> {
            TIME_HELPER.forEach(connection -> {
                if (connection.getState() == ConnectionState.CONNECTED) {
                    // Check response
                    if (options.isAutoReconnect()) {
                        long ts = System.currentTimeMillis() - connection.getLastReceivedTime();
                        if (ts > options.getReceiveLimitMs()) {
                            log.warn("[Sub][" + connection.getConnectionId() + "] No response from server");
                            connection.reConnect(options.getConnectionDelayOnFailure());
                        }
                    }
                } else if (connection.getState() == ConnectionState.DELAY_CONNECT) {
                    connection.reConnect();
                } else if (connection.getState() == ConnectionState.CLOSED_ON_ERROR) {
                    if (options.isAutoReconnect()) {
                        connection.reConnect(options.getConnectionDelayOnFailure());
                    }
                }

                // 如果是 ok,则需要定时 发送 ping
                if(connection.getState() == ConnectionState.CONNECTED){
                    if(subscriptionOptions.getUri().toLowerCase().contains(BaseUrl.okSocket)){
                        connection.send("ping");
                    }
                }
            });
        }, t, t, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(exec::shutdown));
    }

    void onConnectionCreated(WebSocketConnection connection) {
        TIME_HELPER.addIfAbsent(connection);
    }

    void onClosedNormally(WebSocketConnection connection) {
        TIME_HELPER.remove(connection);
        // 取消
        CacheMemory.socketMap.put(connection.getFromExchangeName(),null);
    }
}
