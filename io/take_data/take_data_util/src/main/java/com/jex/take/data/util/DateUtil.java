package com.jex.take.data.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {

    public static final String DATE_FORMAT_ZH_CN = "yyyy-MM-dd";

    /**
     * Deprecated, in favour of DATE_TIME_FORMAT_ZH_CN.
     */

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIMENUM = "yyyyMMddHHmmss";
    /**
     * Deprecated, in favour of DATE_FORMAT_ZH_CN.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_ZH_CN = "yyyy-MM-dd HH:mm:ss";//供外部通过反射调用
    public static final String DATE_TIME_FORMAT_JA = "yyyy-MM-dd HH:mm:ss"; //供外部通过反射调用
    public static final String DATE_TIME_FORMAT_EN = "HH:mm:ss dd/MM/yyyy";//供外部通过反射调用
    public static final String DATE_FORMAT_SECOND = "HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final DateFormat dfDate = new SimpleDateFormat(DATE_FORMAT_ZH_CN);
    /**
     * yyyyMd
     */
    public static final DateFormat dfDateShort = new SimpleDateFormat("yyyyMd");
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat dfDateTime = new SimpleDateFormat(DATE_TIME_FORMAT_ZH_CN);
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final DateFormat dfDateTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * yyyy/M/d/H
     */
    public static final DateFormat dfDateTime3 = new SimpleDateFormat("yyyy/M/d/H");
    /**
     * yyyyMMdd_HHmmss
     */
    public static final DateFormat dfDateTime4 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    /**
     * yyyyMMdd
     */
    public static final DateFormat dfDateTime5 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public static String getFormatDate(Date date) {
        if (date == null) {
            return "";
        }
        return dfDateTime2.format(date);
    }

    public static Date getDate(String dateStr, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    /**
     * String ==> java.util.Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date dateString2Util(String dateStr, String format) {
        return new SimpleDateFormat(format).parse(dateStr, new ParsePosition(0));
    }

    /**
     * java.util.Date ==> String
     *
     * @return
     */
    public static String dateUtil2String(Date date, String format) {
        if (date == null) return null;
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateUtil2StringDate(Date date) {
        if (date == null) {
            return null;
        }
        return dfDate.format(date);
    }

    public static String dateUtil2StringDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return dfDateTime.format(date);
    }

    public static Date dateUtil2date(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(formatter.format(date), new ParsePosition(0));
    }

    public static String getDate() {
        return dateUtil2String(new Date(), DATE_TIME_FORMAT);
    }

    public static Date getLocalDate(long utcLong) {

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(utcLong);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);

            /*DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_ZH_CN);
            TimeZone utcZone = TimeZone.getTimeZone("GMT+8:00");
            format.setTimeZone(utcZone);*/
            return calendar.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }


    /**
     * 2020-02-18T04:08:47.852Z
     * @param utcTime
     * @return
     */
    public static String formatStrUTCToDateStr(String utcTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        sf.setTimeZone(utcZone);
        Date date = null;
        String dateTime = "";
        try {
            date = sf.parse(utcTime);
            dateTime = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static Date formatStrUTCToDate(String utcTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);

        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        sf.setTimeZone(utcZone);
        Date date = null;

        try {
            date = sf.parse(utcTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}