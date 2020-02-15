package com.jex.take.data.service.api;

import com.jex.take.data.service.dto.TradeStatistics;
import com.jex.take.data.service.util.JsonWrapperArray;
import com.jex.take.data.service.util.RestApiRequest;
import com.jex.take.data.service.util.TimeService;
import com.jex.take.data.service.util.UrlParamsBuilder;
import okhttp3.Request;

import java.util.HashMap;
import java.util.Map;

public class RestApiRequestImpl {
    private String marketQueryUrl;
    RestApiRequestImpl( String  url) {
        this.marketQueryUrl = url;
    }

    private Request createRequestByGet(String address, UrlParamsBuilder builder) {
        return createRequestByGet(marketQueryUrl, address, builder);
    }
    private Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
        return createRequest(url, address, builder);
    }

    private Request createRequest(String url, String address, UrlParamsBuilder builder) {
        String requestUrl = url + address;
        if (builder != null) {
            if (builder.hasPostParam()) {
                return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                        .addHeader("Content-Type", "application/json").build();
            } else {
                return new Request.Builder().url(requestUrl + builder.buildUrl())
                        .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
            }
        } else {
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
        }
    }


    RestApiRequest<Map<String, TradeStatistics>> getTickers() {
        RestApiRequest<Map<String, TradeStatistics>> request = new RestApiRequest<>();
        request.request = createRequestByGet("/market/tickers", UrlParamsBuilder.build());
        request.jsonParser = (jsonWrapper -> {
            Map<String, TradeStatistics> map = new HashMap<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            long ts = TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts"));
            dataArray.forEach(item -> {
                TradeStatistics statistics = new TradeStatistics();
                statistics.setTimestamp(ts);
                statistics.setAmount(item.getBigDecimal("amount"));
                statistics.setOpen(item.getBigDecimal("open"));
                statistics.setClose(item.getBigDecimal("close"));
                statistics.setHigh(item.getBigDecimal("high"));
                statistics.setLow(item.getBigDecimal("low"));
                statistics.setCount(item.getLong("count"));
                statistics.setVolume(item.getBigDecimal("vol"));
                map.put(item.getString("symbol"), statistics);
            });
            return map;
        });
        return request;
    }
}
