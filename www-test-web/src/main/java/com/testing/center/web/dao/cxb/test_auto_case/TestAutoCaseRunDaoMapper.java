package com.testing.center.web.dao.cxb.test_auto_case;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;


public interface TestAutoCaseRunDaoMapper {
    void execute(String testCaseName, List<String> log) throws IOException, InterruptedException;

    boolean sendMailLog(String title, String content);

    File getLog();
}
