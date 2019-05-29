package com.jex.market.dao.bean;

import lombok.Data;

import java.util.Date;

/**
 * 所有跟数据库相关类的基类
 *
 * @author zp
 * @date 2018/11/21
 */
@Data
public class BaseBean {

    private long id;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
}
