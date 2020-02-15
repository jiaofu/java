package com.jex.take.data.service.api;

import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.RestApiInvoker;

import java.util.Map;

public class HuobiRequestClient implements RequestClient {

    private static String  baseUrl = "https://api.huobi.pro";


    private final RestApiRequestImpl requestImpl;

    HuobiRequestClient(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    @Override
    public Map<String, TradeStatistics> getTickers() {
        return RestApiInvoker.callSync(requestImpl.getTickers());
    }
}
