package dao;

import com.testing.center.dao.TestAddressClassifyDaoMapper;
import org.junit.Test;

public class TestTestAddressClassifyDao extends TestBase {
    TestAddressClassifyDaoMapper testAddressClassifyDaoMapper =
            applicationContext.getBean("testAddressClassifyDaoMapper",TestAddressClassifyDaoMapper.class);
    @Test
    public void findAll(){
        System.out.println(testAddressClassifyDaoMapper.findTestAddressClassify());
    }
}
