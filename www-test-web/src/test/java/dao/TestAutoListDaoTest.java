package dao;

import com.testing.center.web.dao.dao.TestAddressListDaoMapper;
import com.testing.center.web.dao.dao.test_auto.TestAutoListDaoMapper;
import org.junit.Test;

public class TestAutoListDaoTest implements BaseTestDao {

    @Test
    public void checkFindByGroupId() {
        TestAutoListDaoMapper test = applicationContext.getBean("testAutoListDaoMapper",TestAutoListDaoMapper.class);
        System.out.println(test.findByGroupId(2));
    }

}
