package com.testing.center.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("testAddressClassifyDaoMapper")
public interface TestAddressClassifyDaoMapper extends ISqlMapper {
    List<Map> findTestAddressClassify();
}
