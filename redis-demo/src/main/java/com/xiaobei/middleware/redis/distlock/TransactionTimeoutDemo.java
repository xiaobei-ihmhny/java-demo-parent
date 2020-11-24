package com.xiaobei.middleware.redis.distlock;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/24 7:02
 */
@Component
public class TransactionTimeoutDemo {

    /**
     * 配置 {@link JdbcTemplate}、{@link DataSource} 和 {@link PlatformTransactionManager}
     */
    @Configuration
    @PropertySource(value = {"classpath:META-INF/jdbc.properties", "classpath:META-INF/redis.properties"})
    @EnableTransactionManagement// 开启注解事务支持
    @ComponentScan(basePackages = "com.xiaobei.middleware.redis.distlock")
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

        @Bean(name = "transactionManager")
        public DataSourceTransactionManager txManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public Config redissonConfig(
                @Value("${redis.singleServerAddress}") String singleServerAddress,
                @Value("${redis.password}") String password) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress(singleServerAddress)
                    .setPassword(password);
            return config;
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
     *
     */
    @Test
    public void testAutoTransaction() {
        AnnotationConfigApplicationContext applicationContext = startAndGetApplicationContext();
        TransactionProcess process = applicationContext.getBean(TransactionProcess.class);
        process.processAutoTransactionManager();
        applicationContext.close();
    }

    @Test
    public void testManualTransaction() {
        AnnotationConfigApplicationContext applicationContext = startAndGetApplicationContext();
        TransactionProcess process = applicationContext.getBean(TransactionProcess.class);
        process.processManualTransactionManager();
        applicationContext.close();
    }

    @Component
    class TransactionProcess {

        @Autowired
        private Config redissonConfig;

        @Autowired
        private DataSourceTransactionManager dataSourceTransactionManager;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        /**
         *
         * <p>相关解释：</p>
         * 事务的超时时间是指
         * Spring事务超时 = 事务开始时到最后一个Statement创建时时间 + 最后一个Statement的执行时超时时间（即其queryTimeout）。
         */
        @Transactional(rollbackFor = Exception.class, timeout = 1)
        public void processAutoTransactionManager() {
            RedissonClient redissonClient = Redisson.create(redissonConfig);
            RLock lock = redissonClient.getLock("xiaobei-ihmhny");
            try {
                while(lock.tryLock(20, 10, TimeUnit.SECONDS)) {
                    System.out.println("获取锁成功");
                    // 执行相关sql操作
                    int updateCount = jdbcTemplate.update("UPDATE `goods` SET goods_title = 'iphone16 plus' WHERE id = 1");
                    System.out.println(updateCount);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                redissonClient.shutdown();
            }
        }

        /**
         * 会进入两次 commit
         */
        public void processManualTransactionManager() {
            // 手动开启事务
            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
            RedissonClient redissonClient = Redisson.create(redissonConfig);
            RLock lock = redissonClient.getLock("xiaobei-ihmhny");
            try {
                while(lock.tryLock(20, 10, TimeUnit.SECONDS)) {
                    System.out.println("获取锁成功");
                    // 执行相关sql操作
                    int updateCount = jdbcTemplate.update("UPDATE `goods` SET goods_title = 'iphone16 plus' WHERE id = 1");
                    System.out.println(updateCount);
                    // 手动提交事务
                    dataSourceTransactionManager.commit(transactionStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 手动回滚事务
                dataSourceTransactionManager.rollback(transactionStatus);
            } finally {
                lock.unlock();
                redissonClient.shutdown();
            }
        }
    }
}
