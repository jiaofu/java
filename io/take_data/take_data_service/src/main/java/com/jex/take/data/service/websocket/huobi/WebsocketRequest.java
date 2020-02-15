package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.util.Handler;
import com.jex.take.data.service.util.RestApiJsonParser;
import com.jex.take.data.service.util.SubscriptionErrorHandler;
import com.jex.take.data.service.util.SubscriptionListener;

public class WebsocketRequest<T> {
    WebsocketRequest(SubscriptionListener<T> listener, SubscriptionErrorHandler errorHandler) {
        this.updateCallback = listener;
        this.errorHandler = errorHandler;
    }

    String name;
    Handler<WebSocketConnection> connectionHandler;
    Handler<WebSocketConnection> authHandler = null;
    final SubscriptionListener<T> updateCallback;
    RestApiJsonParser<T> jsonParser;
    final SubscriptionErrorHandler errorHandler;
}
