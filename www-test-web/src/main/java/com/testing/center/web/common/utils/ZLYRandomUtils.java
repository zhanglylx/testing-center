package com.testing.center.web.common.utils;



import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机工具
 */
public class ZLYRandomUtils {
    public static String getRandomString() {
        return JavaUtils.currentTimeMillisString() + ZLYStringUtils.getUUID();
    }

    /**
     * @param lenth
     * @return 返回指定长度的字符串，包含数字，字母，特殊字符
     */
    public static String getRandomPassword(int lenth) {
        StringBuilder stringBuilder = new StringBuilder();
        Object s;
        for (int i = 0; i < lenth; i++) {
            s = getRandomValues(getRandomEnglish(), getRandomSpecialCharacter(), getRandomNumbers());
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String getRandomPassword(int min, int max) {
        return getRandomPassword(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    public static Object getRandomValues(Object... o) {
        return o[ZLYRandNumberUtils.getRandomNumbers(0, o.length)];
    }

    /**
     * 随机生成指定返回随机个数的中文
     *
     * @param min
     * @param max
     * @return
     */
    public static String getRandomChinese(int min, int max) {
        return getRandomChinese(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    /**
     * 常见的中文字符
     *
     * @param number 数量
     * @return
     */
    public static String getRandomChinese(int number) {
        if (number < 0) throw new IllegalArgumentException("参数小于0");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < number; i++) {
            stringBuilder.append(getRandomChinese());
        }
        return stringBuilder.toString();
    }

    /**
     * 常见的中文字符
     *
     * @return
     */
    public static String getRandomChinese() {
        String str = null;
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }// 转成中文
        return str;
    }

    public static String getRandomEnglish(int number) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < number; i++) {
            stringBuilder.append(getRandomEnglish());
        }
        return stringBuilder.toString();
    }

    public static String getRandomEnglish(int min, int max) {
        return getRandomEnglish(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    /**
     * 混出英文和中文，长度根据指定的大小随机生成长度
     *
     * @param min 字符串最小长度
     * @param max 字符串最大长度
     * @return
     */
    public static String getRandomMixtureChinese_English(int min, int max) {
        return getRandomMixtureChinese_English(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    /**
     * 混出英文、中文、数字
     *
     * @param min 字符串最小长度
     * @param max 字符串最大长度
     * @return 根据最小和最大长度随机生成长度的英文、中文、数字的字符串
     */
    public static String getRandomMixtureChinese_English_Number(int min, int max) {
        return getRandomMixtureChinese_English_Number(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    public static String getRandomNumbers() {
        return String.valueOf(ZLYRandNumberUtils.getRandomNumbers(0, 10));
    }

    public static String getRandomMixtureEnglish_Number(int min, int max) {
        return getRandomMixtureEnglish_Number(ZLYRandNumberUtils.getRandomNumbers(min, max));
    }

    public static String getRandomMixtureEnglish_Number(int len) {
        if (len < 0) throw new IllegalArgumentException("len小于0");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < len; i++) {
            stringBuilder.append(getRandomValues(getRandomNumbers(), getRandomEnglish()));
        }
        return stringBuilder.toString();
    }

    /**
     * 混出英文、中文、数字
     *
     * @param number 字符串长度
     * @return
     */
    public static String getRandomMixtureChinese_English_Number(int number) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < number; i++) {
            stringBuilder.append(getRandomValues(
                    getRandomEnglish()
                    , getRandomNumbers()
                    , getRandomChinese()
            ));
        }
        return stringBuilder.toString();
    }


    /**
     * 混出英文和中文
     *
     * @param number
     * @return
     */
    public static String getRandomMixtureChinese_English(int number) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < number; i++) {
            if (ZLYRandNumberUtils.getRandomNumbers(0, 2) == 0) {
                stringBuilder.append(getRandomChinese());
            } else {
                stringBuilder.append(getRandomEnglish());
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 随机产生英文
     *
     * @return 返回一个英文字符串，大小写随机
     */
    public static String getRandomEnglish() {
        String str;
        str = (char) (Math.random() * 26 + 'A') + "";
        if (ZLYRandNumberUtils.getRandomNumbers(0, 2) == 0) str = str.toLowerCase();
        return str;
    }


    /**
     * 获取随机的特殊字符
     *
     * @return 只返回为英文的特殊字符
     */
    public static String getRandomSpecialCharacter() {
        char[] chars = ("`~!@#$%^&*()_-+=/*,.<>][{}\\|/?;:'\"").toCharArray();
        return String.valueOf(chars[ZLYRandomUtils.getRandomArrayIndex(chars)]);
    }


    /**
     * 获取集合的随机内容
     *
     * @param o
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRandomArray(Object o) {
        if (o instanceof List) return (T) ((List) o).get(getRandomArrayIndex(o));
        if (o instanceof Map) return (T) ((Map) o).get(getRandomArrayIndex(o));
        if (o.getClass().isArray()) return (T) Array.get(o, getRandomArrayIndex(o));
        if (o instanceof ZLYJSONArray) return (T) ((ZLYJSONArray) o).get(getRandomArrayIndex(o));
        throw new IllegalArgumentException("不支持的类型");
    }

    /**
     * 获取集合的随机索引
     *
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Integer getRandomArrayIndex(Object o) {
        if (o instanceof List) return ZLYRandNumberUtils.getRandomNumbers(0, ((List) o).size());
        if (o instanceof Map) return ZLYRandNumberUtils.getRandomNumbers(0, ((Map) o).size());
        if (o.getClass().isArray()) return ZLYRandNumberUtils.getRandomNumbers(0, Array.getLength(o));
        if (o instanceof ZLYJSONArray) return ZLYRandNumberUtils.getRandomNumbers(0, ((ZLYJSONArray) o).size());
        throw new IllegalArgumentException("不支持的类型");
    }
}
