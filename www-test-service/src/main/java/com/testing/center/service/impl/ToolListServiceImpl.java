package com.testing.center.service.impl;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.dao.ToolListDaoMapper;
import com.testing.center.service.ToolListService;
import org.apache.commons.lang3.StringUtils;
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
        return getListTestingCenterResult(toolListDaoMapper.findAll());
    }

    @Override
    public TestingCenterResult<List<Map>> loadByBoxIdToolList(Integer boxId) {
        TestingCenterResult<List<Map>> testingCenterResult = new TestingCenterResult<>();
        if (boxId == null) {
            testingCenterResult.errorParameter("boxId不能为空");
            return testingCenterResult;
        }

        return getListTestingCenterResult(toolListDaoMapper.findByBoxId(boxId));
    }

    private TestingCenterResult<List<Map>> getListTestingCenterResult(List<Map> list) {
        TestingCenterResult<List<Map>> testingCenterResult = new TestingCenterResult<>();
        if (list == null || list.size() == 0) {
            testingCenterResult.errorCommon("没有相关的测试工具记录");
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("查询成功", list);
        return testingCenterResult;
    }

    @Override
    public TestingCenterResult addHeat(Integer toolListId) {
        TestingCenterResult testingCenterResult = new TestingCenterResult();
        if (toolListId == null) {
            testingCenterResult.errorParameter("toolListId不能为空");
            return testingCenterResult;
        }
        int r = toolListDaoMapper.addHeat(toolListId);
        if (r != 1) {
            testingCenterResult.errorCommon("更新失败，更新条数:" + r);
            return testingCenterResult;
        }
        testingCenterResult.setSuccess("添加成功");
        return testingCenterResult;
    }
}
