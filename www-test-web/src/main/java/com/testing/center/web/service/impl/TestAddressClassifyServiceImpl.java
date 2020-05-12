package com.testing.center.web.service.impl;


import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.dao.TestAddressClassifyDaoMapper;
import com.testing.center.web.service.TestAddressClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("testAddressClassifyService")
public class TestAddressClassifyServiceImpl implements TestAddressClassifyService {
    @Resource(name = "testAddressClassifyDaoMapper")
    private TestAddressClassifyDaoMapper testAddressClassifyDaoMapper;

    @Override
    public TestingCenterResult<List<Map>> findAllClassify() {
        List<Map> mapList = testAddressClassifyDaoMapper.findTestAddressClassify();
        TestingCenterResult<List<Map>> testingCenterResult = new TestingCenterResult<>();
        if (mapList == null || mapList.size() == 0) {
            testingCenterResult.errorCommon("没有可用的数据");
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("查询成功", mapList);
        return testingCenterResult;
    }
}
