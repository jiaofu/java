package com.jex.take.data.service.util;

public class ChannelParser {

    private String symbol = "";

  public  ChannelParser(String input) {
        String[] fields = input.split("\\.");
        if (fields.length >= 2) {
            symbol = fields[1];
        }
        String type = "";
        if (fields.length > 3) {
            type = fields[2];
        }
        if (type.equals("kline")) {

        }
    }

    public String getSymbol() {
        return symbol;
    }
}
