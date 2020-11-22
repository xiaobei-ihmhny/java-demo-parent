package com.xiaobei.middleware.redis.sentinel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/20 6:45
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/redis-sentinel.properties")
public class SentinelMain {

    @Bean
    public JedisSentinelPool pool(
            @Value("${spring.redis.sentinel.master}") String name,
            @Value("${spring.redis.sentinel.password}") String password,
            @Value("${spring.redis.sentinel.maxTotal}") int maxTotal,
            @Value("${spring.redis.sentinel.maxIdle}") int maxIdle,
            @Value("${spring.redis.sentinel.maxWaitMillis}") int maxWaitMillis,
            @Value("${spring.redis.sentinel.timeout}") int timeout,
            @Value("${spring.redis.sentinel.nodes}") String[] sentinels) {
        Set<String> sentinelSet = Arrays.stream(sentinels).collect(Collectors.toSet());
        // 首先如果要想使用Jedis连接池，则必须有一个类可以保存所有连接池相关属性的配置项
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数
        poolConfig.setMaxTotal(maxTotal);
        // 设置空闲的连接数
        poolConfig.setMaxIdle(maxIdle);
        // 最大等待时间
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        // 创建并返回一个哨兵的连接池
        return new JedisSentinelPool(name, sentinelSet, poolConfig, password);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SentinelMain.class);
        applicationContext.refresh();
        JedisSentinelPool pool = applicationContext.getBean(JedisSentinelPool.class);
        Jedis jedis = pool.getResource();
        String resultSet = jedis.get("xiaobei");
        System.out.println(resultSet);
        jedis.lpush("naTie", "111","222");
        applicationContext.close();
    }
}
