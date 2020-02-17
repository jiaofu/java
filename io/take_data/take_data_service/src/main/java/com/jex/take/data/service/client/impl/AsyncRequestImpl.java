package com.jex.take.data.service.client.impl;

import com.jex.take.data.service.api.RestApiRequestImpl;
import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.RestApiInvoker;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.util.result.ResponseCallback;

import java.util.Map;

public class AsyncRequestImpl implements AsyncRequestClient {
    private final RestApiRequestImpl requestImpl;

    public AsyncRequestImpl(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    @Override
    public void getTickers(ResponseCallback<AsyncResult<Map<String, TradeStatistics>>> callback) {
        RestApiInvoker.callASync(requestImpl.getTickers(), callback);
    }
}
