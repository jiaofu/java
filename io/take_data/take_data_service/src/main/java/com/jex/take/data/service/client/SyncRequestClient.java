package com.jex.take.data.service.client;

import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.websocket.huobi.HuobiApiInternalFactory;

import java.util.Map;

public interface SyncRequestClient {

    Map<String, TradeStatistics> getTickers();


    Map<String, TradeStatistics> getOktickers();

    public static SyncRequestClient create() {
        return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
                new RequestOptions());
    }

     static SyncRequestClient create(RequestOptions requestOptions) {
        return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
                requestOptions);
    }
}
