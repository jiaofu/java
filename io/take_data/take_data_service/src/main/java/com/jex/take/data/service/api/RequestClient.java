package com.jex.take.data.service.api;

import com.jex.take.data.service.dto.TickerDTO;

import java.util.Map;

public interface RequestClient {
    Map<String, TickerDTO> getTickers();
}
