package dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface BaseTestDao {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "spring\\dao\\spring-mybatis.xml"
    );
}
