package com.xiaobei.middleware.redis.monitor;

import com.google.common.util.concurrent.AtomicLongMap;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 监控 Redis 服务端的变化，做一些统计信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/22 21:32
 */
@Component
@PropertySource(value = "classpath:/META-INF/redis.properties")
public class MonitorDemo {

    @Bean
    public JedisPool jedisPool(@Value("${redis.host}") String host,
                               @Value("${redis.port}") int port,
                               @Value("${redis.timeout}") int timeout,
                               @Value("${redis.password}") String password) {
        return new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MonitorDemo.class);
        applicationContext.refresh();
        // 获取 JedisPool
        JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.monitor(new JedisMonitor() {
                @Override
                public void onCommand(String command) {
                    System.out.printf("#monitor: %s\n", command);
                    AtomicLongMap<String> ATOMIC_LONG_MAP = AtomicLongMap.create();
                    ATOMIC_LONG_MAP.incrementAndGet(command);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationContext.close();
    }
}
