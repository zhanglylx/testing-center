package com.testing.center.web.dao.dao;


import com.testing.center.web.dao.entity.ToolBox;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("toolBoxDaoMapper")
public interface ToolBoxDaoMapper extends ISqlMapper {
    List<ToolBox> findAll();

    ToolBox findById(@Param("id") Integer id);
}
