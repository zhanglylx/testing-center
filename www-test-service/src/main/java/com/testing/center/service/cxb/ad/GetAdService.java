package com.testing.center.service.cxb.ad;

import com.testing.center.common.utils.TestingCenterResult;
import com.testing.center.entity.cxb.ad.GetAd;

public interface GetAdService {
    TestingCenterResult<GetAd> getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment);
}
