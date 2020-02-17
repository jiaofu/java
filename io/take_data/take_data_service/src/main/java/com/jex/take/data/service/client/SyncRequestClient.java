package com.jex.take.data.service.client;

import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.websocket.huobi.HuobiApiInternalFactory;

import java.util.Map;

public interface SyncRequestClient {

    Map<String, TickerDTO> getHuobiTickers();


    Map<String, TickerDTO> getOktickers();


    Map<String, TickerDTO> getBinanceTickers();

    public static SyncRequestClient create() {
        return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
                new RequestOptions());
    }

    public static SyncRequestClient create(RequestOptions requestOptions) {
        return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
                requestOptions);
    }
}
