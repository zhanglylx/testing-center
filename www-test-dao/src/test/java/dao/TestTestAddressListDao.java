package dao;

import com.testing.center.dao.TestAddressListDaoMapper;

public class TestTestAddressListDao extends TestBase  {
    TestAddressListDaoMapper testAddressListDaoMapper =
            applicationContext.getBean("testAddressListDaoMapper",TestAddressListDaoMapper.class);

//    public void findAll(){
//        System.out.println(testAddressListDaoMapper.findTestAddressList());
//    }
}
