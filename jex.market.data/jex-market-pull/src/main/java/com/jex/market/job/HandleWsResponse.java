package com.jex.market.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jex.market.dto.SymbolDTO;

import java.util.Map;

public interface HandleWsResponse {
    /**
     * 处理wss的返回值
     * @param response
     * @throws Exception
     */
    default void   wsResponse(String response)throws Exception{
        JSONObject json = JSON.parseObject(response);
        handleTicket(json);
        handleKlines(json);
        handeleDepth(json);
        handleTrade(json);
    }

    /**
     * 处理ticket
     * @param json
     */
     void handleTicket(JSONObject json);

    /**
     * 处理ws 的k 线
     * @param json
     */
     void handleKlines(JSONObject json);

    /**
     * 处理ws的深度
     * @param json
     */
     void handeleDepth(JSONObject json);

    /**
     * 处理ws的交易
     * @param json
     */
     void handleTrade(JSONObject json);

    /**
     * 初始化map 集合
     */
    Map<String, SymbolDTO> initMap();
}
