package com.testing.center.web.service.cxb.ad;


import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.cxb.ad.GetAd;

public interface GetAdService {
    TestingCenterResult<GetAd> getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment);
}
