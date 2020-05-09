package com.testing.center.service;

import com.testing.center.common.utils.TestingCenterResult;
import com.testing.center.entity.ToolBox;
import java.util.List;

public interface ToolBoxService {
    TestingCenterResult<List<ToolBox>> findAll();
}
