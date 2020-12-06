package com.xiaobei.general.spring.demo.dependencyinjection;

import com.xiaobei.general.spring.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO 需要研究一下 {@link org.junit.runner.RunWith} 配合 {@link org.springframework.test.context.junit4.SpringRunner} 在底层到底做了什么？？？？
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 10:26
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/application.xml")
public class ContextConfigurationWithXmlResourcesTest {

    @Autowired
    private User user;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * User{username='xiaobei', age=18}
     */
    @Test
    public void test() {
        System.out.println(user);
    }
}
