package com.jex.take.data.service.util;


import com.alibaba.fastjson.JSONObject;
import com.jex.take.data.service.enums.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public static String klineOkChannel(List<String> symbols) {

        //   {"op": "subscribe", "args": ["spot/ticker:ETH-USDT","spot/candle60s:ETH-USDT"]}
        JSONObject json = new JSONObject();
        json.put("op", "subscribe");
        List<String> lists = new ArrayList<>();
        for (String str : symbols) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("spot/candle60s:");
            stringBuilder.append(str.toUpperCase());
            lists.add(stringBuilder.toString());
        }
        json.put("args", lists);

        String subscribe = json.toJSONString();
        log.info(" ok的订阅的主题为:" + subscribe);

        return subscribe;
    }

    public static String tickerOkChannel(List<String> symbols) {

        //   {"op": "subscribe", "args": ["spot/ticker:ETH-USDT","spot/candle60s:ETH-USDT"]}
        JSONObject json = new JSONObject();
        json.put("op", "subscribe");
        List<String> lists = new ArrayList<>();
        for (String str : symbols) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("spot/ticker:");
            stringBuilder.append(str.toUpperCase());
            lists.add(stringBuilder.toString());
        }
        json.put("args", lists);

        String subscribe = json.toJSONString();
        log.info(" ok的订阅的主题为:" + subscribe);

        return subscribe;
    }

    public static String ticketBinanceChannel(List<String> stringList){
        // 组合查询 <symbol>@miniTicker
        StringBuilder wdString = new StringBuilder(BaseUrl.binanceSocket);
        wdString.append("/stream?streams=");
        for (String str : stringList) {
            wdString.append(str.toLowerCase());
            wdString.append("@miniTicker");
            wdString.append("/");
        }
        if (stringList.size() > 0) {
            if (wdString.length() > 0) {
                wdString.deleteCharAt(wdString.length() - 1);
            }
        }
        return wdString.toString();
    }


    /**
     * 只有一分钟k 线
     * @param stringList
     * @return
     */
    public static String klineBinanceChannel(List<String> stringList){
        // 组合查询 <symbol>@miniTicker
        StringBuilder wdString = new StringBuilder(BaseUrl.binanceSocket);
        wdString.append("/stream?streams=");
        for (String str : stringList) {
            wdString.append(str.toLowerCase());
            wdString.append("@kline_1m");
            wdString.append("/");
        }
        if (stringList.size() > 0) {
            if (wdString.length() > 0) {
                wdString.deleteCharAt(wdString.length() - 1);
            }
        }
        return wdString.toString();
    }



}