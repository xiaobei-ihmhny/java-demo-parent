package com.xiaobei.middleware.redis.pubsub;

import redis.clients.jedis.JedisPubSub;

/**
 * 定义订阅者类，用于处理订阅相关的事件
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/7 21:26
 */
public class Subscriber extends JedisPubSub {

    /**
     * 获取订阅的消息后的处理
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        System.out.printf("receive redis published message, channel %s, message %s\n",
                channel, message);
    }

    /**
     * 初始化订阅时的处理
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.printf("subscribe redis channel success, channel %s, subscribedChannels %d\n",
                channel, subscribedChannels);
    }

    /**
     * 取消订阅时的处理
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.printf("unsubscribe redis channel, channel %s, subscribedChannels %d\n",
                channel, subscribedChannels);
    }

    /**
     * 取得按表达式的方式订阅的消息后的处理
     * @param pattern
     * @param channel
     * @param message
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.printf("receive redis published pMessage, pattern %s channel %s, message %s\n",
                pattern, channel, message);
    }

    /**
     * 取消按表达式的方式订阅时候的处理
     * @param pattern
     * @param subscribedChannels
     */
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.printf("unsubscribe redis channel, pattern %s, subscribedChannels %d\n",
                pattern, subscribedChannels);
    }

    /**
     * 初始化表达时的方式订阅时的处理
     * @param pattern
     * @param subscribedChannels
     */
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.printf("subscribe redis channel success, pattern %s, subscribedChannels %d\n",
                pattern, subscribedChannels);
    }
}
