package com.testing.center.service.cxb.ad.impl;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.cxb.ad.GetAdDaoMapper;
import com.testing.center.entity.cxb.ad.GetAd;
import com.testing.center.service.cxb.ad.GetAdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("getAdService")
public class GetAdServiceImpl implements GetAdService {
    @Resource(name = "getAdDaoMapper")
    private GetAdDaoMapper getAdDaoMapper;

    @Override
    public TestingCenterResult<GetAd> getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment) {
        TestingCenterResult<GetAd> testingCenterResult = new TestingCenterResult<>();
        if (StringUtils.isBlank(net)) {
            testingCenterResult.errorParameterDefaultNull("net");
            return testingCenterResult;
        }
        if (userId == null) {
            testingCenterResult.errorParameterDefaultNull("userId");
            return testingCenterResult;
        }
        if (StringUtils.isBlank(version)) {
            testingCenterResult.errorParameterDefaultNull("version");
            return testingCenterResult;
        }
        if (advId == null) {
            testingCenterResult.errorParameterDefaultNull("advId");
            return testingCenterResult;
        }
        if (StringUtils.isBlank(appname)) {
            testingCenterResult.errorParameterDefaultNull("appname");
            return testingCenterResult;
        }
        if (cnid == null) {
            testingCenterResult.errorParameterDefaultNull("cnid");
            return testingCenterResult;
        }
        if (StringUtils.isBlank(packname)) {
            testingCenterResult.errorParameterDefaultNull("packname");
            return testingCenterResult;
        }
        if (environment == null) {
            testingCenterResult.errorParameterDefaultNull("environment");
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("查询成功", getAdDaoMapper.getAdLuaFunnel(
                net, userId, version, advId, appname, cnid, packname, environment));
        return testingCenterResult;

    }
}
