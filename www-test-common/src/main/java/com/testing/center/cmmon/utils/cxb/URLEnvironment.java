package com.testing.center.cmmon.utils.cxb;

import org.apache.commons.lang3.StringUtils;

public class URLEnvironment {
    /**
     * 切换线上,默认为qa环境，如果switchOnline为qa，将不做任何转换直接返回
     *
     * @param url
     * @param switchOnline 0:qa,1:线上
     * @return
     */
    public static String contextSwitching(String url, Integer switchOnline) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("url不能为空");
        }
        if (switchOnline == null) {
            throw new NullPointerException("switchOnline不能为空");
        }
        switch (switchOnline) {
            case 0:
                return url;
            case 1:
                return url.replace("-qa", "");
            default:
                throw new IllegalArgumentException("不支持的类型,：" + switchOnline);
        }
    }
}
