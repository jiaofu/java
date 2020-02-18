package com.jex.take.data.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TickerDTO {
    private Date date;
    private BigDecimal close;
    private String symbol;


}
