package com.jex.market.job;

import com.jex.market.dto.SymbolDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BinanceWsJob  implements Job ,JexCallback{

    @Autowired
    HandleWsResponse binanceHandleWsResponseImpl;

    String ws  = "wss://stream.binance.net";
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        initialize();
    }


    @Override
    public void onResponse(String response) {
        System.out.println(response);
    }




    @Override
    public void initialize() {
        //String streamingUrl =ws+"/stream?streams=!miniTicker@arr@3000ms";
        String streamingUrl =getWsUrl();
        Request request = new Request.Builder().url(streamingUrl).build();
        OkHttpClient client = ServiceGenerator.getSharedClient();
        client.newWebSocket(request, new JexWebSocketListener(this));
    }

    private String getWsUrl(){
        Map<String, SymbolDTO> map = binanceHandleWsResponseImpl.initMap();
        StringBuilder sb = new StringBuilder();
        sb.append(ws);
        sb.append("/stream?streams=!miniTicker@arr/");
        for(Map.Entry entry : map.entrySet()){
            sb.append(entry.getKey());
            sb.append("@kline_1m/");
            sb.append(entry.getKey());
            sb.append("@depth10/");
            sb.append(entry.getKey());
            sb.append("@trade/");
        }
        String str = sb.toString();
        String  streamingUrl = str.substring(0,str.length()-1);
       // String streamingUrl =ws+"/stream?streams=!miniTicker@arr/bnbbtc@kline_1m/btcusdt@depth10/ltcusdt@depth10/eosusdt@trade";
        return streamingUrl;
    }
}
