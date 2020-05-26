package com.testing.center.web.service.cxb.other;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.cxb.other.BusinessLift;

public interface BusinessLiftService {

    TestingCenterResult<BusinessLift> execute(String bookId, String imei, String oaid, Integer environment);
}
