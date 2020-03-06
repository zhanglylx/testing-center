package com.testing.center.service;

import com.testing.center.cmmon.utils.TestingCenterResult;

import java.util.List;
import java.util.Map;

public interface ToolListService {
    TestingCenterResult<List<Map>> loadAllToolList();

    TestingCenterResult<List<Map>> loadByBoxIdToolList(Integer boxId);

    TestingCenterResult addHeat(Integer toolListId);
}
