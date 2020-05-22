package com.testing.center.web.contoller.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.test_auto.TestAutoCaseRunService;
import com.testing.center.web.service.test_auto.TestAutoGroupService;
import com.testing.center.web.service.test_auto.TestAutoListService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testAuto")
@Validated
public class TestAutoContoller {
    @Autowired
    private TestAutoGroupService testAutoGroupService;

    @Autowired
    private TestAutoListService testAutoListService;

    @Autowired
    private TestAutoCaseRunService testAutoCaseRunService;

    @GetMapping(value = "/groupFindAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestingCenterResult<List<Map<String, Object>>> findAll() {
        return testAutoGroupService.findAll();
    }

    @PostMapping(value = "/list/findByGroupId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestingCenterResult<List<Map<String, Object>>> findByGroupId(
            @Min(0) @RequestParam("testAutoGroupId") Integer testAutoGroupId
    ) {
        return testAutoListService.findByGroupId(testAutoGroupId);
    }


    @PostMapping(value = "/execute", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestingCenterResult<Object> execute(@Min(0) @RequestParam("listId") Integer listId) {
        return testAutoCaseRunService.execute(listId);
    }

    @GetMapping(value = "/list/getLog", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestingCenterResult<List<String>> getLog() {
        return testAutoCaseRunService.getLog();
    }

    @GetMapping(value = "/stop", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestingCenterResult<Object> stop() {
        return testAutoCaseRunService.stop();
    }

    @GetMapping(value = "/getCaseLog")
    public ResponseEntity<Object> getCaseLog() {
        try {
            File file = testAutoCaseRunService.getCaseLog();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".zip");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new FileSystemResource(file));
        } catch (Exception e) {
            e.printStackTrace();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "text/html;charset=UTF-8");
            return ResponseEntity.ok().headers(headers).body("<h4>" + e.getLocalizedMessage() + "</h4>");
        }
    }

}
