package com.testing.center.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestCenterPageContoller {
    @RequestMapping("/home")
    public String getTestHomePage() {
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
}
