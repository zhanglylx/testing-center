package com.testing.center.web.contoller.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.test_auto.TestAutoGroupService;
import com.testing.center.web.service.test_auto.TestAutoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testAuto")
@Validated
public class TestAutoContoller {
    @Autowired
    private TestAutoGroupService testAutoGroupService;

    @Autowired
    private TestAutoListService testAutoListService;

    @GetMapping(value = "/groupFindAll", produces = "application/json;charset=UTF-8")
    public TestingCenterResult<List<Map<String, Object>>> findAll() {
        return testAutoGroupService.findAll();
    }

    @PostMapping(value = "list/findByGroupId", produces = "application/json;charset=UTF-8")
    public TestingCenterResult<List<Map<String, Object>>> findByGroupId(
           @Min(0)  @RequestParam("testAutoGroupId") Integer testAutoGroupId
    ) {
        return testAutoListService.findByGroupId(testAutoGroupId);
    }
}
