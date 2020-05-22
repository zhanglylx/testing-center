package com.testing.center.web.dao.dao.test_auto;

import com.testing.center.web.dao.entity.test_auto_case.TestAutoSync;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAutoSyncDaoMapper {

    void save(@Param("testAutoSync") TestAutoSync testAutoSync);

    String findRunning();

    int update(@Param("testAutoSync") TestAutoSync testAutoSync);
}
