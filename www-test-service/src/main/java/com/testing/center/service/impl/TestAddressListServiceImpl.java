package com.testing.center.service.impl;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.dao.TestAddressListDaoMapper;
import com.testing.center.service.TestAddressListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("testAddressListService")
public class TestAddressListServiceImpl implements TestAddressListService {
    @Resource(name = "testAddressListDaoMapper")
    private TestAddressListDaoMapper testAddressListDaoMapper;


    @Override
    public TestingCenterResult<List<Map>> findTestAddressListByClassifyId(Integer classifyId) {
        TestingCenterResult<List<Map>> testingCenterResult = new TestingCenterResult<>();
        if (classifyId == null) {
            testingCenterResult.errorParameter("classifyId不能为空");
            return testingCenterResult;
        }
        List<Map> mapList = testAddressListDaoMapper.findTestAddressListByClassifyId(classifyId);
        if (mapList == null) {
            testingCenterResult.errorCommon("没有查询到可用的数据");
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("查询成功", mapList);
        return testingCenterResult;
    }
}
