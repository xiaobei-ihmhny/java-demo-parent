package com.xiaobei.middleware.redis.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sentinel 模式测试
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/21 6:51
 */
@EnableWebMvc
@Configuration
@RestController
@RequestMapping("/")
@ComponentScan("com.xiaobei.middleware.redis.sentinel")
public class SentinelWeb extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static <T> T[] of(T... values) {
        return values;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return of(SentinelWeb.class);
    }

    @Override
    protected String[] getServletMappings() {
        return of("/*");
    }


    @Bean(name = "pool")
    public JedisSentinelPool pool(
            @Value("${spring.redis.sentinel.master}") String name,
            @Value("${spring.redis.sentinel.password}") String password,
            @Value("${spring.redis.sentinel.nodes}") String[] sentinels) {
        Set<String> sentinelSet = Arrays.stream(sentinels).collect(Collectors.toSet());
        return new JedisSentinelPool(name, sentinelSet, password);
    }

    @Autowired
    private JedisSentinelPool pool;

    @RequestMapping("get/{key}")
    public String getKey(@PathVariable("key") String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // FIXME 一定要关闭连接，否则会出现问题
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @RequestMapping("lpush/{key}/{values}")
    public String setKeyValue(
            @PathVariable("key") String key,
            @PathVariable("values") String values) {
        // 因为 Jedis 实现了 Closeable 接口，所以可以使用 jdk7 的 try-with-resource 语法
        try (Jedis jedis = pool.getResource()) {
            String[] valueArr = values.split(",");
            Long count = jedis.lpush(key, valueArr);
            return String.valueOf(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
