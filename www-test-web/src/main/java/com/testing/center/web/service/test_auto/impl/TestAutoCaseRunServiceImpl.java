package com.testing.center.web.service.test_auto.impl;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.common.utils.ZLYDateUtils;
import com.testing.center.web.dao.cxb.test_auto_case.TestAutoCaseRunDaoMapper;
import com.testing.center.web.dao.dao.test_auto.TestAutoListDaoMapper;
import com.testing.center.web.dao.dao.test_auto.TestAutoSyncDaoMapper;
import com.testing.center.web.dao.entity.test_auto_case.TestAutoSync;
import com.testing.center.web.service.test_auto.TestAutoCaseRunService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;

@Service
public class TestAutoCaseRunServiceImpl implements TestAutoCaseRunService {

    private List<String> executeLog = Collections.synchronizedList(new ArrayList<>());

    @Resource(name = "testAutoCaseRunDaoMapper")
    private TestAutoCaseRunDaoMapper testAutoCaseRunDaoMapper;

    @Autowired
    private TestAutoSyncDaoMapper testAutoSyncDaoMapper;


    @Autowired
    private TestAutoListDaoMapper testAutoListDaoMapper;

    private ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    @Override
    public TestingCenterResult<Object> execute(Integer listId) {
        TestingCenterResult<Object> test = new TestingCenterResult<>();
        String runCaseName = testAutoSyncDaoMapper.findRunning();
        if (runCaseName != null) {
            test.errorCommon("程序正在运行测试用例：" + runCaseName);
            return test;
        }
        Map<String, Object> map = testAutoListDaoMapper.findCaseNameById(listId);
        String casePath = map.get("test_auto_list_case_path").toString();
        if (StringUtils.isBlank(casePath)) {
            test.errorCommon("casePath为空");
            return test;
        }
        casePath = casePath.replace(".", "\\");
        TestAutoSync testAutoSync = new TestAutoSync();
        testAutoSync.setTest_auto_list_id(listId);
        testAutoSync.setTest_auto_sync_status(1);
        Timestamp date = new Timestamp(new Date().getTime());
        testAutoSync.setTest_auto_sync_createtime(date);
        testAutoSync.setTest_auto_sync_updatetime(date);
        testAutoSyncDaoMapper.save(testAutoSync);
        String finalCasePath = casePath;
        if (executorService.getActiveCount() != 0) {
            test.errorCommon("还有没有执行完成的测试用例");
            return test;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    executeLog.clear();
                    executeLog.add("开始执行测试用例【" + map.get("test_auto_list_name") + "】:" + ZLYDateUtils.getStrSysDefaultFormat());
                    testAutoCaseRunDaoMapper.execute(finalCasePath, executeLog);
                    testAutoSync.setTest_auto_sync_status(0);
                    executeLog.add("执行完成【" + map.get("test_auto_list_name") + "】:" + ZLYDateUtils.getStrSysDefaultFormat());
                    boolean b = false;
                    try {
                        b = testAutoCaseRunDaoMapper.sendMailLog("松鼠测试中心给您来邮件拉",
                                "本次执行的测试用例名称为：" + map.get("test_auto_list_name"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    executeLog.add("邮件发送：" + b);
                } catch (IOException e) {
                    e.printStackTrace();
                    executeLog.add("发生了不可预知的错误:" + e);
                    testAutoSync.setTest_auto_sync_status(-1);
                } catch (InterruptedException e) {
                    executeLog.add("手动终止");
                    testAutoSync.setTest_auto_sync_status(-2);
                } finally {
                    testAutoSync.setTest_auto_sync_updatetime(new Timestamp(new Date().getTime()));
                    testAutoSyncDaoMapper.update(testAutoSync);
                }
            }
        });
        test.setSuccess("测试用例开始执行", null);
        return test;
    }

    @Override
    public TestingCenterResult<List<String>> getLog() {
        String runCaseName = testAutoSyncDaoMapper.findRunning();
        TestingCenterResult<List<String>> list = new TestingCenterResult<>();
        List<String> l = new ArrayList<>();
        if (runCaseName == null) {
            list.errorCommon("没有正在运行的测试用例,获取为上一次运行log结果");
            if (executeLog.size() == 0) {
                l.add("暂时还没有运行log");
            } else {
                l.add("没有正在运行的测试用例,获取为上一次运行log结果");
            }
            l.addAll(executeLog);
            list.setData(l);
            return list;
        }
        list.setSuccess(executeLog);
        return list;
    }

    @Override
    public TestingCenterResult<Object> stop() {
        TestingCenterResult<Object> test = new TestingCenterResult<>();
        return test.errorCommon("非常抱歉，现在暂时不支持手动停止，请您耐心等候！");
    }

    @Override
    public File getCaseLog() {
        File file = testAutoCaseRunDaoMapper.getLog();
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }
        return file;
    }
}

