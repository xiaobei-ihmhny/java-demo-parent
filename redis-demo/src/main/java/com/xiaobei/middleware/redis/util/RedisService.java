package com.xiaobei.middleware.redis.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/6 22:26
 * TODO 是否会存在资源泄露
 */
@Component
@PropertySource(value = "classpath:/META-INF/redis.properties")
public class RedisService implements InitializingBean {

    private JedisPool jedisPool;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.password}")
    private String password;

    /**
     * TODO 是否会存在资源泄露
     * @param key
     * @return
     */
    public String get(String key) {
        return jedisPool.getResource().get(key);
    }

    public String set(String key, String value) {
        return jedisPool.getResource().set(key, value);
    }

    @Override
    public String toString() {
        return "RedisService{" +
                "jedisPool=" + jedisPool +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(password == null || "".equals(password)) {
            jedisPool = new JedisPool(
                    new JedisPoolConfig(), host, port, 10000);
        } else {
            jedisPool = new JedisPool(
                    new JedisPoolConfig(), host, port, 10000, password);
        }
    }
}
