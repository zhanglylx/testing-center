package com.testing.center.web.dao.entity.test_auto_case;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TestAutoSync implements Serializable {
    private Integer id;
    private Integer test_auto_list_id;
    private Integer test_auto_sync_status;
    private Timestamp test_auto_sync_createtime;
    private Timestamp test_auto_sync_updatetime;
}
