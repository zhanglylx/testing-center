package com.testing.center.web.contoller.cxb.cache;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.cxb.cache.ClearCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clearCache")
public class ClearCacheContoller {
    @Autowired
    private ClearCacheService clearCacheService;

    @RequestMapping("/all")
    TestingCenterResult<Object> clearCache() {
        return clearCacheService.clearCache();
    }
}
