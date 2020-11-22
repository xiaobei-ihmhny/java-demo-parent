package com.xiaobei.middleware.redis.shard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.List;

/**
 * jedis 实现的客户端分片方案
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/21 11:31
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/redis-sharding.properties")
public class ShardingMain {

    @Bean
    public ShardedJedisPool jedisPool(
            @Value("${redis.centos01.host}") String centos01Host,
            @Value("${redis.centos01.port}") int centos01Port,
            @Value("${redis.centos01.password}") String centos01Password,
            @Value("${redis.centos02.host}") String centos02Host,
            @Value("${redis.centos02.port}") int centos02Port,
            @Value("${redis.centos02.password}") String centos02Password,
            @Value("${redis.centos03.host}") String centos03Host,
            @Value("${redis.centos03.port}") int centos03Port,
            @Value("${redis.centos03.password}") String centos03Password) {
        // FIXME 暂时不进行配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisShardInfo centos01 = new JedisShardInfo(centos01Host, centos01Port);
        centos01.setPassword(centos01Password);
        JedisShardInfo centos02 = new JedisShardInfo(centos02Host, centos02Port);
        centos02.setPassword(centos02Password);
        JedisShardInfo centos03 = new JedisShardInfo(centos03Host, centos03Port);
        centos03.setPassword(centos03Password);
        List<JedisShardInfo > shards = Arrays.asList(centos01, centos02, centos03);
        return new ShardedJedisPool(poolConfig, shards);
    }

    /**
     * 取到值：0，当前key位于：192.168.163.101:6379
     * 取到值：1，当前key位于：192.168.163.103:6379
     * 取到值：2，当前key位于：192.168.163.101:6379
     * 取到值：3，当前key位于：192.168.163.102:6379
     * 取到值：4，当前key位于：192.168.163.102:6379
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ShardingMain.class);
        applicationContext.refresh();
        ShardedJedisPool jedisPool = applicationContext.getBean(ShardedJedisPool.class);
        // 测试指保存
        try(ShardedJedis jedis = jedisPool.getResource()) {
            for (int i = 0; i < 100; i++) {
                jedis.set("k"+i, String.valueOf(i));
            }
            for(int i=0; i<100; i++){
                Client client = jedis.getShard("k"+i).getClient();
                System.out.println("取到值："+jedis.get("k"+i)+"，"+"当前key位于：" + client.getHost() + ":" + client.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        applicationContext.close();
    }
}
