package com.testing.center.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("testAddressListDaoMapper")
public interface TestAddressListDaoMapper extends ISqlMapper {
    List<Map> findTestAddressListByClassifyId(@Param("classifyId") Integer classifyId);
}
