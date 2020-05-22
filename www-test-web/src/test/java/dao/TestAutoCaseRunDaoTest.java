package dao;
import	java.util.concurrent.Executors;
import	java.util.concurrent.ExecutorService;

import com.testing.center.web.dao.cxb.test_auto_case.TestAutoCaseRunDaoMapper;
import com.testing.center.web.dao.cxb.test_auto_case.impl.TestAutoCaseRunDaoMapperImpl;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestAutoCaseRunDaoTest implements BaseTestDao {

    @Test
    public void checkExecute() {
        TestAutoCaseRunDaoMapper type = applicationContext.getBean("testAutoCaseRunDaoMapper", TestAutoCaseRunDaoMapper.class);
        List<String> log = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        try {
            type.execute("com\\test_cases\\read\\read\\ReadTest", log);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
