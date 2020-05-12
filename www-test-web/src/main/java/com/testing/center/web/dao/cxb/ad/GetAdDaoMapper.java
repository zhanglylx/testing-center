package com.testing.center.web.dao.cxb.ad;

import com.testing.center.web.dao.entity.cxb.ad.GetAd;

public interface GetAdDaoMapper {
    GetAd getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment);
}
