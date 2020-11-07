package com.xiaobei.middleware.redis.pubsub;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * 一、定义Subscriber类
 * 二、定义SubThread线程类（也可以直接使用线程池）
 * 三、定义Publisher类
 * 参考：<a href="https://blog.csdn.net/lihao21/article/details/48370687">Jedis实现Publish/Subscribe功能</a>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/7 21:55
 */
@ComponentScan("com.xiaobei.middleware.redis.util")
public class PubSubMain {

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

    /**
     * 订阅者
     */
    @Test
    public void sub() {
//        SubThread subThread = new SubThread(jedisPool);
//        subThread.start();
        // 监听者
        Future<?> subscribeResult = threadPool.submit(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.subscribe(new Subscriber(), "my-channel");
        });
        Future<?> subscribeResult2 = threadPool.submit(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.psubscribe(new Subscriber(), "my-*");
        });
        Future<?> subscribeResult3 = threadPool.submit(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.psubscribe(new Subscriber(), "*-channel");
        });
        try {
            // 用于阻塞当前线程，避免直接结束！！！
            Object result = subscribeResult.get();
            Object result2 = subscribeResult2.get();
            Object result3 = subscribeResult3.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    /**
     * 发布者
     */
    @Test
    public void pub1() {
        Jedis jedis = jedisPool.getResource();
        jedis.publish("my-natie", "natie");
        jedis.publish("her-channel", "huihui");
        jedis.publish("my-channel", "xiaobei");
    }

    /**
     * TODO {@link Test @Test} 无法实现参数输入
     */
    @Test
    public void pub() {
        // 发布者
        Future<?> publishResult = threadPool.submit(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Jedis jedis = jedisPool.getResource();
            while(true) {
                String line = null;
                try {
                    line = reader.readLine();
                    if(!"quit".equalsIgnoreCase(line)) {
                        jedis.publish("my-channel", line);
                    } else {
                        break;
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        try {
            Object pResult = publishResult.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}
