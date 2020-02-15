package com.jex.take.data.service.api;

import com.jex.take.data.service.dto.TradeStatistics;

import java.util.Map;

public interface RequestClient {
    Map<String, TradeStatistics> getTickers();
}
