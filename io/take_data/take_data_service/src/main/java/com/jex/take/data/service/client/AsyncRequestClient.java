package com.jex.take.data.service.client;

import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.RequestOptions;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.util.result.ResponseCallback;
import com.jex.take.data.service.websocket.huobi.HuobiApiInternalFactory;

import java.util.Map;

public interface AsyncRequestClient {
    void getTickers(ResponseCallback<AsyncResult<Map<String, TradeStatistics>>> callback);
    static AsyncRequestClient create() {
        return HuobiApiInternalFactory.getInstance().createAsyncRequestClient(
                new RequestOptions());
    }
}
