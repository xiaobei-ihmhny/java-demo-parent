package com.xiaobei.general.spring.demo.envirenmentprofiles;

import com.xiaobei.general.spring.demo.domain.MyDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 使用 {@link ContextConfiguration} 来加载所有环境的配置信息（此外为 java 配置）
 * 使用 {@link ActiveProfiles} 来指定当前测试类想要激活的环境信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 12:38
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles("test1")
public class EnvironmentProfilesFromAnnotationTest {

    @Configuration
    @PropertySource(value = "classpath:application.properties")
    static class AnnotationConfig {

        @Bean(name = "myDataSource")
        @Profile("default")
        public MyDataSource myDataSourceDefault(
                @Value("${jdbc.url}") String url,
                @Value("${jdbc.username}") String username,
                @Value("${jdbc.password}") String password) {
            MyDataSource myDataSource = new MyDataSource();
            myDataSource.setUrl(url);
            myDataSource.setUsername(username);
            myDataSource.setPassword(password);
            return myDataSource;
        }

        @Bean(name = "myDataSource")
        @Profile("dev1")
        public MyDataSource myDataSourceDev1(
                @Value("${dev1.jdbc.url}") String url,
                @Value("${dev1.jdbc.username}") String username,
                @Value("${dev1.jdbc.password}") String password) {
            MyDataSource myDataSource = new MyDataSource();
            myDataSource.setUrl(url);
            myDataSource.setUsername(username);
            myDataSource.setPassword(password);
            return myDataSource;
        }

        @Bean(name = "myDataSource")
        @Profile("test1")
        public MyDataSource myDataSourceTest1(
                @Value("${test1.jdbc.url}") String url,
                @Value("${test1.jdbc.username}") String username,
                @Value("${test1.jdbc.password}") String password) {
            MyDataSource myDataSource = new MyDataSource();
            myDataSource.setUrl(url);
            myDataSource.setUsername(username);
            myDataSource.setPassword(password);
            return myDataSource;
        }
    }

    @Autowired
    private MyDataSource myDataSource;

    /**
     * MyDataSource{url='mysql:jdbc://127.0.0.1:3306/db_test1', username='root', password='root'}
     */
    @Test
    public void activeProfilesTest() {
        System.out.println(myDataSource);
    }
}
