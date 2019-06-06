package com.jex.market.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jex.market.dto.*;
import com.jex.market.util.StringUtil;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
        SymbolDTO dto = null;
        if(StringUtil.isEmpty(name) || (dto=map.get(name)) ==null ){
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
        SymbolDTO dto = null;
        if(StringUtil.isEmpty(name) || (dto=map.get(name)) ==null ){
            return;
        }
        DepthDTO depthDTO = dto.getDepth();
        JSONObject data  = json.getJSONObject("data");
        List<OrderBookEntryDTO> bids = depthList("bids",data);
        List<OrderBookEntryDTO> asks = depthList("asks",data);
        depthDTO.setAsks(asks);
        depthDTO.setBids(bids);
        return;

    }
    private  List<OrderBookEntryDTO> depthList(String name, JSONObject data){
        JSONArray jsonArray =data.getJSONArray(name);
        List<OrderBookEntryDTO> list = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            OrderBookEntryDTO orderBookEntryDTO = new OrderBookEntryDTO();
            JSONArray jsonchild =  (JSONArray)(jsonArray.get(0));
            orderBookEntryDTO.setPrice( jsonchild.get(0).toString());
            orderBookEntryDTO.setQty( jsonchild.get(1).toString());
            list.add(orderBookEntryDTO);
        }
        return list;
    }

    @Override
    public void handleTrade(JSONObject json) {
        String name = getSymbol(json,"trade");
        SymbolDTO dto = null;
        if(StringUtil.isEmpty(name) || (dto=map.get(name)) ==null ){
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

    public static void main(String[] args) {
        List<String[]> strings = new ArrayList<>();
        String[] str1 = new String[]{"a","b"};
        String[] str2 = new String[]{"c","d"};
        String[] str3 = new String[]{"3","f"};
        strings.add(str1);
        strings.add(str2);
        strings.add(str3);
       String jsonReuslt =  JSON.toJSONString(strings);
       System.out.println(jsonReuslt);
        JSONArray array = JSONArray.parseArray(jsonReuslt);
      System.out.println(array);
    }


}
