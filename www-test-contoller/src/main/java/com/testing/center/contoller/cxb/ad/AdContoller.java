package com.testing.center.contoller.cxb.ad;

import com.testing.center.common.utils.TestingCenterResult;
import com.testing.center.entity.cxb.ad.GetAd;
import com.testing.center.service.cxb.ad.GetAdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/ad")
public class AdContoller {
    @Resource(name = "getAdService")
    private GetAdService getAdService;

    @RequestMapping("/adLuaFunnel")
    @ResponseBody
    public TestingCenterResult<GetAd> getAdLuaFunnel(
            String net,
            Integer uid,
            String version,
            String adGG,
            String appname,
            Integer cnid,
            String packname,
            Integer environment) {
        return getAdService.getAdLuaFunnel(net, uid, version, adGG, appname, cnid, packname, environment);
    }
}
