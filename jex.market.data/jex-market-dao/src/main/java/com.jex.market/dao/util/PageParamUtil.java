package com.jex.market.dao.util;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 分页工具
 */
@Slf4j
public class PageParamUtil {

    private static final int START = 1;
    private static final int END = 1 << 1;
    private static final int ID = 1 << 2;
    private static final int LIMIT = 1 << 3;
    public static final int START_END = START | END;
    public static final int ID_LIMIT = ID | LIMIT;
    public static final int START_END_LIMIT = START | END | LIMIT;

    public static final int DEFAULT = LIMIT;

    public static int getType(Date start, Date end, Long id, Integer limit) {
        Long startTime = null;
        Long endTime = null;
        if (null != start) {
            startTime = start.getTime();
        }
        if (null != end) {
            endTime = end.getTime();
        }
        return getType(startTime, endTime, id, limit);
    }

    /**
     * 获取分页类型
     *
     * @param start
     * @param end
     * @param id
     * @param limit
     * @return
     */
    public static int getType(Long start, Long end, Long id, Integer limit) {
        //原理: 1表示位置非空， 低0位（1 << 0） 表示 START ,其它类推，
        // 所以 0011 表示 START_END, 1100 表示 ID_LIMIT，其它为14种为异常状态
        int flag = 0;
        if (start != null) {
            flag |= START;
        }
        if (end != null) {
            flag |= END;
        }
        if (id != null) {
            flag |= ID;
        }
        if (limit != null) {
            flag |= LIMIT;
        }
        return flag;
    }


    private static String getDescOfMaxTimeSub(long maxTimeSub) {
        StringBuilder builder = new StringBuilder();
        int second = (int) (maxTimeSub / 1000);
        int minute = second / 60;
        int hour = minute / 60;
        int day = hour / 24;
        if (day > 0) {
            return builder.append(day).append("day").toString();
        } else if (hour > 0) {
            return builder.append(hour).append("hour").toString();
        } else if (minute > 0) {
            return builder.append(minute).append("minute").toString();
        }
        return "";
    }

    /**
     * 查询mode
     */
    @Data
    public static class QueryMode {
        private final int mode;
        private Long fromId;
        private Integer limit;
        private Date startTime;
        private Date endTime;

        public QueryMode(int mode, Long fromId, Integer limit, Date startTime, Date endTime) {
            this.mode = mode;
            this.fromId = fromId;
            this.limit = limit;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

}
