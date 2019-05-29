package com.jex.market.dao.bean.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xxshic on 2018/12/1.
 */
public class PageCondition {

    public static final List EMPTY_LIST = new ArrayList(0);
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";
    public static final int DEFAULT_SIZE = 200;
    public static final String DESC_SIGN = "<";
    public static final String ASC_SIGN = ">";

    public static final int START_0_SIZE_TYPE = 1 << 0;
    public static final int START_SIZE_TYPE = 1 << 1;
    public static final int STARTTIME_SIZE_TYPE = 1 << 2;
    public static final int STARTTIME_ENDTIME_TYPE = 1 << 3;


    private long start;
    private int size;
    private Date startTime;
    private Date endTime;
    private String sign;
    private String direction;
    private int type;

    private PageCondition() {
        setDesc();
    }

    public static PageCondition getCondition(Long start, Integer size, Date startTime, Date endTime, boolean isAsc) {
        if (start == null && startTime == null) {
            //两个不能同时为空
            start = 0L;
        }
        if (size == null && endTime == null) {
            //两个不能同时为空
            size = PageCondition.DEFAULT_SIZE;
        }
        PageCondition condition = new PageCondition();
        if (isAsc) {
            condition.setAsc();
        } else {
            condition.setDesc();
        }
        if (start != null && size != null) {
            // 设定 start size 条件 最高优先
            condition.start = start;
            condition.size = size;
            //设定类型
            if (start <= 0) {
                //start 小于等于0时，是个特殊查询
                condition.type = START_0_SIZE_TYPE;
            } else {
                condition.type = START_SIZE_TYPE;
            }
            return condition;
        } else if (startTime != null && size != null) {
            // 设定 startTime size 条件
            condition.startTime = startTime;
            condition.size = size;
            //设定类型
            condition.type = STARTTIME_SIZE_TYPE;
        } else if (startTime != null && endTime != null) {
            // 设定start endTime 条件
            condition.startTime = startTime;
            condition.endTime = endTime;
            //设定类型
            condition.type = STARTTIME_ENDTIME_TYPE;
        }
        return condition;
    }

    private void setDesc() {
        direction = DESC;
        sign = DESC_SIGN;
    }

    private void setAsc() {
        direction = ASC;
        sign = ASC_SIGN;
    }


    public long getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getSign() {
        return sign;
    }

    public String getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }

}
