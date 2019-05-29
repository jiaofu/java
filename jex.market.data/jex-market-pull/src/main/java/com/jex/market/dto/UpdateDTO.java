package com.jex.market.dto;

import lombok.Data;

/**
 * 更新时间,是否更改
 */
@Data
public class UpdateDTO {
    /**
     * 拉取的时间
     */
    private long pullDate;
    /**
     * 是否更改了本地数据
     */
    private Boolean isChange;

    private int from;

}
