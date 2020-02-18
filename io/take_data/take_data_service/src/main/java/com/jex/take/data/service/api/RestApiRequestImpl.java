package com.jex.take.data.service.api;

import com.alibaba.fastjson.JSON;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.util.*;
import okhttp3.Request;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RestApiRequestImpl {
    private String marketQueryUrl;
    private RequestOptions options;

    public RestApiRequestImpl( RequestOptions options) {
        this.options = options;
        try {
            String host = new URL(this.options.getUrl()).getHost();
            this.marketQueryUrl = "https://" + host;
        } catch (Exception e) {
        }
    }

    private Request createRequestByGet(String address, UrlParamsBuilder builder) {
        return createRequestByGet(marketQueryUrl, address, builder);
    }
    private Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
        return createRequest(url, address, builder);
    }

    private Request createRequest(String url, String address, UrlParamsBuilder builder) {
        String requestUrl = url + address;
        if(url.toLowerCase().contains("okex")){
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/json").build();
        }
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


   public RestApiRequest<Map<String, TickerDTO>> getHuobiTickers() {
        RestApiRequest<Map<String, TickerDTO>> request = new RestApiRequest<>();
        request.request = createRequestByGet("/market/tickers", UrlParamsBuilder.build());
        request.jsonParser = (jsonWrapper -> {
            Map<String, TickerDTO> map = new HashMap<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            long ts = TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts"));
            dataArray.forEach(item -> {
                TickerDTO statistics = new TickerDTO();
                statistics.setDate(new Date(ts));
                statistics.setClose(item.getBigDecimal("close"));
                map.put(item.getString("symbol"), statistics);
            });
            return map;
        });
        return request;
    }

    public RestApiRequest<Map<String, TickerDTO>> getOkTickers() {
        RestApiRequest<Map<String, TickerDTO>> request = new RestApiRequest<>();
        request.request = createRequestByGet("/api/spot/v3/instruments/ticker", UrlParamsBuilder.build());
        request.jsonParser = (jsonWrapper -> {
            Map<String, TickerDTO> map = new HashMap<>();

            if(jsonWrapper.getJsonArrayOk() !=null){
                JsonWrapperArray dataArray = new JsonWrapperArray(jsonWrapper.getJsonArrayOk());
                dataArray.forEach(item -> {
                    TickerDTO statistics = new TickerDTO();
                    statistics.setClose(item.getBigDecimal("last"));
                    map.put(item.getString("product_id"), statistics);
                });

            }
            return map;
        });
        return request;
    }

    public RestApiRequest<Map<String, TickerDTO>> getBinanceTickers() {
        RestApiRequest<Map<String, TickerDTO>> request = new RestApiRequest<>();
        request.request = createRequestByGet("/api/v3/ticker/price", UrlParamsBuilder.build());
        request.jsonParser = (jsonWrapper -> {
            Map<String, TickerDTO> map = new HashMap<>();

            if(jsonWrapper.getJsonArrayOk() !=null){
                JsonWrapperArray dataArray = new JsonWrapperArray(jsonWrapper.getJsonArrayOk());
                dataArray.forEach(item -> {
                    TickerDTO statistics = new TickerDTO();
                    statistics.setClose(item.getBigDecimal("price"));
                    map.put(item.getString("symbol"), statistics);
                });

            }
            return map;
        });
        return request;
    }
}
