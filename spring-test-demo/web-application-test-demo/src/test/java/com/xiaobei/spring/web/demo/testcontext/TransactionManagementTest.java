package com.xiaobei.spring.web.demo.testcontext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * 默认情况下，使用在测试类中使用 {@link Transactional} 执行的操作都是会回滚的，
 * 可以使用 {@link org.springframework.test.annotation.Commit}
 * 或 {@link org.springframework.test.annotation.Rollback}
 * 来指明是提交事务还是回滚事务
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 17:52
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@Transactional
public class TransactionManagementTest {

    @Configuration
    @PropertySource(value = "classpath:application.properties")
    static class AppConfig {

        @Bean(name = "dataSource")
        public DataSource dataSource(
                @Value("${jdbc.url}") String url,
                @Value("${jdbc.username}") String username,
                @Value("${jdbc.password}") String password) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }

        @Bean(name = "jdbcTemplate")
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            return jdbcTemplate;
        }

        @Bean(name = "transactionManager")
        public DataSourceTransactionManager transactionManager(DataSource dataSource) {
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource);
            return transactionManager;
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testTransaction() {
        int update = jdbcTemplate.update("UPDATE user SET `user`.age = ? WHERE `user`.id = ?", 1, 3);
        System.out.println(update);
    }
}
