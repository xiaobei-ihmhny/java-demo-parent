package com.xiaobei.general.spring.demo.envirenmentprofiles;

import com.xiaobei.general.spring.demo.domain.MyDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * 使用 {@link ContextConfiguration} 来加载所有环境的配置信息（此外为 xml 配置）
 * 使用 {@link ActiveProfiles} 来指定当前测试类想要激活的环境信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 12:13
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/app-config.xml")
@ActiveProfiles("dev1")
public class EnvironmentProfilesFromXmlTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyDataSource myDataSource;

    /**
     * MyDataSource{url='mysql:jdbc://127.0.0.1:3306/db_dev1', username='root', password='root'}
     */
    @Test
    public void activeProfilesTest() {
        System.out.println(myDataSource);
    }

}
