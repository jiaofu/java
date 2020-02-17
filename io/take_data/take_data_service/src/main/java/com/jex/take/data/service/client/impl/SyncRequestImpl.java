package com.jex.take.data.service.client.impl;

import com.jex.take.data.service.api.RestApiRequestImpl;
import com.jex.take.data.service.client.SyncRequestClient;
import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.RestApiInvoker;

import java.util.Map;

public class SyncRequestImpl implements SyncRequestClient {
    private final RestApiRequestImpl requestImpl;

    public SyncRequestImpl(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }


    @Override
    public Map<String, TradeStatistics> getTickers() {
        return RestApiInvoker.callSync(requestImpl.getTickers());
    }
}
