package com.testing.center.web.service.test_auto.impl;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.dao.test_auto.TestAutoGroupDaoMapper;
import com.testing.center.web.service.test_auto.TestAutoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TestAutoGroupServiceImpl implements TestAutoGroupService {

    @Resource(name = "testAutoGroupDaoMapper")
    private TestAutoGroupDaoMapper testAutoGroupDaoMapper;

    @Override
    public TestingCenterResult<List<Map<String, Object>>> findAll() {
        TestingCenterResult<List<Map<String, Object>>> testingCenterResult = new TestingCenterResult<>();
        testingCenterResult.setSuccess("查询成功", testAutoGroupDaoMapper.findAll());
        return testingCenterResult;
    }
}
