package com.testing.center.service.impl;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.dao.ToolListDaoMapper;
import com.testing.center.service.ToolListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("toolListService")
public class ToolListServiceImpl implements ToolListService {
    @Resource(name = "toolListDaoMapper")
    private ToolListDaoMapper toolListDaoMapper;

    @Override
    public TestingCenterResult<List<Map>> loadAllToolList() {
        List<Map> list = toolListDaoMapper.findAll();
        TestingCenterResult<List<Map>> testingCenterResult = new TestingCenterResult<>();
        if (list == null) {
            testingCenterResult.errorCommon("没有相关的测试工具记录");
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("查询成功", list);
        return testingCenterResult;
    }
}
