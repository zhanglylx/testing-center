package com.testing.center.web.dao.dao.test_auto;

import com.testing.center.web.dao.dao.ISqlMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
//mvn package -f E:\server-testng\pom.xml  test -Dsurefire.suiteXmlFiles=E:\server-testng\testNgRun\thumbupCancel.xml
@Repository("testAutoGroupDaoMapper")
public interface TestAutoGroupDaoMapper extends ISqlMapper {
    List<Map<String,Object>> findAll();
}
