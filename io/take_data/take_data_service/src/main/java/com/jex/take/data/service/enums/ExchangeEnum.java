package com.jex.take.data.service.enums;

public enum ExchangeEnum {

    huobiTicket(1,"huobi"),
    okTicket(2,"ok"),
    binanceTicket(3,"binance");

    private int code;
    private String desc;
    ExchangeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
