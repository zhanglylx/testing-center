package com.testing.center.web.service.test_auto.impl;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.dao.test_auto.TestAutoListDaoMapper;
import com.testing.center.web.service.test_auto.TestAutoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestAutoListServiceImpl implements TestAutoListService {

    @Autowired
    private TestAutoListDaoMapper testAutoListDaoMapper;


    @Override
    public TestingCenterResult<List<Map<String, Object>>> findByGroupId(Integer testAutoGroupId) {
        TestingCenterResult<List<Map<String, Object>>> testingCenterResult = new TestingCenterResult<>();
        List<Map<String, Object>> list = testAutoListDaoMapper.findByGroupId(testAutoGroupId);
        testingCenterResult.setSuccess(list);
        return testingCenterResult;
    }
}

