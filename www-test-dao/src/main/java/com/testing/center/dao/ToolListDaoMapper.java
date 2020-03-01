package com.testing.center.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("toolListDaoMapper")
public interface ToolListDaoMapper extends ISqlMapper{
    List<Map> findAll();
}
