package com.jex.take.data.service.api;

import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.RestApiInvoker;

import java.util.Map;


public class HuobiRequestClient implements RequestClient {

    private static String  baseUrl = "https://api.huobi.pro";


    public static String getBaseUrl(){
        return baseUrl;
    }
    private final RestApiRequestImpl requestImpl;

   public HuobiRequestClient(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    @Override
    public Map<String, TickerDTO> getTickers() {
        return RestApiInvoker.callSync(requestImpl.getTickers());
    }
}
