package com.testing.center.web.dao.dao.test_auto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("testAutoListDaoMapper")
public interface TestAutoListDaoMapper {
    List<Map<String, Object>> findByGroupId(@Param("testAutoGroupId") Integer testAutoGroupId);
}
