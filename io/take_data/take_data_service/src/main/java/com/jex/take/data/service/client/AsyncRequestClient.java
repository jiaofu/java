package com.jex.take.data.service.client;

import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.util.result.ResponseCallback;
import com.jex.take.data.service.websocket.huobi.HuobiApiInternalFactory;

import java.util.Map;

public interface AsyncRequestClient {
    void getHuobiTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback);

    void getOkTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback);

    void getBinanceTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback);
    static AsyncRequestClient create() {
        return HuobiApiInternalFactory.getInstance().createAsyncRequestClient(
                new RequestOptions());
    }

     static AsyncRequestClient create(RequestOptions requestOptions) {
        return HuobiApiInternalFactory.getInstance().createAsyncRequestClient(
                requestOptions);
    }
}
