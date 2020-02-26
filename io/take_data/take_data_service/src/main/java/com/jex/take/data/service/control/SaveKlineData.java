package com.jex.take.data.service.control;

import com.jex.take.data.service.model.event.CandlestickEvent;

import java.util.List;

public interface SaveKlineData {
    public void insertList(CandlestickEvent candlestickEvent);
    public void insertSave(List<CandlestickEvent> list);
}
