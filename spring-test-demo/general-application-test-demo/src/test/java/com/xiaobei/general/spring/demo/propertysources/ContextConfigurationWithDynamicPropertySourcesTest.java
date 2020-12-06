package com.xiaobei.general.spring.demo.propertysources;

import com.xiaobei.general.spring.demo.domain.MyDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 通过 {@link TestPropertySource#locations()} 指定属性配置文件的位置
 * 通过 {@link TestPropertySource#properties()} 新增或替换指定的属性对
 * 通过 {@link DynamicPropertySource} 来动态新增或替换属性信息
 * 参见：https://www.testcontainers.org/quickstart/junit_4_quickstart/
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 14:18
 * @since 5.2.5
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/app-config.xml")
@TestPropertySource(
        // 通过 locations 指定属性文件的位置
        locations = "classpath:application.properties",
        // 通过 properties 指定相关的键值对的值，其优先级要高于 locations 中的优先级
        properties = {
                "test1.jdbc.url = mysql:jdbc:///db1",// key=value
                "jdbc.username huihui",// key value
                "jdbc.password: tietie"// key:value
        })
public class ContextConfigurationWithDynamicPropertySourcesTest {

    /**
     * 其中 {@link DynamicPropertySource} 的优先级最高
     * @param registry
     */
    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.username", () -> "xiaohui");
    }

    @Autowired
    private MyDataSource myDataSource;

    @Value("${test1.jdbc.url}")
    private String testJdbcUrl;

    @Value("${test1.jdbc.username}")
    private String testJdbcUsername;

    /**
     * MyDataSource{url='mysql:jdbc://127.0.0.1:3306/db1', username='xiaohui', password='tietie'}
     * mysql:jdbc:///db1
     * root
     */
    @Test
    public void test() {
        System.out.println(myDataSource);
        System.out.println(testJdbcUrl);
        System.out.println(testJdbcUsername);
    }

}
