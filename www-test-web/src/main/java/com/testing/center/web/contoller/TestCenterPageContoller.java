package com.testing.center.web.contoller;

import com.testing.center.web.common.utils.http.HttpUtils;
import com.testing.center.web.common.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestCenterPageContoller {
    private Logger logger = LoggerFactory.getLogger(TestCenterPageContoller.class);

    @RequestMapping("/home")
    public String getTestHomePage(HttpServletRequest httpRequest) {
        this.logger.info(ServletUtils.getUserIP(httpRequest) + "访问了页面");
        return "test-home";
    }

    @RequestMapping("/address/test-address")
    public String getAddressPage() {
        return "address/test-address";
    }

    @RequestMapping("cdn/cdn-volume")
    public String getCdnVolume() {
        return "cdn/cdn-volume";
    }

    @RequestMapping("ad/ad-lua-funnel")
    public String getAdLuaFunnel() {
        return "ad/ad-lua-funnel";
    }

    @RequestMapping("bookResourceCentre/bookManagement/addChannelBooks")
    public String getAddChannelBooks() {
        return "book_resource_centre/add-channel-books";
    }

    @RequestMapping("bookResourceCentre/bookManagement/searchBookCenter")
    public String getSearchBookCenter() {
        return "book_resource_centre/search-book-center";
    }

    @RequestMapping("testAuto/testAuto")
    public String getTestAuto() {
        return "test_auto/test-auto";
    }

    @RequestMapping("other/businessLift")
    public String getBusinessLift() {
        return "other/business-lift";
    }
}
