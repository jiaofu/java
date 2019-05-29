package com.jex.market.dto;

import lombok.Data;

@Data
public class SymbolDTO {

    public SymbolDTO(){
        depth = new DepthDTO();
        klines = new KlinesDTO();
        price = new PriceDTO();
        trades = new TradesDTO();
    }
    private  DepthDTO depth;

    private KlinesDTO klines;

    private PriceDTO price;

    private TradesDTO trades;

}
