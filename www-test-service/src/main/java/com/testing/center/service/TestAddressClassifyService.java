package com.testing.center.service;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.ToolBox;

import java.util.List;
import java.util.Map;

public interface TestAddressClassifyService {
    TestingCenterResult<List<Map>> findAllClassify();
}
