package com.xiaobei.middleware.redis.replication;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xiaobei.middleware.redis.pubsub.PubSubMain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/11 6:53
 */
@ComponentScan("com.xiaobei.middleware.redis.util")
public class ReplicationMain {


    private AnnotationConfigApplicationContext applicationContext;

    private JedisPool jedisPool;

    private static final int THRESHOLD = 100;

    private static final ThreadFactory factory =
            new ThreadFactoryBuilder().setNameFormat("%d_Thread").build();

    public static final ExecutorService threadPool = new ThreadPoolExecutor(
            5,
            10,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            factory,
            new ThreadPoolExecutor.AbortPolicy()
    );

    @Before
    public void before() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PubSubMain.class);
        applicationContext.refresh();
        jedisPool = applicationContext.getBean(JedisPool.class);
    }

    @After
    public void after() {
        applicationContext.close();
    }


    @Test
    public void replication() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("xiaobei","1111");
        String xiaobei = jedis.get("xiaobei");
        System.out.println(xiaobei);
    }
}
