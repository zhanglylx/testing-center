package com.testing.center.web.contoller.cxb.other;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.common.utils.cxb.URLEnvironment;
import com.testing.center.web.dao.entity.cxb.other.BusinessLift;
import com.testing.center.web.service.cxb.other.BusinessLiftService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
public class BusinessLiftContoller implements OtherContoller {
    @Autowired
    BusinessLiftService businessLiftService;


    @PostMapping(value = "/businessLift", produces = "application/json;charset=UTF-8")
    public TestingCenterResult<BusinessLift> executor(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "imei", required = false, defaultValue = "") String imei,
            @RequestParam(value = "oaid", required = false, defaultValue = "") String oaid,
            @RequestParam(value = "environment") @Min(URLEnvironment.QA) @Max(URLEnvironment.ONLINE) Integer environment) {
        if (StringUtils.isBlank(imei) && StringUtils.isBlank(oaid)) throw new ValidationException("imei与oaid不能同时为空");
        return businessLiftService.execute(bookId, imei, oaid, environment);

    }
}
