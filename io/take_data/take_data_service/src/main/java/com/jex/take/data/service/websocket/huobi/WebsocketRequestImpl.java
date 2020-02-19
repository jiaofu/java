package com.jex.take.data.service.websocket.huobi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jex.take.data.service.dto.Candlestick;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.model.event.CandlestickEvent;
import com.jex.take.data.service.model.event.CandlestickReqEvent;
import com.jex.take.data.service.util.*;
import com.jex.take.data.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WebsocketRequestImpl {

    WebsocketRequest<TickerDTO> subscribeTickerHuobiEvent(
            List<String> symbols,
            CandlestickInterval interval,
            SubscriptionListener<TickerDTO> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {

        WebsocketRequest<TickerDTO> request =
                new WebsocketRequest<>(subscriptionListener, errorHandler);
        if (symbols.size() == 1) {
            request.name = "Candlestick for Huobi ticker" + symbols;
        } else {
            request.name = "Candlestick for Huobi ticker" + symbols + " ...";
        }
        request.connectionHandler = (connection) ->
                symbols.stream()
                        .map((symbol) -> Channels.klineChannel(symbol, interval))
                        .forEach(req -> {
                            connection.send(req);
                            InternalUtils.await(1);
                        });
        request.jsonParser = (jsonWrapper) -> {
            String ch = jsonWrapper.getString("ch");
            ChannelParser parser = new ChannelParser(ch);
            TickerDTO data = new TickerDTO();
            data.setSymbol(parser.getSymbol());

            data.setDate(new Date(
                    TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts"))));
            JsonWrapper tick = jsonWrapper.getJsonObject("tick");

            data.setDate(new Date(TimeService.convertCSTInSecondToUTC(tick.getLong("id"))));

            data.setClose(tick.getBigDecimal("close"));

            return data;
        };
        return request;
    }

    WebsocketRequest<TickerDTO> subscribeTickerOkEvent(
            List<String> symbols,
            SubscriptionListener<TickerDTO> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {

        WebsocketRequest<TickerDTO> request =
                new WebsocketRequest<>(subscriptionListener, errorHandler);
        if (symbols.size() == 1) {
            request.name = "Candlestick for ok " + symbols;
        } else {
            request.name = "Candlestick for ok " + symbols + " ...";
        }
        request.connectionHandler = (connection) ->
                connection.send(Channels.klineOkChannel(symbols));
        request.jsonParser = (jsonWrapper) -> {
            String ch = jsonWrapper.getString("data");
            JSONArray jsonArray = JSONArray.parseArray(ch);
            TickerDTO data = new TickerDTO();
            for( Object obj : jsonArray){
               JSONObject jsonObject = (JSONObject) obj;
               data.setSymbol(jsonObject.getString("instrument_id"));
               data.setDate(DateUtil.formatStrUTCToDate(jsonObject.getString("timestamp")));
               data.setClose(jsonObject.getBigDecimal("last"));
            }
            return data;
        };
        return request;
    }

    WebsocketRequest<TickerDTO> subscribeTickerBinanceEvent(

            SubscriptionListener<TickerDTO> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {

        WebsocketRequest<TickerDTO> request =
                new WebsocketRequest<>(subscriptionListener, errorHandler);

        request.connectionHandler = null;
        request.jsonParser = (jsonWrapper) -> {
            TickerDTO data = new TickerDTO();
            if(jsonWrapper.containKey("stream")&& jsonWrapper.containKey("data")){

                String ch = jsonWrapper.getString("data");
                JSONObject jsonObject = JSON.parseObject(ch);
                data.setSymbol(jsonObject.getString("s"));
                data.setClose(jsonObject.getBigDecimal("c"));
                data.setDate(new Date(jsonObject.getLong("E")));
            }
            return data;
        };
        return request;
    }



    WebsocketRequest<CandlestickEvent> subscribeCandlestickEvent(
            List<String> symbols,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {

        WebsocketRequest<CandlestickEvent> request =
                new WebsocketRequest<>(subscriptionListener, errorHandler);
        if (symbols.size() == 1) {
            request.name = "Candlestick for " + symbols;
        } else {
            request.name = "Candlestick for " + symbols + " ...";
        }
        request.connectionHandler = (connection) ->
                symbols.stream()
                        .map((symbol) -> Channels.klineChannel(symbol, interval))
                        .forEach(req -> {
                            connection.send(req);
                            InternalUtils.await(1);
                        });
        request.jsonParser = (jsonWrapper) -> {
            String ch = jsonWrapper.getString("ch");
            ChannelParser parser = new ChannelParser(ch);
            CandlestickEvent candlestickEvent = new CandlestickEvent();
            candlestickEvent.setSymbol(parser.getSymbol());
            candlestickEvent.setInterval(interval);
            candlestickEvent.setTimestamp(
                    TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts")));
            JsonWrapper tick = jsonWrapper.getJsonObject("tick");
            Long id = tick.getLong("id");
            Candlestick data = new Candlestick();
            data.setId(id);
            data.setTimestamp(TimeService.convertCSTInSecondToUTC(tick.getLong("id")));
            data.setOpen(tick.getBigDecimal("open"));
            data.setClose(tick.getBigDecimal("close"));
            data.setLow(tick.getBigDecimal("low"));
            data.setHigh(tick.getBigDecimal("high"));
            data.setAmount(tick.getBigDecimal("amount"));
            data.setCount(tick.getLong("count"));
            data.setVolume(tick.getBigDecimal("vol"));
            candlestickEvent.setData(data);
            return candlestickEvent;
        };
        return request;
    }


    WebsocketRequest<CandlestickReqEvent> requestCandlestickEvent(
            List<String> symbols, Long from, Long to,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {


        WebsocketRequest<CandlestickReqEvent> request =
                new WebsocketRequest<>(subscriptionListener, errorHandler);
        if (symbols.size() == 1) {
            request.name = "Candlestick for huobi " + symbols;
        } else {
            request.name = "Candlestick for huobi " + symbols + " ...";
        }
        request.connectionHandler = (connection) ->
                symbols.stream()
                        .map((symbol) -> Channels.klineReqChannel(symbol, interval, from, to))
                        .forEach(req -> {
                            connection.send(req);
                            InternalUtils.await(1);
                        });
        request.jsonParser = (jsonWrapper) -> {
            String ch = jsonWrapper.getString("rep");
            ChannelParser parser = new ChannelParser(ch);
            CandlestickReqEvent candlestickEvent = new CandlestickReqEvent();
            candlestickEvent.setSymbol(parser.getSymbol());
            candlestickEvent.setInterval(interval);
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");

            List<Candlestick> list = new ArrayList<>();
            dataArray.forEach(dataJson -> {
                Long id = dataJson.getLong("id");
                Candlestick data = new Candlestick();
                data.setId(id);
                data.setTimestamp(TimeService.convertCSTInSecondToUTC(id));
                data.setOpen(dataJson.getBigDecimal("open"));
                data.setClose(dataJson.getBigDecimal("close"));
                data.setLow(dataJson.getBigDecimal("low"));
                data.setHigh(dataJson.getBigDecimal("high"));
                data.setAmount(dataJson.getBigDecimal("amount"));
                data.setCount(dataJson.getLong("count"));
                data.setVolume(dataJson.getBigDecimal("vol"));
                list.add(data);
            });

            candlestickEvent.setData(list);
            return candlestickEvent;
        };
        return request;


    }



}
