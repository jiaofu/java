package com.jex.market.dto;

import lombok.Data;

@Data
public class KlinesDTO extends UpdateDTO {

    private String open;

    private String high;

    private String low;

    private String close;

    private String volume;

    private Long startTime;

    private String quoteAssetVolume;

    private Long numberOfTrades;
}
