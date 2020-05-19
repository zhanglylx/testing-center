package com.testing.center.web.service.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;

import java.util.List;
import java.util.Map;

public interface TestAutoGroupService {
    TestingCenterResult<List<Map<String,Object>>> findAll();
}
