package com.testing.center.web.service.cxb.other.impl;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.cxb.other.BusinessLiftDaoMapper;
import com.testing.center.web.dao.entity.cxb.other.BusinessLift;
import com.testing.center.web.service.cxb.other.BusinessLiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessLiftServiceImpl implements BusinessLiftService {

    @Autowired
    BusinessLiftDaoMapper businessLiftDaoMapper;

    @Override
    public TestingCenterResult<BusinessLift> execute(String bookId, String imei, String oaid, Integer environment) {
        TestingCenterResult<BusinessLift> testingCenterResult = new TestingCenterResult<>();
        return testingCenterResult.setSuccess(businessLiftDaoMapper.execute(bookId, imei, oaid, environment));
    }
}
