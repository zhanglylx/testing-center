package com.testing.center.contoller;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.service.ToolListService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RequestMapping("/toolList")
@Controller
public class ToolListContoller {
    @Resource(name = "toolListService")
    private ToolListService toolListService;

    @RequestMapping(value = "/findAll", produces = "application/json;charset:utf-8")
    @ResponseBody
    public TestingCenterResult<List<Map>> loadToolListAll() {
        return toolListService.loadAllToolList();
    }

    @RequestMapping(value = "/findByBoxId", method = RequestMethod.POST, produces = "application/json;charset:utf-8")
    @ResponseBody
    public TestingCenterResult<List<Map>> loadByBoxIdToolList(@RequestParam("boxId") Integer boxId) {
        return toolListService.loadByBoxIdToolList(boxId);
    }

    /**
     * 添加热度
     *
     * @param toolListId listId
     * @return
     */
    @RequestMapping(value = "/addHeat", method = RequestMethod.POST, produces = "application/json;charset:utf-8")
    @ResponseBody
    public TestingCenterResult addHeat(@RequestParam("toolListId") Integer toolListId) {
        return toolListService.addHeat(toolListId);
    }

}
