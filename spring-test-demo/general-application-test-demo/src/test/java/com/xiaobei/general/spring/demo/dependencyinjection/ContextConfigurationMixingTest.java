package com.xiaobei.general.spring.demo.dependencyinjection;

import com.xiaobei.general.spring.demo.domain.User;
import com.xiaobei.general.spring.demo.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 11:07
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ContextConfigurationMixingTest {

    @Configuration
    @Import(SpringConfig.class)
    @ImportResource(locations = "classpath:META-INF/application.xml")
    static class MixContextConfig {

    }

    @Autowired
    private List<User> users;

    /**
     * [User{username='natie', age=1}, User{username='xiaobei', age=18}]
     */
    @Test
    public void mixContextTest() {
        System.out.println(users);
    }
}
