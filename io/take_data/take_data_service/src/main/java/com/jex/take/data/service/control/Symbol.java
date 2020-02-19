package com.jex.take.data.service.control;

import com.jex.take.data.service.enums.ExchangeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Symbol {

    public static Map<String, List<String>> map = new ConcurrentHashMap<>();

    public static Map<String, List<String>> getSymbols() {

        if (map.size() != 0) {
            return map;
        }


        List<String> huobi = new ArrayList<>();
        List<String> binance = new ArrayList<>();
        List<String> ok = new ArrayList<>();
        huobi.add("btcusdt");
        huobi.add("ethusdt");
        binance.add("BTCUSDT");
        binance.add("ETHUSDT");
        ok.add("ETH-USDT");
        ok.add("BTC-USDT");

        map.put(ExchangeEnum.huobiTicket.getDesc(), huobi);
        map.put(ExchangeEnum.binanceTicket.getDesc(), binance);
        map.put(ExchangeEnum.okTicket.getDesc(), ok);
        return map;
    }

    public static List<String> getSymbols(String name) {
        Map<String, List<String>> current  =  getSymbols();
       return current.get(name);

    }
}
