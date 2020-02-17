package com.jex.take.data.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TickerDTO {
    private long timestamp;
    private BigDecimal close;
    private String symbol;


}
