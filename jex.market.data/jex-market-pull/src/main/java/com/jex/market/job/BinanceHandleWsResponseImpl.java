package com.jex.market.job;

import com.alibaba.fastjson.JSONObject;
import com.jex.market.dto.KlinesDTO;
import com.jex.market.dto.PriceDTO;
import com.jex.market.dto.SymbolDTO;
import com.jex.market.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BinanceHandleWsResponseImpl implements HandleWsResponse {

    public static Map<String, SymbolDTO> map = new ConcurrentHashMap<>();


    @Override
    public void handleTicket(JSONObject json) {
        String name = getSymbol(json,"miniTicker");
        if(StringUtil.isEmpty(name)){
            return;
        }
        JSONObject data  = json.getJSONObject("data");
        String s = data.getString("s");
        SymbolDTO dto = map.get(s);
        if(dto == null){
            return;
        }
        PriceDTO price = dto.getPrice();
        String nowPrice = data.getString("c");
        if(nowPrice.equals(price.getPrice())){
            price.setIsChange(false);
        }else {
            price.setIsChange(true);
        }
        price.setPrice(nowPrice);
        price.setPullDate(System.currentTimeMillis());

        return;
    }

    @Override
    public void handleKlines(JSONObject json) {
        String name = getSymbol(json,"kline");
        if(StringUtil.isEmpty(name)){
            return;
        }
        SymbolDTO dto = map.get(name);
        if(dto == null){
            return;
        }
        KlinesDTO klinesDTO = dto.getKlines();
        JSONObject data  = json.getJSONObject("data");
        JSONObject k =data.getJSONObject("k");

        long t = k.getLong("t");
       String o = k.getString("o");
        String c = k.getString("c");
        String h = k.getString("h");
        String l = k.getString("l");
        String v = k.getString("v");
        String q = k.getString("q");
        if(klinesDTO.getStartTime().equals(t)){
            if(klinesDTO.getOpen().equals(o)&& klinesDTO.getClose().equals(c) && klinesDTO.getHigh().equals(h)&&
            klinesDTO.getLow().equals(l) && klinesDTO.getVolume().equals(v)){
                klinesDTO.setIsChange(false);
            }else {
                klinesDTO.setIsChange(true);
            }
        }else {
            klinesDTO.setIsChange(true);
        }
        klinesDTO.setClose(c);
        klinesDTO.setOpen(o);
        klinesDTO.setHigh(h);
        klinesDTO.setLow(l);
        klinesDTO.setVolume(v);
        klinesDTO.setQuoteAssetVolume(q);

        klinesDTO.setStartTime(t);
        return;

    }

    @Override
    public void handeleDepth(JSONObject json) {
        String name = getSymbol(json,"depth");
        if(StringUtil.isEmpty(name)){
            return;
        }

    }

    @Override
    public void handleTrade(JSONObject json) {
        String name = getSymbol(json,"trade");
        if(StringUtil.isEmpty(name)){
            return;
        }
    }

    @Override
    public Map<String, SymbolDTO> initMap() {
        map.put("bnbudst",new SymbolDTO());
        map.put("eosusdt",new SymbolDTO());
        map.put("ethusdt",new SymbolDTO());
        return map;
    }

    private String getSymbol(JSONObject json, String name) {
        Object obj = json.get("stream");
        if(obj == null){
            return null;
        }
        String streamName = obj.toString();
        if(streamName.contains(name)){
            String[] split = streamName.split("@");
            if(split.length !=2){
                return null;
            }
            return split[0].toLowerCase();
        }
        return null;
    }


}
