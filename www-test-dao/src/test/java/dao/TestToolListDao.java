package dao;

import com.testing.center.dao.ToolListDaoMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestToolListDao {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
    ToolListDaoMapper toolListDaoMapper = applicationContext.getBean("toolListDaoMapper",ToolListDaoMapper.class);
    @Test
    public void testFindAll(){
        System.out.println(toolListDaoMapper.findAll());
    }
}
