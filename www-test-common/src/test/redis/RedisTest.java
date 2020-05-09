package redis;

import com.testing.center.common.cache.RedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisTest {
    public ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-common.xml");

    @Test
    public void test() {
        RedisClient redisClient = applicationContext.getBean("redisClient", RedisClient.class);
        System.out.println(redisClient.get("cx_findUserByMac_ssss"));
    }
}
