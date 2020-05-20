package com.testing.center.web.service.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestAutoListService {
    TestingCenterResult<List<Map<String, Object>>> findByGroupId(Integer testAutoGroupId);
}
