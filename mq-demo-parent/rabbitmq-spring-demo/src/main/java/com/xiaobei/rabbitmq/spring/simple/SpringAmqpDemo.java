package com.xiaobei.rabbitmq.spring.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * spring amqp 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 06:36:36
 */
public class SpringAmqpDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAmqpDemo.class);

    public static void main(String[] args) throws URISyntaxException {
        // 配置 连接工厂
        CachingConnectionFactory connectionFactory
                = new CachingConnectionFactory(
                        new URI("amqp://admin:centos01@192.168.163.101:5672"));
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("my-first-spring-queue"));
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("my-first-spring-queue", "hello rabbitmq");
        String result = (String) template.receiveAndConvert("my-first-spring-queue");
        LOGGER.info("接收到的消息为：{}", result);
    }
}
