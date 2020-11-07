package com.xiaobei.middleware.redis.datatype;

import com.xiaobei.middleware.redis.util.RedisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/6 22:58
 */
@ComponentScan("com.xiaobei.middleware.redis.util")
public class DataTypeDemo {

    AnnotationConfigApplicationContext context;

    private RedisService redisService;

    private JedisPool jedisPool;

    @Before
    public void before() {
        context = new AnnotationConfigApplicationContext();
        context.register(DataTypeDemo.class);
        context.refresh();
        redisService = context.getBean(RedisService.class);
        jedisPool = context.getBean(JedisPool.class);
    }

    @After
    public void after() {
        context.close();
    }

    @Test
    public void string() {
        redisService.set("111","aaa");
        String s = redisService.get("111");
        System.out.println(s);
    }

    public Map<String, String> getCartMap() {
        Map<String, String> cartMap = new HashMap<>();
        cartMap.put("userId", "1001");
        cartMap.put("totalPayPrice", "1290.00");
        return cartMap;
    }

    @Test
    public void hash() {
        Jedis jedis = jedisPool.getResource();
        // 设置购物车信息
        Long result = jedis.hset("cart-1001", getCartMap());
        Map<String, String> cartMap = jedis.hgetAll("cart-1001");
        System.out.println(cartMap);
    }

    @Test
    public void zSet() {
        Jedis jedis = jedisPool.getResource();
        Map<String, Double> scoreMembers = new HashMap<>(8);
        scoreMembers.put("xiaobei", 30d);
        scoreMembers.put("huihui", 40d);
        scoreMembers.put("natie", 2d);
        Long result = jedis.zadd("myZset", scoreMembers);
        System.out.println(result);
        Set<String> myZset = jedis.zrange("myZset", 0L, -1L);
        System.out.println(myZset);
    }

    @Test
    public void hyperLogLog() {
        Jedis jedis = jedisPool.getResource();
        int size = 100000;
        String[] hyperStr = new String[size];
        for (int i = 0; i < size; i++) {
            hyperStr[i] = "hll-" + i;
        }
        Long result = jedis.pfadd("hyper", hyperStr);
        System.out.println(result);
        long count = jedis.pfcount("hyper");
        System.out.printf("统计个数：%s", count);
        System.out.printf("正确率：%s",  count * 1.0 / size);
        System.out.printf("误差率：%s", 1 - (count * 1.0 / size));
    }

    @Test
    public void pubSub() {
        Jedis jedis = jedisPool.getResource();
//        JedisPubSub pubSub = new JedisSentinelPool();
//        jedis.psubscribe();
    }

}
