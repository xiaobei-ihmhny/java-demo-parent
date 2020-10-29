package com.xiaobei.java.demo.distributed.lock.redis;

/**
 * 使用 redis 实现的分布式锁
 *
 * 实现思路：
 * 1. setnx(lockkey, 当前时间+过期超时时间) ，如果返回1，则获取锁成功；
 * 如果返回0则没有获取到锁，转向2。
 * 2. get(lockkey)获取值oldExpireTime ，并将这个value值与当前的系统时间进行比较，
 * 如果小于当前系统时间，则认为这个锁已经超时，可以允许别的请求重新获取，转向3。
 * 3. 计算newExpireTime=当前时间+过期超时时间，然后getset(lockkey, newExpireTime)
 * 会返回当前 lockkey 的值currentExpireTime。
 * 4. 判断currentExpireTime与oldExpireTime 是否相等，如果相等，说明当前getset设置成功，获取到了锁。
 * 如果不相等，说明这个锁又被别的请求获取走了，那么当前请求可以直接返回失败，或者继续重试。
 * 5. 在获取到锁之后，当前线程可以开始自己的业务处理，当处理完毕后，
 * 比较自己的处理时间和对于锁设置的超时时间，如果小于锁设置的超时时间，则直接执行delete释放锁；
 * 如果大于锁设置的超时时间，则不需要再锁进行处理。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-28 17:59:59
 */
public class RedisDistributedLockUtil {
}