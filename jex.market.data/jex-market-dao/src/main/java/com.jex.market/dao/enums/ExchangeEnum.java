package com.jex.market.dao.enums;

public enum ExchangeEnum {


    binance(1,"binance");

    private int code;
    private String desc;
    ExchangeEnum(int code, String desc) {
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
}
