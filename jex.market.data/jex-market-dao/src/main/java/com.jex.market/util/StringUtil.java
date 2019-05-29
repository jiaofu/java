package com.jex.market.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghui
 */
public class StringUtil {

   static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    private final static byte[] val = {
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F
    };

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        String tempStr = str.trim();
        if (tempStr.length() == 0)
            return true;
        if (tempStr.equals("null"))
            return true;
        return false;
    }

    /**
     * 去掉空白字符 空白符包含：空格、tab键、换行符
     *
     * @param str
     * @return
     */
    public static String removeWhiteSpace(String str) {
        String res = null;

        if (str != null) {
            char[] chars = str.toCharArray();
            res = "";

            for (int i = 0; i < chars.length; ++i) {
                if (!Character.isWhitespace(chars[i])) {
                    res += chars[i];
                }
            }
        }

        return res;
    }

    public static String getString(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            return s.trim();
        }
    }

    /**
     * 转码
     *
     * @param str
     * @return
     */
    public static String utf8Togb2312(String str) {
        String gbk = "";
        try {
            String utf8 = new String(str.getBytes("UTF-8"));
            String unicode = new String(utf8.getBytes(), "UTF-8");
            gbk = new String(unicode.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            logger.error(" StringUtil utf8Togb2312 ", e);
        }
        return gbk;
    }

    public static String utf8togb2312(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(
                                str.substring(i + 1, i + 3), 16));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }
                    i += 2;
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        String result = sb.toString();
        String res = null;
        try {
            byte[] inputBytes = result.getBytes("8859_1");
            res = new String(inputBytes, "UTF-8");
        } catch (Exception e) {
        }
        return res;
    }

    public static String toUTF8(String str) {
        try {
            if (str == null)
                str = "";
            else {
                str = str.trim();
                //str=toHtmlInput(str);
                str = new String(str.getBytes("ISO-8859-1"), "utf-8");
            }

        } catch (Exception e) {
            //com.wantuan.util.LogUtil.dealWith(ex,StringUtil.class);
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    public static String togbk(String str) {
        try {
            if (str == null)
                str = "";
            else {
                str = str.trim();
                str = new String(str.getBytes("gbk"), "gbk");
            }
        } catch (Exception e) {
            //com.wantuan.util.LogUtil.dealWith(ex,StringUtil.class);
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * 分割字符串
     *
     * @param str
     * @param c
     * @return
     */
    public static String[] splitStr(String str, char c) {
        try {
            str += c;
            int n = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c) n++;
            }

            String out[] = new String[n];

            for (int i = 0; i < n; i++) {
                int index = str.indexOf(c);
                out[i] = str.substring(0, index);
                str = str.substring(index + 1, str.length());
            }
            return out;

        } catch (Exception e) {
        }
        return null;
    }

    public static String UrlDecoder(String url) {
        try {
            if (url != null) {
                return URLDecoder.decode(url, "UTF-8");
            } else {
                return null;
            }

        } catch (IllegalArgumentException e) {
            try {
                return URLDecoder.decode(url, "GBK");
            } catch (UnsupportedEncodingException ex) {
                return null;
            } catch (IllegalArgumentException ex) {
                logger.error("url Decoder error : " + url, ex);
                return null;
            }
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    public static String UrlDecoderGBK(String url) {
        try {
            if (url != null) {
                return URLDecoder.decode(url, "GBK");
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException ex) {
            //com.wantuan.util.LogUtil.dealWith(ex,StringUtil.class);
            return null;
        }
    }

    public static String UrlEncoder(String url) {
        try {
            if (url == null) url = "";
            String tmp = URLEncoder.encode(url, "UTF-8");
            return tmp;
        } catch (UnsupportedEncodingException ex) {
            //com.wantuan.util.LogUtil.dealWith(ex,StringUtil.class);
            return null;
        }
    }

    /**
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source    源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     */
    public static String replaceStr(String source, String oldString, String newString) {
        StringBuffer output = new StringBuffer();

        int lengthOfSource = source.length();   // 源字符串长度
        int lengthOfOld = oldString.length();   // 老字符串长度

        int posStart = 0;   // 开始搜索位置
        int pos;            // 搜索到老字符串的位置

        String lower_s = source.toLowerCase();        //不区分大小写
        String lower_o = oldString.toLowerCase();

        while ((pos = lower_s.indexOf(lower_o, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }

    public static boolean isCn(String strInput) {
        if (isEmpty(strInput)) {
            return false;
        }
        String reg = "[\\u4e00-\\u9fa5]+";
        return strInput.matches(reg);
    }

    public static boolean isChinese(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (((charArray[i] >= 0x3400) && (charArray[i] < 0x9FFF)) || (charArray[i] >= 0xF900)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符串格式化成 HTML 代码输出
     * 只转换特殊字符，适合于 HTML 中的表单区域
     *
     * @param str 要格式化的字符串
     * @return 过滤后的字符串
     */
    public static String inputToHtml(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        str = replaceStr(str, "<", "&lt;");
        str = replaceStr(str, ">", "&gt;");
        //str = replaceStr(str, "/", "&#47;");
        str = replaceStr(str, "\\", "&#92;");
        str = replaceStr(str, "\r\n", "<br/>");
        str = replaceStr(str, " ", "&nbsp;");
        str = replaceStr(str, "'", "&acute;");
        str = replaceStr(str, "\"", "&quot;");
        str = replaceStr(str, "(", "&#40;");
        str = replaceStr(str, ")", "&#41;");
        //str = replaceStr(str,".","&#46;");
        return str;
    }

    /**
     * 将原始字符串中有危险的字符过滤
     * 用过html编辑器内容显示到网页上，需要保留大部分html标签
     *
     * @param str 格式化后的字符串
     * @return 过滤了危险之后的原始字符串
     */

    public static String outputToHtml(String str) {
        if (StringUtil.isEmpty(str)) return null;

        String[] html = {"onwaiting", "onvolumechange", "ontimeupdate", "onsuspend", "onstalled", "onseeking", "onseeked", "onratechange", "onprogress", "onplaying", "onplay", "onpause", "onloadstart", "onloadedmetadata", "onloadeddata", "onended", "onemptied", "ondurationchange", "oncanplaythrough", "oncanplay", "oninvalid", "oninput", "onforminput", "onformchange", "onundo", "onstorage", "onredo", "onpopstate", "onpageshow", "onpagehide", "ononline", "onoffline", "onmessage", "onbeforeonload", "onhaschange", "onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut", "onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onblur", "onbounce", "oncellchange", "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress", "onkeyup", "onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove", "onmoveend", "onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete", "onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload", "javascript", "vbscript", "expression", "applet", "meta", "xml", "blink", "link", "style", "script", "embed", "object", "iframe", "frame", "frameset", "ilayer", "layer", "bgsound", "title", "base"};
        str = outputToOrgStr(str);
        str = replaceStr(str, "<iframe", "");
        str = replaceStr(str, "</iframe", "");
        str = replaceStr(str, "<script", "");
        str = replaceStr(str, "</script", "");
        str = replaceStr(str, "<embed", "");
        str = replaceStr(str, "</embed", "");
        str = replaceStr(str, "<link", "");
        str = replaceStr(str, "</link", "");
        str = replaceStr(str, "script", "");
        str = replaceStr(str, "document", "");
        str = replaceStr(str, "createElement", "");
        str = replaceStr(str, "onerror", "");
        str = replaceStr(str, "body", "");
        str = replaceStr(str, "appendChild", "");
        str = replaceStr(str, "innerHTML", "");

        for (int i = 0; i < html.length; i++) {
            str = replaceStr(str, html[i], "");
        }

        return str;
    }

    /**
     * 过滤html输出与网站中可能产生冲突的标签
     *
     * @param str
     * @return
     */
    public static String filterWebsiteTag(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        str = fiterHtmlTag(str, "dd");
        str = fiterHtmlTag(str, "DD");
        str = fiterHtmlTag(str, "dl");
        str = fiterHtmlTag(str, "DL");
        str = fiterHtmlTag(str, "dt");
        str = fiterHtmlTag(str, "DT");
        return str;
    }

    public static String Html2Text(String inputString) {
        try {
            String htmlStr = inputString; //含html标签的字符串
            String textStr = "";
            Pattern p_script;
            Matcher m_script;
            Pattern p_style;
            Matcher m_style;
            Pattern p_html;
            Matcher m_html;


            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            textStr = htmlStr;
            textStr = replaceStr(textStr, "\r\n", "");
            textStr = replaceStr(textStr, "	", "");


            return textStr;//返回文本字符串
        } catch (Exception e) {
            logger.error("Html2Text: ", e);
            return null;
        }
    }

    /**
     * 过滤指定标签
     *
     * @param str
     * @param tag
     * @return
     */
    public static String fiterHtmlTag(String str, String tag) {
        String regxp = "<\\s*" + tag + "\\s*([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        str = sb.toString();
        regxp = "</" + tag + ">";
        pattern = Pattern.compile(regxp);
        matcher = pattern.matcher(str);
        sb = new StringBuffer();
        result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 过滤html标签
     *
     * @param htmlStr
     * @param length
     * @return
     */
    public static String htmlToStr(String htmlStr, int length) {
        if (htmlStr == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int count = 0;

        boolean flag = true;
        htmlStr = htmlStr.replace("&lt;", "<");
        char[] a = htmlStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == '<') {
                flag = false;
                continue;
            }
            if (a[i] == '>') {
                flag = true;
                continue;
            }
            if (flag == true) {
                count++;
            }
            if (count > length) {
                result.append("...");
                break;
            }
            if (flag == true) {
                result.append(a[i]);
            }
        }
        return result.toString();
    }

    /**
     * 全角转半角
     *
     * @param str
     * @return
     */
    public static String SBCchange(String str) {
        String outStr = "";
        byte[] b = null;
        try {
            for (int i = 0; i < str.length(); i++) {
                b = str.substring(i, i + 1).getBytes("unicode");
                if (b[3] == -1) {
                    b[2] = (byte) (b[2] + 32);
                    b[3] = 0;
                    outStr += new String(b, "unicode");
                } else {
                    outStr += str.substring(i, i + 1);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return outStr;
    }

    /**
     * 半角转全角
     *
     * @param str
     * @return
     */
    public static String BQchange(String str) {
        String outStr = "";
        byte[] b = null;
        try {
            for (int i = 0; i < str.length(); i++) {
                b = str.substring(i, i + 1).getBytes("unicode");
                if (b[3] != -1) {
                    b[2] = (byte) (b[2] - 32);
                    b[3] = -1;
                    outStr += new String(b, "unicode");
                } else {
                    outStr += str.substring(i, i + 1);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return outStr;
    }

    public static String filterCHNTOENG(String keyword) {
        if (keyword == null) return "";
        keyword = StringUtil.replaceStr(keyword, "‘", "'");
        keyword = StringUtil.replaceStr(keyword, "’", "'");
        keyword = StringUtil.replaceStr(keyword, "“", "\"");
        keyword = StringUtil.replaceStr(keyword, "”", "\"");
        keyword = StringUtil.replaceStr(keyword, "。", ".");
        keyword = StringUtil.replaceStr(keyword, "，", ",");
        keyword = StringUtil.replaceStr(keyword, "！", "!");
        keyword = StringUtil.replaceStr(keyword, "？", "?");
        keyword = StringUtil.replaceStr(keyword, "：", ":");
        keyword = StringUtil.replaceStr(keyword, "；", ";");
        return keyword;
    }

    public static int wordLength(String str) {
        if (str == null || str.length() == 0)
            return 0;
        int flag = -1;
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) >= 0) && (str.charAt(i) <= 255)) {
                if (flag != 0) {
                    flag = 0;
                    sum = sum + 1;
                }
            } else {
                flag = 1;
                sum = sum + 1;
            }
        }
        return sum;
    }

    /**
     * 字符串ip转长整型ip
     *
     * @param strIP
     * @return
     */
    public static long ipToLong(String strIP) {
        try {
            if (strIP == null || strIP.length() == 0) {
                return 0L;
            }
            long[] ip = new long[4];
            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);
            ip[0] = Long.parseLong(strIP.substring(0, position1));
            ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIP.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (Exception ex) {
            return 0L;
        }
    }

    /**
     * 长整型ip转字符串ip
     *
     * @param ipLong
     * @return
     */
    public static String longToip(long ipLong) {
        long mask[] = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
        long num = 0;
        StringBuffer ipInfo = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            num = (ipLong & mask[i]) >> (i * 8);
            if (i > 0) ipInfo.insert(0, ".");
            ipInfo.insert(0, Long.toString(num, 10));
        }
        return ipInfo.toString();
    }

    /**
     * 是否过滤登陆ip
     *
     * @return
     */
    public static boolean isFilterLoginIp(String ip) {

        if (!StringUtil.isEmpty(ip) && ip.length() > 10 && !"218.60.".equals(ip.substring(0, 7)) && !"180.97.".equals(ip.substring(0, 7)) && !"120.24.229.124".equals(ip) && !"43.230.88.".equals(ip.substring(0, 10))) {
            return true;
        }
        return false;
    }

    /**
     * 过滤十进制，16进制汉字
     *
     * @param str
     * @return
     */
    public static boolean filterCode(String str) {
        boolean flag = false;
        if (str == null || str.trim().length() == 0) {
            return flag;
        }
        String type = "&(amp;)?#\\w{5};";
        Pattern pattern = Pattern.compile(type);
        Matcher matcher = pattern.matcher(str);
        flag = matcher.matches();
        return flag;
    }

    public static String filterString(String str) {
        str = str.toLowerCase().trim();
        str = str.replaceAll("&nbsp;|<br>|<BR>|[\\s]|[\u3000]", "");
        return str;
    }
    public static boolean isAddress20(String str){
        String regex = "^(0(x|X)[A-Za-z0-9]{40})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 十进制转换成二进制
     *
     * @param ten
     * @return
     */
    public static String tenToEr(long ten) {
        String s = "";
        while (ten > 0) {
            long i = ten % 2;
            s = i + s;
            ten = ten / 2;
        }
        int t = s.length();
        for (int i = t; i < 32; i++) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 根据ip获取  子网掩码（起始ip 和 结束ip长度必须一致）
     *
     * @param startIP 起始ip(必须是二进制)
     * @param endIP   结束ip (必须是二进制)
     * @return
     */
    public static int getIPMark(String startIP, String endIP) {
        int result = 0;
        if (startIP == null || startIP.trim().length() == 0 || endIP == null || endIP.trim().length() == 0) {
            return result;
        }
        for (int i = 0; i < startIP.length(); i++) {
            char start = startIP.charAt(i);
            char end = endIP.charAt(i);
            if (start != end) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * 根据ip获取  子网掩码
     *
     * @param startIP
     * @param endIP
     * @return
     */
    public static int getIPMark(long startIP, long endIP) {
        int result = 0;
        if (startIP <= 0 || endIP <= 0) {
            return result;
        }
        result = getIPMark(tenToEr(startIP), tenToEr(endIP));
        return result;
    }

    /**
     * ip校验
     *
     * @param s
     * @return 格式是否正确
     */
    public static Boolean isIpAddress(String s) {
        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * email校验
     *
     * @param email
     * @return 格式是否正确
     */
    public static Boolean isEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        String regex = "^([a-zA-Z0-9_\\-\\.\\+]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否为合法的登录密码
     *
     * @param loginPwd 登陆密码
     * @return <li>true: 合法</li>
     * <li>false: 不合法</li>
     */
    public static boolean isLegalLoginPwd(String loginPwd) {
        if (!isLengthLegal(loginPwd, 6, 64)) {
            return false;
        }
        return isPassword(loginPwd);
    }

    /**
     * 判断是否为合法的交易密码
     *
     * @param adminPwd 登陆密码 注：亦称tradePassword, bankrollPassword, withdrawPassword
     * @return <li>true: 合法</li>
     * <li>false: 不合法</li>
     */
    public static boolean isLegalAdminPwd(String adminPwd) {
        if (!isLengthLegal(adminPwd, 6, 30)) {
            return false;
        }
        return isPassword(adminPwd);
    }

    /**
     * 判断是否为合法的短信验证码
     *
     * @param phoneCode 短信验证码, 亦称：smsCode
     * @return <li>true: 合法</li>
     * <li>false: 不合法</li>
     */
    public static boolean isLegalPhoneCode(String phoneCode) {
        return isLengthEqual(phoneCode, 6) && isNumber(phoneCode);
    }

    /**
     * password校验
     *
     * @param password
     * @return 格式是否正确
     */
    public static Boolean isPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        String special = "(.*[~!@#$%^&*()_+|<>,.?/:;'\\\\[\\\\]{}\\\"]+.*)";
        String number = "^.*[0-9]+.*$";
        String character = "^.*[a-zA-Z]+.*$";
        boolean a = password.matches(number);
        boolean b = password.matches(character);
        boolean c = password.matches(special);
        return a || b || (a && b) || (a && c) || (b && c) || (a && b && c);
    }

    /**
     * 手机号校验<br>
     * ^((176)|(177)|(178)|(170)|(173)|(13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$<br>
     * 合并：(176)|(177)|(178)|(170)|(173) -> (17[03678]), (13[0-9])|(14[0-9])|(18[0-9]) -> (1[348][0-9])<br>
     * ^((17[03678])|(1[348][0-9])|(15[^4,\\D]))\\d{8}$
     *
     * @param phoneNo
     * @return 格式是否正确
     * @date 2016-4-18
     */
    public static boolean isMobile(String phoneNo) {
        if (isEmpty(phoneNo) || !(phoneNo.length() <=11&&phoneNo.length()>= 7)) {
            return false;
        }
        String regex1 = "^((17[0123456789])|(1[348][0-9])|(15[^4,\\D])|(166)|(19[89]))\\d{8}$";
        String regex2 = "^\\+[0-9]{1,4}?\\d{6,11}$";
        String regex3="^[023456789]\\d{6,10}$";//泰国手机号
        Pattern p1 = Pattern.compile(regex1);
        Pattern p2 = Pattern.compile(regex2);
        Pattern p3 = Pattern.compile(regex3);
        Matcher m1 = p1.matcher(phoneNo);
        Matcher m2 = p2.matcher(phoneNo);
        Matcher m3 = p3.matcher(phoneNo);
        return m1.matches() || m2.matches()|| m3.matches();
    }

    /**
     * 是否为中国手机号码<br>
     *
     * @param mobiles
     * @return
     * @date 2016-4-18
     */
    public static boolean isChineseMobile(String mobiles) {
        if (isEmpty(mobiles)) {
            return false;
        }
        String regex1 = "^(\\+86)?((17[03678])|(1[348][0-9])|(15[^4,\\D]))\\d{8}$";
        Pattern p1 = Pattern.compile(regex1);
        Matcher m1 = p1.matcher(mobiles);
        return m1.matches();
    }

    /**
     *
     * @param s
     * @return 如果s的数组元素属于订单状态组合返回true
     */
    public static boolean isTradeStatus(String[] s){
        for (String ss : s){
            if (!(ss.equals("-1")  || ss.equals("0") || ss.equals("1") || ss.equals("2") || ss.equals("4") || ss.equals("8"))){
                return false;
            }
        }
        return true;
    }
    /**
     * @param str
     * @param len
     * @return
     */
    public static String toHtmlSubString(String str, int len) {
        int index = len - 1; //下标比总数少一个
        if (null == str || "".equals(str.trim())) {
            return "";
        }
        if (index <= 0) {
            return str;
        }
        byte[] bt = null;
        try {
            bt = str.getBytes();
        } catch (Exception e) {
            e.getMessage();
        }
        if (null == bt || bt.length <= len) {
            return str;
        }
        if (index > bt.length - 1) {
            index = bt.length - 1; //防越界
        }
        String substrx = null;
        //如果当前字节小于0，说明当前截取位置 有可能 将中文字符截断了
        if (bt[index] < 0) {
            int jsq = 0;
            int num = index;
            while (num >= 0) {
                if (bt[num] < 0) {
                    jsq += 1; //计数
                } else {
                    break; //循环出口
                }
                num -= 1;
            }

            int m = 0;
            //Unicode编码
            m = jsq % 2;
            index -= m;
            //这里是重点,去掉半个汉字(有可能是半个), m为0表示无一半汉字,
            substrx = new String(bt, 0, index + 1) + ".."; //当前被截断的中文字符整个不取
            return substrx;
        } else {
            substrx = null;
            //Unicode编码
            substrx = new String(bt, 0, index + 1) + "..";
            return substrx;
        }

    }

    public static String getOutputPintString(String str, int len) {
        try {
            if (str == null || str.trim().length() == 0) {
                return "";
            }
            str = str.replaceAll("&nbsp;", " ");
            byte[] b = str.trim().getBytes();
            if (b.length <= len) {
                return str;
            }
            StringBuffer sb = new StringBuffer();
            double sub = 0;
            for (int i = 0; i < str.length(); i++) {
                if (sub >= len - 2) {
                    sb.append("..");
                    break;
                }
                String tmp = str.substring(i, i + 1);
                sub += tmp.getBytes().length > 2 ? 2 : tmp.getBytes().length;
                char c = str.charAt(i);
                if (c >= 65 && c <= 90) {
                    sub += 0.5;
                }
                sb.append(tmp);
            }
            return sb.toString();
        } catch (Exception e) {
            return str;
        }

    }

    public static double getStringIELength(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        byte[] b = s.getBytes();
        if (b.length > 1) {
            return b.length;
        } else if ("il: ".indexOf(s) > -1) {
            return 0.4;
        } else if ("fjt".indexOf(s) > -1) {
            return 0.5;
        } else if ("rI".indexOf(s) > -1) {
            return 0.6;
        } else if ("sJ-z".indexOf(s) > -1) {
            return 0.7;
        } else if ("aceghnuvxy".indexOf(s) > -1) {
            return 0.9;
        } else if ("EFLPSTZ".indexOf(s) > -1) {
            return 1.1;
        } else if ("BR".indexOf(s) > -1) {
            return 1.2;
        } else if ("ACDGFHmwUVXY".indexOf(s) > -1) {
            return 1.3;
        } else if ("KNOQ".indexOf(s) > -1) {
            return 1.5;
        } else if ("MW".indexOf(s) > -1) {
            return 1.65;
        } else {
            return 1;
        }

    }

    public static boolean isEnglish(String str) {
        String reg = "[a-zA-Z]";
        int lng = str.length();
        for (int i = 0; i < lng; i++) {
            String tmp = str.charAt(i) + "";
            if (tmp.matches(reg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串长度是否处于一个规定的范围之内
     *
     * @param s      字符串
     * @param minLen 规定最小长度
     * @param maxLen 规定最大长度
     * @return <li>true: 合法</li>
     * <li>false: 不合法</li>
     */
    public static boolean isLengthLegal(String s, int minLen, int maxLen) {
        return !isEmpty(s) && s.length() >= minLen && s.length() <= maxLen;
    }

    /**
     * 判断字符串长度是否匹配
     *
     * @param s   字符串
     * @param len 规定的长度
     * @return <li>true: 长度匹配</li>
     * <li>false: 长度不匹配</li>
     */
    public static boolean isLengthEqual(String s, int len) {
        return !isEmpty(s) && s.length() == len;
    }

    /**
     * 是否包含非法字符
     * \\/:*?\" ><|
     *
     * @param str
     * @return true:包含非法字符 false 不包含非法字符
     */
    public static boolean isContainIllegalChar(String str) {
        if (str.indexOf("\\") == -1 && str.indexOf("/") == -1 && str.indexOf(":") == -1 && str.indexOf("*") == -1 && str.indexOf("?") == -1 && str.indexOf("\"") == -1 && str.indexOf(">") == -1 && str.indexOf("<") == -1 && str.indexOf("|") == -1) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * 把str字符串最后一个字符 若为中文标点则转为英文标点
     *
     * @param str
     * @return
     */
    public static String lastCharacterCn2En(String str) {
        if (str != null && str.length() > 0) {
            String lastStr = str.substring(str.length() - 1, str.length());
            String regex = "[，。？：；‘’！“”—……、（）【】{}《》－]";
            Pattern p = null; //正则表达式
            Matcher m = null; //操作的字符串
            p = Pattern.compile(regex);
            m = p.matcher(lastStr);
            if (m.matches()) {
                str = str + " ";
            }
        }
        return str;
    }

    public static int getCharCount(String str, char c) {
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                j++;
        }
        return j;
    }

    public static String getWeekStr(int week) {
        String weekStr = "";
        switch (week) {
            case 1:
                weekStr = "一";
                break;
            case 2:
                weekStr = "二";
                break;
            case 3:
                weekStr = "三";
                break;
            case 4:
                weekStr = "四";
                break;
            case 5:
                weekStr = "五";
                break;
            case 6:
                weekStr = "六";
                break;
            case 7:
                weekStr = "日";
                break;
            default:
                break;
        }
        return weekStr;
    }

    /**
     * 检测是否有emoji字符
     *
     * @param
     * @return 一旦含有就抛出
     */

    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtil.isEmpty(source)) {
            return source;
        }
        StringBuilder buf = new StringBuilder();
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    /**
     * 生成随机数
     *
     * @param length 生成随机数长度
     * @return
     */
    public static String randomNum(int length) {
        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, length);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, length + 1);
    }

    /**
     * 随机大小写字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomStr(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Random ran = new Random();
            int choice = ran.nextInt(2) % 2 == 0 ? 65 : 97;  //先通过 一个0-1的随机数 来选择是大写还是小写
            char x = (char) (choice + ran.nextInt(26));
            str.append(x);
        }
        return str.toString();
    }

    /**
     * 随机大小写字母以及数字字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomStrAndNumber(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Random ran = new Random();
            int suiji = ran.nextInt(3) % 3;
            int choice = suiji == 2 ? 65 : suiji == 1 ? 97 : 48;  //先通过 一个2-1-0的随机数 来选择是大写、小写还是数字
            char x = (char) (choice + (suiji == 0 ? ran.nextInt(10) : ran.nextInt(26)));
            str.append(x);
        }
        return str.toString();
    }

    /**
     * 去掉字符串中的 " [ ] 字符
     *
     * @param defstr
     * @return
     */
    public static String formatString(String defstr) {
        if (isEmpty(defstr)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < defstr.length(); i++) {
            String s = defstr.substring(i, i + 1);
            if (!s.endsWith("\"") && !s.endsWith("[") && !s.endsWith("]")) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static String gbkToUtf(String content) throws UnsupportedEncodingException {
        if (isEmpty(content)) {
            return "";
        }
        String iso = new String(content.getBytes("UTF-8"), "ISO-8859-1");
        String value = new String(iso.getBytes("ISO-8859-1"), "UTF-8");
        return value;
    }

    public static boolean isNum(String ss) {
        if (isEmpty(ss)) {
            return false;
        }
        for (int i = 0; i < ss.length(); i++) {
            char a = ss.charAt(i);
            if (a < '0' || a > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字 整数 小数 实数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        if (isEmpty(str)) {
            return false;
        }

        Pattern integerPattern = Pattern.compile("^-?\\d+$");
        boolean isInteger = integerPattern.matcher(str).matches();
        if (!isInteger) {
            Pattern floatPattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
            boolean isFloat = floatPattern.matcher(str).matches();
            return isFloat;
        }
        return isInteger;
    }

    /**
     * 字符串只包含字母数字
     *
     * @param string
     * @return
     */
    public static boolean isLetterOrDigits(String string) {
        boolean flag = false;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLowerCase(string.charAt(i))
                    || Character.isUpperCase(string.charAt(i))
                    || Character.isDigit(string.charAt(i))) {
                flag = true;
            } else {
                flag = false;
                return flag;
            }
        }
        return flag;

    }

    /**
     * 按位赋值，返回新值<br>
     * <pre>
     * 1:登录进行谷歌安全认证
     * 2:邮箱绑定
     * 3：是否短信验证
     * 4:是否谷歌验证
     * 5:免支付密码
     * 6：免二次验证码
     * 7:付款进行邮件验证
     * </pre>
     *
     * @param value 旧值
     * @param bit   指定位序号，从1开始
     * @param index 指定位的值，0或1
     * @return 新值
     * @date 2016-4-18
     */
    public static long setBinaryIndex(long value, int bit, int index) {
        if (getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value = value - (1L << bit - 1);
            }
        } else {
            if (index == 1) {
                value = value | (1L << bit - 1);
            }
        }
        return value;
    }

    public static int setBinaryIndex(int value, int bit, int index) {
        if (getBinaryIndex(value, bit) == 1) {
            if (index == 0) {
                value = value - (1 << bit - 1);
            }
        } else {
            if (index == 1) {
                value = value | (1 << bit - 1);
            }
        }
        return value;
    }

    /**
     * 按位取值，返回指定位的值<br>
     *
     * @param value
     * @param bit   指定位序号，从1开始
     * @return
     * @date 2016-4-18
     */
    public static long getBinaryIndex(long value, int bit) {
        long remainder = 0;
        for (int i = 0; i < bit; i++) {
            long factor = value / 2;
            remainder = value % 2;
            if (factor == 0) {
                if (i < bit - 1) {
                    remainder = 0;
                }
                break;
            }
            value = factor;
        }
        return remainder;
    }

    /**
     * <p>取相应位置的值<p/>
     * 当对应位置是1时返回 true，否则返回 false
     * @param value
     * @param bit 指定位序号，从1开始
     * @return
     */
    public static boolean getBinaryIndexForBoolean(long value, int bit) {
        long bitValue = 1 << (bit - 1);
        if ((value & bitValue) == bitValue) {
            return true;
        }
        return false;
    }

    public static int getBinaryIndex(int value, int bit) {
        int remainder = 0;
        for (int i = 0; i < bit; i++) {
            int factor = value / 2;
            remainder = value % 2;
            if (factor == 0) {
                if (i < bit - 1) {
                    remainder = 0;
                }
                break;
            }
            value = factor;
        }
        return remainder;
    }

    /**
     * 获取登录密码强度,并返回其位数
     *
     * @param value
     * @param pwdType 0:登录密码 1:资金密码
     * @return
     */
    public static int getBinaryIndexPwdFlag(int value, int pwdType) {
        if (pwdType == 0) {
            for (int i = 1; i <= 3; i++) {
                if (StringUtil.getBinaryIndex(value, i) == 1) {
                    return i;
                }
            }
        } else {
            for (int i = 4; i <= 6; i++) {
                if (StringUtil.getBinaryIndex(value, i) == 1) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * 设置登录密码强度,并返回其新值
     *
     * @param value
     * @param pwdType 0:登录密码 1:资金密码
     * @return
     */
    public static long setBinaryIndexPwdFlag(long value, int bit, int index, int pwdType) {
        if (pwdType == 0) {
            for (int i = 1; i <= 3; i++) {
                value = StringUtil.setBinaryIndex(value, i, index ^ 1);
            }
        } else {
            for (int i = 4; i <= 6; i++) {
                value = StringUtil.setBinaryIndex(value, i, index ^ 1);
            }
        }
        return StringUtil.setBinaryIndex(value, bit, index);
    }

    /**
     * 检查密码强度
     */
    public static int checkPasswordStrong(String password) {
        int modes = 0;
        for (int i = 0; i < password.length(); i++) {
            //测试每一个字符的类别并统计一共有多少种模式
            modes |= charMode(password.charAt(i));
        }
        return bitTotal(modes);
    }

    /**
     * 验证字符是属于哪一种类型
     */
    public static int charMode(char c) {
        if (c >= 48 && c <= 57) return 1;//数字
        if (c >= 65 && c <= 90) return 2;//大写字母
        if (c >= 97 && c <= 122) return 4;//小写
        else return 8; //特殊字符
    }

    /**
     * 统计字符类型总数
     */
    public static int bitTotal(int num) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if ((num & 1) == 1) {
                count++;
            }
            num >>>= 1;
        }
        return count;
    }

    /**
     * 上传文件是否为允许的后辍名
     */
    public static Boolean isAllowExt(String fileName) {
        String[] allowFileExt = new String[]{".jpg", ".jpeg", ".png"};
        String suffix = fileName.toLowerCase();
        for (String ext : allowFileExt) {
            if (suffix.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证是否为有效身份证号
     *
     * @param idnumber
     * @return
     */
    public static boolean isIdNumber(String idnumber) {
        if (!StringUtil.isEmpty(idnumber)) {
            String regex = "([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}|[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|[Xx]))";
            return idnumber.trim().matches(regex);
        }
        return false;
    }

    public static boolean isNumber(String number) {
        if (!StringUtil.isEmpty(number)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(number).matches();
        }
        return false;
    }

    /**
     * 校验数字包括小数
     *
     * @param number
     * @return
     */
    public static boolean isNumberDecimal(String number) {
        if (isEmpty(number)) {
            return false;
        }
        if (!StringUtil.isEmpty(number)) {
            return number.matches("^[+]?\\d+(\\.\\d+)?$");
        }
        return false;
    }

    /**
     * 根据传入的数字进行位移运算
     *
     * @param scale
     * @return
     */
    public static long getOffsetNum(long scale) {
        Long temp = new Long(scale);
        temp = temp + 2048L;
        temp = temp << 2;
        temp = ~temp;
        temp = temp << 2;
        temp = temp - 2048L;
        temp = ~temp;
        StringBuffer str = new StringBuffer(temp + "");
        int t = 0;
        for (int i = 11 - str.length(); i > 0; i--) {
            str.append(0);
            t++;
        }
        str.append(t);
        return Long.parseLong(str.toString());
    }

    /**
     * 根据传入的数字做反位移运算
     * 限定传入的数字长度为12位大于0的长整型数字
     *
     * @param offsetNum
     * @return
     */
    public static long getOriginNum(long offsetNum) throws Exception {
        StringBuffer str = new StringBuffer(offsetNum + "");
        if (str.length() != 12 || offsetNum <= 0) {//当前限定长度为12位
            throw new Exception("Argument is illegal.");
        }
        int affix = Integer.parseInt(str.substring(str.length() - 1));
        int length = str.length() - 1 - affix;
        long finalOffset = Long.parseLong(str.substring(0, length));
        Long temp = new Long(finalOffset);
        temp = ~temp;
        temp = temp + 2048L;
        temp = temp >> 2;
        temp = ~temp;
        temp = temp >> 2;
        temp = temp - 2048L;
        return temp;
    }

    /**
     * null "" 格式化为""
     *
     * @param value
     * @return ""
     */
    public static String stringBlank(String value) {
        if (value == null || value.equals("")) {
            value = "";
        }
        return value.replaceAll("\r|\n", "");
    }

    /**
     * 手机中间四位替换为 *
     *
     * @param
     * @return
     */
    public static String hidePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 将邮箱@前面的字符替换为*
     *
     * @param email
     * @return
     */
    public static String hideEmail(String email) {
        int index = email.indexOf("@");
        String emailPrefix = email.substring(0, index);
        String eamilSuffix = email.substring(index, email.length());
        StringBuilder hideEmail = new StringBuilder();
        if (emailPrefix.length() > 3) {
            hideEmail.append(emailPrefix.substring(0, 3)).append("***");
        }
        if (emailPrefix.length() <= 3) {
            hideEmail.append("***");
        }
        hideEmail.append(eamilSuffix);
        return hideEmail.toString();
    }

    /**
     * 从身份证号码中提取出生日期
     */
    public static String getBirthDatefromIdNumber(String idNumber) {
        StringBuilder birthDate = new StringBuilder();
        if (isEmpty(idNumber)) {
            return "";
        }

        if (idNumber.length() == 18) {
            birthDate = birthDate.append(idNumber.substring(6, 10)).append("-").append(idNumber.substring(10, 12)).append("-").append(idNumber.substring(12, 14));
        } else if (idNumber.length() == 15) {
            birthDate = birthDate.append("19").append(idNumber.substring(6, 8)).append("-").append(idNumber.substring(8, 10)).append("-").append(idNumber.substring(10, 12));
        } else {
            birthDate = birthDate.append("");
        }
        return birthDate.toString();
    }

    /**
     * 截取银行卡的尾号
     */
    public static String getTailOfBankCard(String bankCard) {
        if (isEmpty(bankCard)) {
            return "";
        }
        // 去除空格
        bankCard = bankCard.replaceAll("&nbsp;", "");
        bankCard = bankCard.replaceAll(" ", "");

        if (bankCard.length() <= 4) {
            return bankCard;
        }

        bankCard = bankCard.substring(bankCard.length() - 4, bankCard.length());

        return bankCard;
    }

    /**
     * 填充字符串: 	1、如果 size<=0 直接返回源字符串
     * 2、返回的结果字符串为null 或者 长度等于size
     * 3、指定填充位置，如果不指定，默认为左填充
     *
     * @param source   源字符串
     * @param size     填充后字符串的总长度
     * @param padStr   填充的字符
     * @param position 0左填充  1右填充
     * @return
     */
    public static String pad(String source, int size, String padStr, int position) {
        if (source == null) {
            source = "";
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int pads = size - source.length();
        if (pads <= 0) {
            return source;
        }
        if (pads < padLen) {
            padStr = padStr.substring(0, pads);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            padStr = new String(padding);
        }
        if (position == 1) {
            return source.concat(padStr);
        } else {
            return padStr.concat(source);
        }
    }

    public static List<String> parseStringToList(String inputString, String parserString) {
        if (inputString == null)
            return null;
        StringTokenizer st = new StringTokenizer(inputString, parserString);
        ArrayList<String> result = new ArrayList<String>(20);
        while (st.hasMoreTokens())
            result.add(st.nextToken().trim());

        return result;
    }

    /**
     * 将字符串的首字母转成大写字母
     *
     * @param str
     * @return
     */
    public static String changefirstCharToUpperCase(String str) {
        StringBuilder stb = new StringBuilder();
        if (!isEmpty(str) && str.matches("^[a-z].*$")) {
            stb.append(String.valueOf(str.charAt(0)).toUpperCase());
            if (str.length() > 0) {
                stb.append(str.substring(1));
            }
            return stb.toString();
        }
        return str;
    }

    /**
     * 逗号分隔字符串转数组，
     *
     * @param sourceStr
     * @param defvalue  字符串为空时的默认返回值
     * @return
     */
    public static String[] getArrayByCommaSplit(String sourceStr, String... defvalue) {
        if (StringUtil.isEmpty(sourceStr)) {
            if (defvalue != null && defvalue.length > 0) {
                return defvalue;
            }
            return null;
        } else {
            return sourceStr.split(",");
        }
    }

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 将json值中包含的""转换为中文双引号
     *
     * @param s
     * @return
     */
    private static String jsonString(String s) {
        char[] temp = s.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            temp[j] = '”';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(temp);
    }

    /**
     * 纠正json数据中包含制表符和值域中包含""的错误结构
     *
     * @param errorJsonStr
     * @return
     */
    public static String getStanderJsonStr(String errorJsonStr) {
        String standerJsonStr = errorJsonStr.replace("\t", "");
        standerJsonStr = jsonString(standerJsonStr);
        return standerJsonStr;
    }

    /**
     * String to Long
     *
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        return toLong(str, null);
    }

    public static Long toLong(String str, Long defaultValue) {
        if (str == null || "null".equals(str) || "".equals(str.trim()))
            return defaultValue;
        try {
            return Long.parseLong(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * String to Integer
     *
     * @param str
     * @return
     */
    public static Integer toInteger(String str) {
        return toInteger(str, 0);
    }

    public static Integer toInteger(String str, Integer defaultValue) {
        if (str == null || "".equals(str.trim()) || !str.matches("^[-+]?[0-9]+$"))
            return defaultValue;
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * String to Double
     *
     * @param str
     * @return
     */
    public static Double toDouble(String str) {
        return toDouble(str, 0d);
    }

    public static Double toDouble(String str, Double defaultValue) {
        if (str == null || "".equals(str.trim()))
            return defaultValue;
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 分割小数点，数组下标0是整数位，1是小数位
     *
     * @param value
     * @return
     */
    public static String[] splitPoint(BigDecimal value) {


        return splitPoint(value, -1);
    }

    /**
     * 分割小数点，数组下标0是整数位，1是小数位
     *
     * @param value
     * @param scale 保留几位小数
     * @return
     */
    public static String[] splitPoint(BigDecimal value, int scale) {

        String[] result = null;

        if (value != null) {
            if (scale > 0) {
                value = ArithmeticUtil.roundDown(value, scale);
            }
            String valueStr = String.valueOf(value.doubleValue());
            result = valueStr.split("[.]");

        }
        return result;
    }

    /**
     * 整数部分千分位，小数点分割
     *
     * @param value
     * @param scale
     * @return
     */
    public static String[] splitPointAndFormat(BigDecimal value, int scale) {
        String[] result = null;
        if (value != null) {
            if (scale > 0) {
                value = ArithmeticUtil.roundDown(value, scale);
            }
            result = convertValueToResult(value, result, scale);
        }
        return result;
    }

    /**
     * 整数部分千分位，小数点分割
     *
     * @param value
     * @param scale
     * @return
     */
    public static String[] splitPointAndFormatTruncate(BigDecimal value, int scale, RoundingMode roundingMode) {
        String[] result = null;
        if (value != null) {
            if (scale > 0) {
                value = value.setScale(scale, roundingMode);
            }
            result = convertValueToResult(value, result, scale);
            for (int i = 0; i < result.length; i++) { //消除","。类似：1,0000
                result[i] = result[i].replaceAll(",", "");
            }
        }
        return result;
    }

    /**
     * decimal转千分位字符串
     *
     * @param value
     * @return
     */
    public static String formatDecimal(BigDecimal value) {

        if (value != null) {

            DecimalFormat df = null;
            //如果没有小数位则加两个0
            if (value.compareTo(BigDecimal.ONE) < 0) {
                df = new DecimalFormat("0.00######");
            } else {
                df = new DecimalFormat(",###.00######");
            }
            return df.format(value);
        } else {
            return null;
        }

    }

    public static String formatDecimalOfNoAdd(BigDecimal value) {
        if (value != null) {

            DecimalFormat df = null;
            //如果没有小数位则加两个0
            if (value.compareTo(BigDecimal.ONE) < 0) {
                df = new DecimalFormat("0.########");
            } else {
                df = new DecimalFormat(",###.########");
            }
            return df.format(value);
        } else {
            return null;
        }
    }

    public static String formatDecimalOfScale(BigDecimal value, int scale) {
        String[] result = null;

        if (value != null) {
            if (scale >= 0) {
                value = ArithmeticUtil.roundDown(value, scale);
            }
            result = coverValueOfNoAdd(value, result, scale);
        }
        StringBuilder retStr = new StringBuilder();
        retStr.append(result[0]);
        if (result.length > 1) {
            retStr.append(".").append(result[1]);
        }
        return retStr.toString();
    }

    /**
     * 获取带有默认String值的String值
     *
     * @param originalStr 原始String值
     * @param defaultStr  默认String值
     * @return
     */
    public static String getValueWithDefault(String originalStr, String defaultStr) {
        return originalStr != null ? originalStr : defaultStr;
    }

    public static BigDecimal toBigDecimal(String value,BigDecimal defaultValue){
        if(StringUtil.isEmpty(value)){
            return defaultValue;
        }
        BigDecimal returnValue=defaultValue;
        try{
            returnValue=new BigDecimal(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
        return returnValue;
    }

    /**
     * JavaScript escape/unescape 编码的 Java 实现
     *
     * @param s
     * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') {                        // + : map to ' '
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '/' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) {         // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else {                            // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            }
            i++;
        }
        return sbuf.toString();
    }

    public static String decode(String str) {
        if (isEmpty(str)) {
            return "";
        }
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            return "";
        }
    }

    private static String[] coverValueOfNoAdd(BigDecimal value, String[] result, int scale) {
        String valueStr = formatDecimalOfNoAdd(value);
        result = valueStr.split("[.]");
        //位数不够用0补齐
        if (result != null && ((result.length > 1 && result[1].length() < scale) || (result.length == 1))) {
            if (result.length == 1 && scale > 0) {
                result = new String[]{result[0], "0"};
            }
            if (result.length > 1) {
                int s = scale - result[1].length();
                for (int i = 0; i < s; i++) {
                    result[1] = result[1] + "0";
                }
            }
        }
        return result;
    }

    private static String[] convertValueToResult(BigDecimal value, String[] result, int scale) {
        String valueStr = formatDecimal(value);
        result = valueStr.split("[.]");
        //位数不够用0补齐
        if (result != null && ((result.length > 1 && result[1].length() < scale) || (result.length == 1))) {
            if (result.length == 1) {
                result = new String[]{result[0], "0"};
            }
            int s = scale - result[1].length();
            for (int i = 0; i < s; i++) {
                result[1] = result[1] + "0";
            }
        }
        return result;
    }
    public static BigDecimal ipToBigDecimal(String strIP) {
        if(strIP.indexOf(":")>0){
            //ipv6
            return new BigDecimal(iPToBigInteger(strIP));
        }else{
            //ipv4
            return new BigDecimal(ipToLong(strIP));
        }
    }

    public static BigInteger iPToBigInteger(String addr) {
        String[] addrList=addr.split("%");
        String[] addrArray = addrList[0].split(":");//a IPv6 adress is of form 2607:f0d0:1002:0051:0000:0000:0000:0004    2607:f0d0:1002:0051::0004
        int length=addrArray.length;
        if(length<8){
            if(addr.indexOf("::")<0){
                return new BigInteger("0");
            }
            length=8;
        }else if(length>8){
            return new BigInteger("0");
        }
        long[] num = new long[length];
        int j=0;
        boolean isFill=false;
        for (int i=0; i<addrArray.length; i++) {
            String d=addrArray[i];
            if(d.length()<=0){
                isFill=true;
                num[j] = 0;
            }else {
                if(isFill){
                    int fillLength=length-addrArray.length;
                    for(int k=0;k<fillLength;k++){
                        num[j] = 0;
                        j++;
                    }
                }
                num[j] = Long.parseLong(d, 16);
                isFill=false;
            }
            j++;
        }
        if(isFill){
            int fillLength=length-addrArray.length;
            for(int k=0;k<fillLength;k++){
                num[j] = 0;
                j++;
            }
        }
        BigInteger bigInteger=BigInteger.valueOf(num[0]);
        for (int i=1;i<length;i++) {
            bigInteger=bigInteger.shiftLeft(16).add(BigInteger.valueOf(num[i]));
        }
        return bigInteger;
    }

    public static String numberToIP(BigDecimal ipNumber) {
        BigInteger ip = ipNumber.toBigInteger();
        if(ipNumber.precision()<=10) {
            return longToip(ip.longValue());
        }
        String ipString = "";
        BigInteger a = new BigInteger("FFFF", 16);
        for (int i = 0; i < 8; i++) {
            ipString = ip.and(a).toString(16) + ":" + ipString;
            ip = ip.shiftRight(16);
        }
        return ipString.substring(0, ipString.length() - 1);
    }

    /**
     * 将字符串格式化后 HTML 代码转成原始字符输出
     * 只有html编辑器中修改或输入表单中的修改需要调此函数
     *
     * @param str 格式化后的字符串
     * @return 原始的字符串
     */

    private static String outputToOrgStr(String str) {
        if (StringUtil.isEmpty(str)) return null;
        str = replaceStr(str, "&lt;", "<");
        str = replaceStr(str, "&gt;", ">");
        str = replaceStr(str, "&#47;", "/");
        str = replaceStr(str, "&#92;", "\\");
        str = replaceStr(str, "<br/>", "\r\n");
        str = replaceStr(str, "&nbsp;", " ");
        str = replaceStr(str, "&acute;", "'");
        str = replaceStr(str, "&quot;", "\"");
        //str = replaceStr(str, "&#46;",".");
        str = replaceStr(str, "&#40;", "(");
        str = replaceStr(str, "&#41;", ")");

//	    str = replaceStr(str, "<br>","\r\n");
//	    str = replaceStr(str, "&cedil;",".");
//	    str = replaceStr(str, "    ","\t" );
        return str;
    }

    public static void main(String[] args) {

        System.out.println(getBinaryIndex(8,4));
    }
}