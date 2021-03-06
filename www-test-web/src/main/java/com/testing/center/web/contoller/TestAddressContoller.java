package com.testing.center.web.contoller;


import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.TestAddressClassifyService;
import com.testing.center.web.service.TestAddressListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test_address")
public class TestAddressContoller {
    @Resource(name = "testAddressClassifyService")
    private TestAddressClassifyService testAddressClassifyService;
    @Resource(name = "testAddressListService")
    private TestAddressListService testAddressListService;

    @RequestMapping(value = "/findAllClassify", produces = "application/json;charset:UTF-8")
    @ResponseBody
    public TestingCenterResult<List<Map>> findAllClassify() {
        return testAddressClassifyService.findAllClassify();
    }

    @RequestMapping(value = "/findAddressListByClassifyId", method = RequestMethod.POST, produces = "application/json;charset:UTF-8")
    @ResponseBody
    public TestingCenterResult<List<Map>> findAddressListByClassifyId(@RequestParam("classifyId") Integer classifyId) {
        return testAddressListService.findTestAddressListByClassifyId(classifyId);
    }
}
