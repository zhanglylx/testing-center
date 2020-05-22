package com.testing.center.web.service.test_auto;

import com.testing.center.web.common.utils.TestingCenterResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.List;

public interface TestAutoCaseRunService {
    TestingCenterResult<Object> execute(Integer listId);

    TestingCenterResult<List<String>> getLog();

    TestingCenterResult<Object> stop();

    File getCaseLog() ;
}
