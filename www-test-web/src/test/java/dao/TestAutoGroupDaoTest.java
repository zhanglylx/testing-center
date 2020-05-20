package dao;

import com.testing.center.web.dao.dao.test_auto.TestAutoGroupDaoMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAutoGroupDaoTest implements BaseTestDao {


    @Test
    public void testFindAll(){
        TestAutoGroupDaoMapper testAutoGroupDaoMapper = applicationContext.getBean(
                "testAutoGroupDaoMapper",
                TestAutoGroupDaoMapper.class
        );
        System.out.println(testAutoGroupDaoMapper.findAll());
    }
}
