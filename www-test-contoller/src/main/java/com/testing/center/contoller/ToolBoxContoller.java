package com.testing.center.contoller;

import com.testing.center.common.utils.TestingCenterResult;
import com.testing.center.entity.ToolBox;
import com.testing.center.service.ToolBoxService;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/toolBox")
@Validated
public class ToolBoxContoller {
    @Resource(name = "toolBoxService")
    private ToolBoxService toolBoxService;

    @RequestMapping("/findAll")
    public TestingCenterResult<List<ToolBox>> findAllToolBox() {
        return toolBoxService.findAll();
    }


}
