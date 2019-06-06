package com.shuiliu.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    /**
     * 传入字符串，返回 SHA-256 加密字符串
     * @return
     */
    public String Hash(final String strText) {
        // 返回值
        String strResult = null;
        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // 创建加密对象，传入要加密类型
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 执行哈希计算，得到 byte 数组
                byte byteBuffer[] = messageDigest.digest();
                // 將 byte 数组转换 string 类型
                StringBuffer strHexString = new StringBuffer();
                // 遍历 byte 数组
                for (int i = 0; i < byteBuffer.length; i++) {
                    // 转换成16进制并存储在字符串中
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
}
