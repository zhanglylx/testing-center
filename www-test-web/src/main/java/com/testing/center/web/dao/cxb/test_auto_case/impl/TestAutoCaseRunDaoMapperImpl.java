package com.testing.center.web.dao.cxb.test_auto_case.impl;

import java.io.*;


import com.testing.center.web.common.utils.MailClent;
import com.testing.center.web.common.utils.WindosUtils;
import com.testing.center.web.common.utils.ZipUtils;
import com.testing.center.web.dao.cxb.test_auto_case.TestAutoCaseRunDaoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("testAutoCaseRunDaoMapper")
public class TestAutoCaseRunDaoMapperImpl implements TestAutoCaseRunDaoMapper {
    @Value("#{test_auto_case.pomPath}")
    private String pomPath;

    @Autowired
    private File logPathFile;

    @Autowired
    private MailClent mailClent;

    private static final Logger LOGGER = LoggerFactory.getLogger(TestAutoCaseRunDaoMapperImpl.class);

    private File fileLogZip = new File("testing_center.zip");

    @Override
    public void execute(String testCaseName, List<String> log) throws IOException, InterruptedException {
        if (StringUtils.isBlank(testCaseName)) throw new IllegalArgumentException("testCaseName is Null");
        if (logPathFile.exists() && !WindosUtils.removeDir(logPathFile)) {
            throw new RuntimeException("历史log文件删除失败了:" + logPathFile);
        }
        if (fileLogZip.exists() && !fileLogZip.delete()) {
            throw new RuntimeException("历史log文件删除失败了:" + fileLogZip);
        }
        WindosUtils.cmd("mvn.cmd package -f " + this.pomPath + "   test -Dtest=" + testCaseName, log);
    }

    @Override
    public boolean sendMailLog(String title, String content) {

        if (!logPathFile.exists()) throw new RuntimeException("没有log");
        if (!ZipUtils.toZip(logPathFile, fileLogZip, true)) {
            if (fileLogZip.exists()) {
                if (!fileLogZip.delete()) {
                    throw new RuntimeException("文件压缩失败，存在历史文件，历史文件删除失败了;");
                } else {
                    LOGGER.info("删除log文件:" + fileLogZip);
                }
            }
            throw new RuntimeException("文件压缩失败了;");
        }

        return mailClent.sendMail(title, content, fileLogZip);
    }

    public File getLog() {
        return fileLogZip;
    }

}
