package com.xiaobei.middleware.redis.distlock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/22 16:21
 */
public class RedissonDisLock {

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.163.101:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock rLock = redissonClient.getLock("updateAccount");
        // 最多等待 100s，上锁 10s 以后自动解锁
        if(rLock.tryLock(100, 10, TimeUnit.SECONDS)) {
            System.out.println("获取锁成功");
        }
        // Thread.sleep(20000);
        rLock.unlock();

        redissonClient.shutdown();
    }
}
