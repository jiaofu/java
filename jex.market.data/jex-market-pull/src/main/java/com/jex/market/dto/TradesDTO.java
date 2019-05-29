package com.jex.market.dto;

import lombok.Data;

@Data
public class TradesDTO extends UpdateDTO {
    /**
     * Price.
     */
    private String price;

    /**
     * Quantity.
     */
    private String qty;


    private String commissionAsset;


    private long time;

    private String symbol;


    private boolean buyer;


    private boolean maker;


    private boolean bestMatch;

    private String orderId;

}
