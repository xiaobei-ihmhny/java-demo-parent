package com.xiaobei.middleware.redis.pipeline;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/22 9:50
 */
@Component
@PropertySource(value = "classpath:/META-INF/redis.properties")
public class PipelineSet {

    @Bean
    public JedisPool jedisPool(@Value("${redis.host}") String host,
                               @Value("${redis.port}") int port,
                               @Value("${redis.timeout}") int timeout,
                               @Value("${redis.password}") String password) {
        return new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
    }

    /**
     * 本机测试结果 1000000万条数据使用 pipeline 保存仅需 3.641s
     */
    @Test
    public void usePipeline() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PipelineSet.class);
        applicationContext.refresh();
        // 获取对应的 JedisPool
        JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
        try(Jedis jedis = jedisPool.getResource();
            Pipeline pipelined = jedis.pipelined()) {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
                pipelined.set("batch"+i, ""+i);
            }
            pipelined.syncAndReturnAll();
            System.out.printf("耗时：%d ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationContext.close();
    }

    /**
     * TODO 10万条数据保存没问题，但100万条数据保存失败，需要找原因？？？
     */
    @Test
    public void useMSet() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PipelineSet.class);
        applicationContext.refresh();
        // 获取对应的 JedisPool
        // TODO 为什么 1000000 个 key-value 对会设置失败？？
        int keySizes = 1000000;
        JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
        try (Jedis jedis = jedisPool.getResource()) {
            long startTime = System.currentTimeMillis();
            String[] allKeyValues = new String[2 * keySizes];
            for (int i = 0; i < keySizes; i++) {
                int j = 2 * i;
                allKeyValues[j] = "batch"+i;
                allKeyValues[j + 1] = ""+i;
            }
            jedis.mset(allKeyValues);
            System.out.printf("耗时：%d ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        applicationContext.close();

    }
}
