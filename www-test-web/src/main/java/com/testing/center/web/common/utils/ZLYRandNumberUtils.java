package com.testing.center.web.common.utils;

import java.security.SecureRandom;

public class ZLYRandNumberUtils {
    /**
     * 获取随机数,包前不包后
     *
     * @param min 最小值
     * @param max 最大值
     * @return 最小值到最大值减一的随机数
     */

    public static int getRandomNumbers(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("min >= max: min=" + min + " max=" + max);
        return new SecureRandom().nextInt(max) % (max - min) + min;
    }

    public static int getRandomNumbers(int max) {
        return getRandomNumbers(0,max);
    }

    /**
     * 获取指定位数的随机串
     *
     * @param lenth
     * @return
     */
    public static long getRandomSpecifyNumbers(int lenth) {
        if (lenth < 1) throw new IllegalArgumentException("lenth 小于0，lenth :" + lenth);
        if (lenth > 19) throw new IllegalArgumentException("lenth 大于19位数，lenth :" + lenth);
        return (long) ((Math.random() * 9 + 1) * Math.pow(10, lenth - 1));
    }




    public static long getRandomPhone() {
        return (long) Math.pow(10, 10) + getRandomSpecifyNumbers(10);
    }


}
