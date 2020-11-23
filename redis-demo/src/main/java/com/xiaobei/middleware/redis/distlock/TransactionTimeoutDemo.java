package com.xiaobei.middleware.redis.distlock;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/24 7:02
 */
public class TransactionTimeoutDemo {

    /**
     *
     */
    @Configuration
    @PropertySource(value = "classpath:META-INF/jdbc.properties")
    @EnableTransactionManagement
    static class JdbcTemplateConfig {

        @Bean(name = "jdbcTemplate")
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            return jdbcTemplate;
        }

        @Bean(name = "dataSource")
        public DataSource dataSource(@Value("${jdbc.url}") String url,
                                     @Value("${jdbc.username}") String username,
                                     @Value("${jdbc.password}") String password) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(JdbcTemplateConfig.class);
        applicationContext.refresh();
        // 获取 JdbcTemplate
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList("SELECT * FROM goods");
        System.out.println(resultList);
        applicationContext.close();
    }

    public AnnotationConfigApplicationContext startAndGetApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(JdbcTemplateConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }

    /**
     * TODO 待完成
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testTransaction() {
        AnnotationConfigApplicationContext applicationContext = startAndGetApplicationContext();
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        int updateCount = jdbcTemplate.update("UPDATE `goods` SET goods_title = 'iphone16 plus' WHERE id = 1");
        int i = 1/0;
        System.out.println(updateCount);
        applicationContext.close();
    }
}
