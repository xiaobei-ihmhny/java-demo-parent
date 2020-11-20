package com.xiaobei.middleware.redis.sentinel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
            @Value("${spring.redis.sentinel.nodes}") String[] sentinels) {
        return new JedisSentinelPool(name,
                Arrays.stream(sentinels).collect(Collectors.toSet()));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SentinelMain.class);
        applicationContext.refresh();
        JedisSentinelPool pool = applicationContext.getBean(JedisSentinelPool.class);
        Jedis jedis = pool.getResource();
        jedis.set("xiaobei", "100");
        System.out.println(jedis.get("xiaobei"));
        applicationContext.close();
    }
}
