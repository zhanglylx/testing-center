package com.testing.center.web.contoller.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.test_auto.TestAutoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testAuto")
public class TestAutoContoller {
    @Autowired
    private TestAutoGroupService testAutoGroupService;

    @GetMapping(value = "/groupFindAll", produces = "application/json;charset=UTF-8")
    public TestingCenterResult<List<Map<String, Object>>> findAll() {
        return testAutoGroupService.findAll();
    }
}
