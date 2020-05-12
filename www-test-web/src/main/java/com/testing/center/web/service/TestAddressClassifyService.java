package com.testing.center.web.service;


import com.testing.center.web.common.utils.TestingCenterResult;

import java.util.List;
import java.util.Map;

public interface TestAddressClassifyService {
    TestingCenterResult<List<Map>> findAllClassify();
}
