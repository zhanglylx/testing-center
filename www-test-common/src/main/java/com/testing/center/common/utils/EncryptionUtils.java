package com.testing.center.common.utils;


import com.testing.center.common.utils.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类
 */
public class EncryptionUtils {

    public static byte[] getMd5Byte(Object... str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
        char[] charArray = StringUtils.join(str).toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        return md5.digest(byteArray);
    }

    public static String getMD5Str(Object... str) {
        return getAlgorithmStr(false, "MD5", StandardCharsets.UTF_8, str);
    }

    public static String getSHA256Str(Object... str) {
        return getAlgorithmStr(false, "SHA-256", StandardCharsets.UTF_8, str);
    }


    /**
     * 获取指定算法的字符串
     *
     * @param str
     * @param urlEndecode 是否需要url转码
     * @return
     */
    public static String getAlgorithmStr(boolean urlEndecode, String algorithm, Charset encoder, Object... str) {
        try {
            String string = StringUtils.join(str);
            if (urlEndecode) string = HttpUtils.getEncoderString(string, encoder);
            MessageDigest md5 = MessageDigest.getInstance(algorithm);
            md5.update(string.getBytes(StandardCharsets.UTF_8));
            byte[] messageDigest = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String t = Integer.toHexString(0xFF & messageDigest[i]);
                if (t.length() == 1) {
                    hexString.append("0").append(t);
                } else {
                    hexString.append(t);
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
