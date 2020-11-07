package com.xiaobei.middleware.redis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/7 22:21
 */
public class SubThread extends Thread {
    private final JedisPool jedisPool;
    private final Subscriber subscriber = new Subscriber();

    private final String channel = "mychannel";

    public SubThread(JedisPool jedisPool) {
        super("SubThread");
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        System.out.printf("subscribe redis, channel %s, thread will be blocked%n", channel);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(subscriber, channel);
        } catch (Exception e) {
            System.out.printf("subscribe channel error, %s%n", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}