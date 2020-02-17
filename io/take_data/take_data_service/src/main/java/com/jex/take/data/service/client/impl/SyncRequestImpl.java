package com.jex.take.data.service.client.impl;

import com.jex.take.data.service.api.RestApiRequestImpl;
import com.jex.take.data.service.client.SyncRequestClient;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.RestApiInvoker;

import java.util.Map;

public class SyncRequestImpl implements SyncRequestClient {
    private final RestApiRequestImpl requestImpl;

    public SyncRequestImpl(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }


    @Override
    public Map<String, TickerDTO> getHuobiTickers() {
        return RestApiInvoker.callSync(requestImpl.getHuobiTickers());
    }

    @Override
    public Map<String, TickerDTO> getOktickers() {
        return RestApiInvoker.callSync(requestImpl.getOkTickers());
    }

    @Override
    public Map<String, TickerDTO> getBinanceTickers() {
        return RestApiInvoker.callSync(requestImpl.getBinanceTickers());
    }
}
