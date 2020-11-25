package com.xiaobei.middleware.redis.distlock;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
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
 * <p>建表语句</p>
 * CREATE DATABASE IF NOT EXISTS `miaosha` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
 * CREATE TABLE `goods` (
 *    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 *    `goods_title` varchar(255) DEFAULT NULL COMMENT '商品名称',
 *    PRIMARY KEY (`id`)
 *  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Component
public class TransactionTimeoutTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTimeoutTest.class);

    /**
     * 配置 {@link JdbcTemplate}、{@link DataSource} 和 {@link PlatformTransactionManager}
     *
     * {@link EnableTransactionManagement @EnableTransactionManagement} 开启注解事务支持
     * {@link Configuration @Configuration} 指定当前类为配置类
     * {@link PropertySource @PropertySource} 指定属性配置文件位置
     * {@link ComponentScan @ComponentScan} 指定包扫描路径
     */
    @Configuration
    @PropertySource(value = {"classpath:META-INF/jdbc.properties", "classpath:META-INF/redis.properties"})
    @EnableTransactionManagement
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

    public AnnotationConfigApplicationContext startAndGetApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(JdbcTemplateConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }

    /**
     * 测试自动事务控制
     */
    @Test
    public void testAutoTransaction() {
        AnnotationConfigApplicationContext applicationContext = startAndGetApplicationContext();
        TransactionProcess process = applicationContext.getBean(TransactionProcess.class);
        process.processAutoTransactionManager();
        applicationContext.close();
    }

    /**
     * 测试手动事务控制
     */
    @Test
    public void testManualTransaction() {
        AnnotationConfigApplicationContext applicationContext = startAndGetApplicationContext();
        TransactionProcess process = applicationContext.getBean(TransactionProcess.class);
        process.processManualTransactionManager();
        applicationContext.close();
    }

    /**
     * 测试自动事务和手动事务
     */
    @Component
    static class TransactionProcess {

        /**
         * redis配置信息
         */
        private Config redissonConfig;

        /**
         * 事务管理器
         */
        private DataSourceTransactionManager dataSourceTransactionManager;

        /**
         * jdbcTemplate
         */
        private JdbcTemplate jdbcTemplate;

        @Autowired
        public TransactionProcess setRedissonConfig(Config redissonConfig) {
            this.redissonConfig = redissonConfig;
            return this;
        }

        @Autowired
        public TransactionProcess setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
            this.dataSourceTransactionManager = dataSourceTransactionManager;
            return this;
        }

        @Autowired
        public TransactionProcess setJdbcTemplate(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            return this;
        }

        /**
         * 测试的时候需要在进入方法：if(lock.tryLock(30, TimeUnit.SECONDS)) 之前执行如下命令：
         * <pre class="code">
         *     hset xiaobei-ihmhny aaaa 1;
         *     expire xiaobei-ihmhny 15
         *     </pre>
         * <p>相关解释：</p>
         * 事务的超时时间是指
         * Spring事务超时 = 事务开始时到最后一个Statement创建时时间 + 最后一个Statement的执行时超时时间（即其queryTimeout）。
         */
        @Transactional(rollbackFor = Exception.class, timeout = 3)
        public void processAutoTransactionManager() {
            RedissonClient redissonClient = Redisson.create(redissonConfig);
            RLock lock = redissonClient.getLock("xiaobei-ihmhny");
            try {
                // 只有当不设置锁的过期时间时，“看门狗”才会被激活
                long startTime = System.currentTimeMillis();
                System.out.println("开始尝试获取锁，开始时间："+ startTime);
                if(lock.tryLock(30, TimeUnit.SECONDS)) {
                    System.out.println("获取锁成功，用时："+ (System.currentTimeMillis() - startTime));
                    // 执行相关sql操作
                    int updateCount = jdbcTemplate.update("UPDATE `goods` SET goods_title = 'iphone16 plus' WHERE id = 1");
                    System.out.println(updateCount);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(lock.isHeldByCurrentThread())
                    lock.unlock();
                redissonClient.shutdown();
            }
        }

        /**
         * 测试手动提交事务
         *
         * 测试的时候需要在进入方法：if(lock.tryLock(30, TimeUnit.SECONDS)) 之前执行如下命令：
         * <pre class="code">
         *     hset xiaobei-ihmhny aaaa 1;
         *     expire xiaobei-ihmhny 15
         *     </pre>
         */
        public void processManualTransactionManager() {
            // 手动开启事务
            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
            RedissonClient redissonClient = Redisson.create(redissonConfig);
            RLock lock = redissonClient.getLock("xiaobei-ihmhny");
            try {
                // 只有当不设置锁的过期时间时，“看门狗”才会被激活
                if(lock.tryLock(30, TimeUnit.SECONDS)) {
                    LOGGER.info("获取锁成功");
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
                LOGGER.info("准备释放锁，关闭 redisson 客户端");
                lock.unlock();
                redissonClient.shutdown();
            }
        }
    }
}
