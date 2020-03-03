package dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
}
