package com.jex.market.util;

import java.math.BigDecimal;

/**
 * Created by wanghui on 2017/6/19.
 * 计算类
 */
public class ArithmeticUtil {


    /**
     * 小数精确的位数
     */
    private static final int DEF_DIV_SCALE = 10;
    private static final BigDecimal one = new BigDecimal("1");

    /**
     * 提供精确的加法运算。
     *
     * @param v1  被加数
     * @param v2  加数
     * @return 两个参数的和
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return add(b1,b2);
    }

    public static BigDecimal add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return add(b1,b2);
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }


    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    public static BigDecimal subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return subtract(b1,b2);
    }

    public static BigDecimal subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return subtract(b1,b2);
    }

    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }



    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return multiply(b1,b2);
    }

    public static BigDecimal multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return multiply(b1,b2);
    }

    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {

        return v1.multiply(v2);
    }



    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divide(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return divide(b1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal divide(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return divide(b1, b2, DEF_DIV_SCALE);
    }

    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }


    ///////////////////////////////////////取余数//////////////////////////////////////////////////
    /**
     * 取余数  string
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static BigDecimal strRemainder(String v1, String v2, int scale){
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 取余数  string
     * @param v1
     * @param v2
     * @param scale
     * @return  string
     */
    public static String strRemainder2Str(String v1, String v2, int scale){
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 取余数  BigDecimal
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static BigDecimal bigRemainder(BigDecimal v1, BigDecimal v2, int scale){
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.remainder(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    ///////////////////////////////////////比较//////////////////////////////////////////////////
    /**
     * 比较大小 如果v1 大于v2 则 返回true 否则false
     * @param v1
     * @param v2
     * @return
     */
    public static boolean compareTo(String v1, String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return compareTo(b1,b2);
    }

    /**
     * 比较大小 如果v1 大于v2 则 返回true 否则false
     * @param v1
     * @param v2
     * @return
     */
    public static boolean compareTo(BigDecimal v1, BigDecimal v2){

        int bj = v1.compareTo(v2);
        boolean res ;
        if(bj>0)
            res = true;
        else
            res = false;
        return res;
    }


    /**
     * 比较大小 如果v1 等于v2 则 返回true 否则false
     * @param v1
     * @param v2
     * @return
     */
    public static boolean compareEquals(String v1, String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return compareEquals(b1,b2);

    }

    /**
     * 比较大小 如果v1 等于v2 则 返回true 否则false
     * @param b1
     * @param b2
     * @return
     */
    public static boolean compareEquals(BigDecimal b1, BigDecimal b2){
        int bj = b1.compareTo(b2);
        boolean res ;
        if(bj==0)
            res = true;
        else
            res = false;
        return res;
    }

    /**
     * 小于等于
     * @param b1
     * @param b2
     * @return
     */
    public static boolean compareLE(BigDecimal b1, BigDecimal b2){
        int bj = b1.compareTo(b2);
        boolean res ;
        if(bj<=0)
            res = true;
        else
            res = false;
        return res;
    }

    /**
     * 大于等于
     * @param b1
     * @param b2
     * @return
     */
    public static boolean compareGE(BigDecimal b1, BigDecimal b2){
        int bj = b1.compareTo(b2);
        boolean res ;
        if(bj>=0)
            res = true;
        else
            res = false;
        return res;
    }

    /**
     * 总是在非   0   舍弃小数(即截断)之前增加数字
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal roundUpScale(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v.divide(one, scale, BigDecimal.ROUND_UP);
    }
    ///////////////////////////////////////四舍五入//////////////////////////////////////////////////

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal roundUp(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 提供精确的小数位不四舍五入处理。
     *
     * @param v 不需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 不四舍五入后的结果
     */
    public static BigDecimal roundDown(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        return v.divide(one, scale, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal roundDown(double v, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v));
        return roundDown(b1,scale);
    }
    public static BigDecimal roundUp(double v, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v));
        return roundUp(b1,scale);
    }


    public static void main(String[] args) {
        BigDecimal a=new BigDecimal("633.010");
        BigDecimal b= ArithmeticUtil.roundDown(a,0);
        System.out.println(b);
        System.out.println(StringUtil.formatDecimal(b));
    }
}
