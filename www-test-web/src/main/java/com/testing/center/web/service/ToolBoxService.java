package com.testing.center.web.service;


import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.ToolBox;

import java.util.List;

public interface ToolBoxService {
    TestingCenterResult<List<ToolBox>> findAll();
}
