package com.xiaobei.middleware.redis.distlock;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/22 15:00
 */
public class JedisDistLock {

    private static final String LOCK_SUCCESS = "OK";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请示标识
     * @param expireMilliTime 超时时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributeLock(
            Jedis jedis, String lockKey, String requestId, int expireMilliTime) {
        SetParams params = new SetParams();
        // 存在时才进行操作
        params.nx();
        // 设置过期时间为指定的毫秒数
        params.px(expireMilliTime);
        // SET 支持多个参数 NX(not exist) XX(exist) EX(seconds) PX(million seconds)
        String result = jedis.set(lockKey, requestId, params);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 尝试释放分布式锁
     * @param jedis Redis 客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean tryReleaseDistributeLock(Jedis jedis, String lockKey, String requestId) {
        // 删除之前先判断当前的标识与自己获取锁时的标识是否一致，
        // 若一致就删除对应的key（即释放锁），否则释放失败
        String delIfAvailableScript = "if redis.call('get', KEYS[1]) == ARGV([1] " +
                "then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(delIfAvailableScript, Collections.singletonList(lockKey),
                Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }
}
