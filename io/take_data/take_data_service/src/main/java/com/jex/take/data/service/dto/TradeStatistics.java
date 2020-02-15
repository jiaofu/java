package com.jex.take.data.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeStatistics {
    private long timestamp;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal amount;
    private BigDecimal high;
    private BigDecimal low;
    private long count;
    private BigDecimal volume;
}
