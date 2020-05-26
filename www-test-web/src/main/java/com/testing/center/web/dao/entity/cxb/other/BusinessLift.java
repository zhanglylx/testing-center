package com.testing.center.web.dao.entity.cxb.other;

import com.testing.center.web.dao.entity.ServerBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessLift extends ServerBean {
    private GuangDtClickData guangDtClickData;
}
