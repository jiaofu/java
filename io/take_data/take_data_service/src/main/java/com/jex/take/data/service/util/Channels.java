package com.jex.take.data.service.util;


import com.alibaba.fastjson.JSONObject;
import com.jex.take.data.service.enums.CandlestickInterval;

public abstract class Channels {

    public static final String OP_SUB = "sub";
    public static final String OP_REQ = "req";

    public static String klineChannel(String symbol, CandlestickInterval interval) {
        JSONObject json = new JSONObject();
        json.put("sub", "market." + symbol + ".kline." + interval.toString());
        json.put("id", TimeService.getCurrentTimeStamp() + "");
        return json.toJSONString();
    }

    public static String klineReqChannel(String symbol, CandlestickInterval interval, Long from, Long to) {
        JSONObject json = new JSONObject();
        json.put(OP_REQ, "market." + symbol + ".kline." + interval.toString());
        json.put("id", TimeService.getCurrentTimeStamp() + "");
        if (from != null) {
            json.put("from", from);
        }
        if (to != null) {
            json.put("to", to);
        }
        return json.toJSONString();
    }

}