package com.xiaobei.general.spring.demo;

import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 06:04
 */
public class MockObjectsTest {

    @Test
    public void environment() {
        // TODO 如何配合使用
        MockEnvironment environment = new MockEnvironment();
        environment.setActiveProfiles("test1");
        // TODO 如何配合使用
        MockPropertySource propertySource = new MockPropertySource();
        propertySource.setProperty("natie", 7);

    }

    /**
     * 通过 {@link ReflectionTestUtils} 设置或访问类中的私有成员、私有方法，方便调试。
     * <p>运行结果：
     * org.springframework.jdbc.datasource.DriverManagerDataSource@12f40c25
     * 这是一个私有方法，方法id为：100
     */
    @Test
    public void reflectionTestUtils() {
        UserService userService = new UserService();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("mysql:jdbc://127.0.0.1:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        // 通过 {@link ReflectionTestUtils} 设置私有属性
        ReflectionTestUtils.setField(userService, "dataSource", dataSource);
        DataSource dataSource1 = userService.getDataSource();
        System.out.println(dataSource1);
        // 通过 {@link ReflectionTestUtils} 访问私有方法
        Object result = ReflectionTestUtils.invokeMethod(userService,"privateMethod", 100L);
        System.out.println(result);
    }

    /**
     * TODO 演示 {@link AopTestUtils} 用法
     */
    @Test
    public void aopTestUtils() {
        // TODO 补充 aop 相关测试内容
    }
}
