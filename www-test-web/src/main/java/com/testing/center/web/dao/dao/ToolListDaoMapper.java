package com.testing.center.web.dao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("toolListDaoMapper")
public interface ToolListDaoMapper extends ISqlMapper {
    List<Map> findAll();
    List<Map> findByBoxId(@Param("id") Integer boxId);
    int addHeat(@Param("id") Integer toolListId);
}
