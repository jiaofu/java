package com.jex.market.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepthDTO extends UpdateDTO {

    public DepthDTO(){
        bids = new ArrayList<>();
        asks = new ArrayList<>();
    }

    private List<OrderBookEntryDTO> bids;


    private List<OrderBookEntryDTO> asks;
}
