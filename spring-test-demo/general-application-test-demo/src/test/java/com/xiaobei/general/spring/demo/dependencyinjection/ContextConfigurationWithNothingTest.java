package com.xiaobei.general.spring.demo.dependencyinjection;

import com.xiaobei.general.spring.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link ContextConfiguration} 注解不指定任何参数，
 * 则默认会从当前测试类的内部类中加载所有的 {@code Configuration Class}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 10:58
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ContextConfigurationWithNothingTest {

    @Configuration
    static class ContextConfig {

        @Bean(name = "natietie")
        public User user() {
            User user = new User();
            user.setUsername("natietie");
            user.setAge(1);
            return user;
        }
    }

    @Autowired
    private User user;

    /**
     * User{username='natietie', age=1}
     */
    @Test
    public void testContextConfigurationWithNothing() {
        System.out.println(user);
    }
}
