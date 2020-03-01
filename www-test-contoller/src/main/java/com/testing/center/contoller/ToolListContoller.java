package com.testing.center.contoller;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.service.ToolListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/toolList")
@Controller
public class ToolListContoller {
    @Resource(name = "toolListService")
    private ToolListService toolListService;

    @RequestMapping("/findAll")
    @ResponseBody
    public TestingCenterResult<List<Map>> loadToolListAll() {
        return toolListService.loadAllToolList();
    }
}
