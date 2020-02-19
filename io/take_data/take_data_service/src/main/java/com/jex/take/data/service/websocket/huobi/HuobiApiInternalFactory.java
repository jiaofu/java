package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.api.RestApiRequestImpl;
import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.client.SyncRequestClient;
import com.jex.take.data.service.client.impl.AsyncRequestImpl;
import com.jex.take.data.service.client.impl.SyncRequestImpl;
import com.jex.take.data.service.util.RequestOptions;

import java.net.URI;

public final class HuobiApiInternalFactory {

    private static final HuobiApiInternalFactory instance = new HuobiApiInternalFactory();

    public static HuobiApiInternalFactory getInstance() {
        return instance;
    }

    private HuobiApiInternalFactory() {
    }

    public SyncRequestClient createSyncRequestClient(
            RequestOptions options) {
        RequestOptions requestOptions = new RequestOptions(options);
        RestApiRequestImpl requestImpl = new RestApiRequestImpl( requestOptions);
        return new SyncRequestImpl(requestImpl);
    }

    public AsyncRequestClient createAsyncRequestClient(RequestOptions options) {
        RequestOptions requestOptions = new RequestOptions(options);
        RestApiRequestImpl requestImpl = new RestApiRequestImpl( requestOptions);

        return new AsyncRequestImpl(requestImpl);
    }

    public SubscriptionClient createSubscriptionClient(
            SubscriptionOptions options) {
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions(options);
        RequestOptions requestOptions = new RequestOptions();
        try {
            String host = new URI(options.getUri()).getHost();
            requestOptions.setUrl("https://" + host);
        } catch (Exception e) {

        }
        SubscriptionClient webSocketStreamClient = new WebSocketStreamClientImpl(
              subscriptionOptions);
        return webSocketStreamClient;
    }


}