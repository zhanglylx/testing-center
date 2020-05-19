package com.testing.center.web.service.cxb.cache.impl;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.cxb.cache.ClearCacheDaoMapper;
import com.testing.center.web.service.cxb.cache.ClearCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("clearCacheService")
public class ClearCacheServiceImpl implements ClearCacheService {

    @Resource(name = "clearCacheDaoMapper")
    private ClearCacheDaoMapper clearCacheDaoMapper;

    @Override
    public TestingCenterResult<Object> clearCache() {
        TestingCenterResult<Object> testingCenterResult = new TestingCenterResult<>();
        try {
            clearCacheDaoMapper.clearCache();
            testingCenterResult.setSuccess("清除成功", null);
            return testingCenterResult;
        } catch (Exception e) {
            return testingCenterResult.errorCommon("执行失败了", e);
        }


    }
}
