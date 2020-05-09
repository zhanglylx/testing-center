package com.testing.center.service;

import com.testing.center.common.utils.TestingCenterResult;

import java.util.List;
import java.util.Map;

public interface TestAddressListService {
    TestingCenterResult<List<Map>> findTestAddressListByClassifyId(Integer classifyId);
}
