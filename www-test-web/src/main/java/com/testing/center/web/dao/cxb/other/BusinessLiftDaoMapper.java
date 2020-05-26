package com.testing.center.web.dao.cxb.other;

import com.testing.center.web.dao.entity.cxb.other.BusinessLift;

/**
 * 商务吊起接口Dao
 */
public interface BusinessLiftDaoMapper {

    BusinessLift execute(String bookId, String imei, String oaid,Integer environment);


}
