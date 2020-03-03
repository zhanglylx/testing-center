package com.testing.center.service;

import com.testing.center.cmmon.utils.TestingCenterResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestAddressListService {
    TestingCenterResult<List<Map>> findTestAddressListByClassifyId(Integer classifyId);
}
