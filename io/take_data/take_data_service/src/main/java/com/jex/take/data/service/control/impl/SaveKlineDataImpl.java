package com.jex.take.data.service.control.impl;

import com.jex.take.data.service.control.SaveKlineData;
import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.model.event.CandlestickEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class SaveKlineDataImpl implements SaveKlineData {
    public static Queue<CandlestickEvent> queus = new ConcurrentLinkedQueue();
    public static long lastInsert = System.currentTimeMillis();
    public static int limitSize = 100;
    public static int limitSecond = 60000;

    @Override
    public void insertList(CandlestickEvent candlestickEvent) {
        queus.add(candlestickEvent);
        checkList();
    }


    @Override
    public void insertSave(List<CandlestickEvent> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        lastInsert = System.currentTimeMillis();
        log.info("我已经插入输入数据库,条数;" + list.size());

    }

    public void checkList() {
        if (queus.size() >= limitSize || lastInsert + limitSecond < System.currentTimeMillis()) {
            int count = queus.size() > limitSize ? limitSize : queus.size();
            Map<String,CandlestickEvent> map =new HashMap<>();
            List<CandlestickEvent> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                CandlestickEvent candlestickEvent = queus.poll();
                if (candlestickEvent != null) {
                    map.put(candlestickEvent.getSymbol()+candlestickEvent.getInterval().toString()+candlestickEvent.getData().getOpen(),candlestickEvent);
                }
                map.entrySet().stream().forEach(q->list.add(candlestickEvent));
            }
            if (list.size() > 0) {
                insertSave(list);
            }
        }
    }
}
