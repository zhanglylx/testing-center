package com.testing.center.service.impl;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.dao.ToolBoxDaoMapper;
import com.testing.center.entity.ToolBox;
import com.testing.center.service.ToolBoxService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("toolBoxService")
public class ToolBoxServiceImpl implements ToolBoxService {
    @Resource(name = "toolBoxDaoMapper")
    private ToolBoxDaoMapper toolBoxDaoMapper;

    public TestingCenterResult<List<ToolBox>> findAll() {
        TestingCenterResult<List<ToolBox>> toolBoxTestingCenterResult = new TestingCenterResult<List<ToolBox>>();
        List<ToolBox> list = toolBoxDaoMapper.findAll();
        if (list == null) {
            return toolBoxTestingCenterResult.errorCommon("没有找到可用的工具组");
        }
        toolBoxTestingCenterResult.setData(list);
        toolBoxTestingCenterResult.setStatus(0);
        return toolBoxTestingCenterResult;
    }
}
