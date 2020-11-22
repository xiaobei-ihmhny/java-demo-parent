package com.xiaobei.middleware.redis.pipeline;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Set;

/**
 * 从测试结果看 {@code jedis.mget(...)} 的效率要略高于 {@code pipelined.syncAndReturnAll()}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/22 11:14
 */
@Component
@PropertySource(value = "classpath:/META-INF/redis.properties")
public class PipelineGet {

    @Bean
    public JedisPool jedisPool(@Value("${redis.host}") String host,
                               @Value("${redis.port}") int port,
                               @Value("${redis.timeout}") int timeout,
                               @Value("${redis.password}") String password) {
        return new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
    }

    /**
     * pipeline 共获取 1000000 个 key，耗时 1476 ms.
     * pipeline 共获取 1000000 个 key，耗时 1662 ms.
     * pipeline 共获取 1000000 个 key，耗时 1845 ms.
     * pipeline 共获取 1000000 个 key，耗时 1710 ms.
     * pipeline 共获取 1000000 个 key，耗时 1718 ms.
     * pipeline 共获取 1000000 个 key，耗时 1714 ms.
     * pipeline 共获取 1000000 个 key，耗时 1691 ms.
     * pipeline 共获取 1000000 个 key，耗时 1601 ms.
     * pipeline 共获取 1000000 个 key，耗时 1680 ms.
     * pipeline 共获取 1000000 个 key，耗时 1512 ms.
     */
    @Test
    public void pipelineGet() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PipelineSet.class);
        applicationContext.refresh();
        // 获取对应的 JedisPool
        JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
        for (int i = 0; i < 10; i++) {
            try (Jedis jedis = jedisPool.getResource();
                 Pipeline pipelined = jedis.pipelined()) {
                Set<String> keys = jedis.keys("batch*");
                long startTime = System.currentTimeMillis();
                for (String key : keys) {
                    pipelined.get(key);
                }
                List<Object> result = pipelined.syncAndReturnAll();
                System.out.printf("pipeline 共获取 %d 个 key，耗时 %d ms.\n", result.size(), System.currentTimeMillis() - startTime);
            }
        }
    }

    /**
     * mget 共获取 1000000 个 key，耗时 914 ms.
     * mget 共获取 1000000 个 key，耗时 896 ms.
     * mget 共获取 1000000 个 key，耗时 1967 ms.
     * mget 共获取 1000000 个 key，耗时 1027 ms.
     * mget 共获取 1000000 个 key，耗时 953 ms.
     * mget 共获取 1000000 个 key，耗时 1053 ms.
     * mget 共获取 1000000 个 key，耗时 865 ms.
     * mget 共获取 1000000 个 key，耗时 828 ms.
     * mget 共获取 1000000 个 key，耗时 821 ms.
     * mget 共获取 1000000 个 key，耗时 858 ms.
     */
    @Test
    public void mGet() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PipelineSet.class);
        applicationContext.refresh();
        // 获取对应的 JedisPool
        JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
        for (int i = 0; i < 10; i++) {
            try (Jedis jedis = jedisPool.getResource()) {
                Set<String> keys = jedis.keys("batch*");
                long startTime = System.currentTimeMillis();
                List<String> result = jedis.mget(keys.toArray(new String[0]));
                System.out.printf("mget 共获取 %d 个 key，耗时 %d ms.\n", result.size(), System.currentTimeMillis() - startTime);
            }
        }
    }
}
