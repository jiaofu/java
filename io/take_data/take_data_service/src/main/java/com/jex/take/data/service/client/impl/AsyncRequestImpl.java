package com.jex.take.data.service.client.impl;

import com.jex.take.data.service.api.RestApiRequestImpl;
import com.jex.take.data.service.client.AsyncRequestClient;
import com.jex.take.data.service.dto.TickerDTO;
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
    public void getHuobiTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback) {
        RestApiInvoker.callASync(requestImpl.getHuobiTickers(), callback);
    }

    @Override
    public void getOkTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback) {
        RestApiInvoker.callASync(requestImpl.getOkTickers(), callback);
    }

    @Override
    public void getBinanceTickers(ResponseCallback<AsyncResult<Map<String, TickerDTO>>> callback) {
        RestApiInvoker.callASync(requestImpl.getBinanceTickers(), callback);
    }
}
