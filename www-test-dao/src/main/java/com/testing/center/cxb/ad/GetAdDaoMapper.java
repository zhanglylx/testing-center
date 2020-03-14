package com.testing.center.cxb.ad;

import com.testing.center.entity.cxb.ad.GetAd;

public interface GetAdDaoMapper {
    GetAd getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment);
}
