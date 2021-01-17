package com.xiaobei.rabbitmq.spring.ttl;

import com.xiaobei.rabbitmq.spring.javaconfig.AmqpConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

import java.nio.charset.StandardCharsets;

/**
 * 测试消息的过期时间设置
 * <ul>分两种过期时间</ul>
 * <ol>1. 队列维度的过期时间设置</ol>
 * <ol>2. 消息维度的过期时间设置</ol>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 11:03:03
 */
@DisplayName("ttl 设置示例")
public class TTLDemo {

    /**
     * ttl 队列配置
     */
    @Configuration
    static class Config {

        public static final String TTL_QUEUE_NAME = "ttl-queue";

        public static final String COMMON_QUEUE_NAME = "common-queue";

        public static final String DIRECT_EXCHANGE_NAME = "direct-exchange";

        @Bean(name = "queue")
        @Profile("ttl")
        public Queue ttlQueue() {
            // 声明含有过期时间的队列并绑定交换机
            Queue queue = new Queue(TTL_QUEUE_NAME);
            // 队列中的消息超过 10s 钟未被消费则自动删除消息
            queue.addArgument("x-message-ttl", 10000);
            return queue;
        }

        @Bean(name = "binding")
        @Profile("ttl")
        public Binding ttlBinding() {
            return BindingBuilder.bind(ttlQueue())
                    .to(directExchange())
                    .with(TTL_QUEUE_NAME);
        }

        @Bean(name = "queue")
        @Profile("common")
        public Queue commonQueue() {
            return new Queue(COMMON_QUEUE_NAME);
        }

        @Bean(name = "binding")
        @Profile("common")
        public Binding commonBinding() {
            return BindingBuilder.bind(commonQueue())
                    .to(directExchange())
                    .with(COMMON_QUEUE_NAME);
        }

        @Bean(name = "direct-exchange")
        public DirectExchange directExchange() {
            return new DirectExchange(DIRECT_EXCHANGE_NAME);
        }

    }

    /**
     * 队列维度的 ttl 示例
     */
    @Test
    @DisplayName("测试队列的过期时间配置")
    void queueTTL() {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 激活 ttl 环境配置
        environment.setActiveProfiles("ttl");
        // 加载相关配置
        applicationContext.register(AmqpConfig.class, Config.class);
        applicationContext.refresh();
        AmqpTemplate amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        // 使用 queue name 作为 路由key
        amqpTemplate.convertAndSend(Config.DIRECT_EXCHANGE_NAME, Config.TTL_QUEUE_NAME,
                "this is a ttl message, expired after 10s");
        applicationContext.close();
    }

    @Test
    @DisplayName("测试消息的过期时间配置")
    void messageTTL() {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 激活 common 环境配置
        environment.setActiveProfiles("common");
        applicationContext.register(AmqpConfig.class, Config.class);
        applicationContext.refresh();
        AmqpTemplate amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        MessageProperties properties = new MessageProperties();
        // 设置消息的过期时间，单位为 ms
        properties.setExpiration("10000");
        Message message = new Message(
                "这条消息将在10s后消失".getBytes(StandardCharsets.UTF_8), properties);
        amqpTemplate.convertAndSend(Config.COMMON_QUEUE_NAME, message);
    }

    /**
     * 在同时指定消息和队列的过期时间时，是否以谁的时间段为准？
     * 以时间短的为准！
     */
    @Test
    @DisplayName("同时指定队列和消息的过期时间")
    void queueTTLAndMessageTTL() {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 激活 common 和 ttl 环境配置
        environment.setActiveProfiles("common", "ttl");
        applicationContext.register(AmqpConfig.class, Config.class);
        applicationContext.refresh();
        AmqpTemplate amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        MessageProperties properties = new MessageProperties();
        // 设置消息的过期时间，单位为 ms
        properties.setExpiration("5000");
        Message message = new Message(
                "队列过期时间为10s，消息过期时间为5s".getBytes(StandardCharsets.UTF_8), properties);
        amqpTemplate.convertAndSend(Config.TTL_QUEUE_NAME, message);
        // 设置消息的过期时间，单位为 ms
        properties.setExpiration("20000");
        Message message2 = new Message(
                "队列过期时间为10s，消息过期时间为20s".getBytes(StandardCharsets.UTF_8), properties);
        amqpTemplate.convertAndSend(Config.TTL_QUEUE_NAME, message2);
    }
}
