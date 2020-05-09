package com.testing.center.common.utils;

import org.apache.commons.lang3.StringUtils;

public class ParameterInspect {
    public static void stringIsBlank(String str) {
        stringIsBlank(str, "");
    }

    public static void stringIsBlank(String str, String msg) {
        if (msg == null) msg = "";
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(msg);
        }
    }

}
