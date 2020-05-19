package com.testing.center.web.contoller;



import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.ToolBox;
import com.testing.center.web.service.ToolBoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/toolBox")
@Validated
public class ToolBoxContoller {
    private final Logger logger = LoggerFactory.getLogger(ToolBoxContoller.class);
    @Resource(name = "toolBoxService")
    private ToolBoxService toolBoxService;

    @RequestMapping("/findAll")
    public TestingCenterResult<List<ToolBox>> findAllToolBox() {
        return toolBoxService.findAll();
    }


}
