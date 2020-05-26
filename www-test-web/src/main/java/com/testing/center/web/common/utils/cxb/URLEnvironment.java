package com.testing.center.web.common.utils.cxb;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class URLEnvironment {
    public static final int QA = 0;
    public static final int ONLINE = 1;
    private static Properties properties;


    @Value("#{URLEnvironmentProperties}")
    public void setProperties(Properties properties) {
        URLEnvironment.properties = properties;
    }

    /**
     * 切换线上,默认为qa环境，如果switchOnline为qa，将不做任何转换直接返回
     *
     * @param url
     * @param environment 0:qa 直接返回,1:线上，替换-qa
     * @return
     */
    public static String contextSwitching(String url, Integer environment) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("url不能为空");
        }
        if (environment == null) {
            throw new NullPointerException("switchOnline不能为空");
        }
        switch (environment) {
            case QA:
                return url;
            case ONLINE:
                return url.replace("-qa", "");
            default:
                throw new IllegalArgumentException("不支持的类型,：" + environment);
        }
    }

    /**
     * 切换线上,默认为qa环境，如果switchOnline为qa，将不做任何转换直接返回
     *
     * @param urlQa
     * @param urlOnline
     * @param environment 0:urlQa 1:urlOnline
     * @return
     */
    public static String contextSwitching(String urlQa, String urlOnline, Integer environment) {
        if (StringUtils.isBlank(urlQa)) {
            throw new NullPointerException("urlQa不能为空");
        }
        if (StringUtils.isBlank(urlOnline)) {
            throw new NullPointerException("urlOnline不能为空");
        }
        if (environment == null) {
            throw new NullPointerException("switchOnline不能为空");
        }
        switch (environment) {
            case QA:
                return urlQa;
            case ONLINE:
                return urlOnline;
            default:
                throw new IllegalArgumentException("不支持的类型,：" + environment);
        }
    }

    public static String contextSwitchingWebApp(String path, Integer environment) {
        return contextSwitchingProperties(path, environment, "cxb_webapp_qa_Host", "cxb_webapp_Online_Host");
    }

    public static String contextSwitchingReadBook(String path, Integer environment) {
        return contextSwitchingProperties("readbook", path, environment);
    }

    public static String contextSwitchingZwscad(String path, Integer environment) {
        return contextSwitchingProperties(path, environment, "zwscad_qa", "zwscad_online");
    }

    private static String contextSwitchingProperties(String qaName, String path, Integer environment) {
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("path不能为空");
        }
        path = path.trim().startsWith("/") ? path : "/" + path;
        return contextSwitching(properties.getProperty(qaName) + path, environment);
    }

    private static String contextSwitchingProperties(String path, Integer environment, String qaName, String onlineName) {
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("path不能为空");
        }
        if (environment == null) {
            throw new NullPointerException("switchOnline不能为空");
        }
        path = path.trim().startsWith("/") ? path : "/" + path;
        switch (environment) {
            case QA:
                return properties.getProperty(qaName) +
                        path;
            case ONLINE:
                return properties.getProperty(onlineName) + path;
            default:
                throw new IllegalArgumentException("不支持的类型,：" + environment);
        }
    }

}
