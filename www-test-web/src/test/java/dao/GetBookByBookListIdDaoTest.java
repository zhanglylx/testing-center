package dao;

import com.testing.center.web.dao.book_resource_centre.GetBookByBookListIdDaoMapper;
import com.testing.center.web.dao.entity.ServerBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetBookByBookListIdDaoTest {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "spring/dao/spring-book-resource-centre.xml"
    );

    @Test
    public void test() {
        GetBookByBookListIdDaoMapper bookByBookListIdDaoMapper = applicationContext.getBean(GetBookByBookListIdDaoMapper.class);
        ServerBean r = bookByBookListIdDaoMapper.getBookByBookListId("810300121", 0);
        System.out.println(r);
    }
}
