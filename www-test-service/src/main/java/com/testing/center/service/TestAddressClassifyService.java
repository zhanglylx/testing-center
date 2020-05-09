package com.testing.center.service;

import com.testing.center.common.utils.TestingCenterResult;

import java.util.List;
import java.util.Map;

public interface TestAddressClassifyService {
    TestingCenterResult<List<Map>> findAllClassify();
}
